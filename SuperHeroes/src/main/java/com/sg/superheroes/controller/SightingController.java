/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.controller;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanSightingBridge;
import com.sg.superheroes.model.Sighting;
import com.sg.superheroes.service.LocationService;
import com.sg.superheroes.service.MetaHumanService;
import com.sg.superheroes.service.MetaHumanSightingBridgeService;
import com.sg.superheroes.service.SightingService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author chandler
 */
@Controller
public class SightingController {

    SightingService service;
    MetaHumanService MHservice;
    LocationService locService;
    MetaHumanSightingBridgeService MHSightingBridgeService;

    @Inject
    public SightingController(SightingService service, MetaHumanService MHservice, LocationService locService,
            MetaHumanSightingBridgeService MHSightingBridgeService) {
        this.service = service;
        this.MHservice = MHservice;
        this.locService = locService;
        this.MHSightingBridgeService = MHSightingBridgeService;
    }

    @RequestMapping(value = "/sightings", method = RequestMethod.GET)
    public String displayAllSightings(HttpServletRequest request, Model model) {

        List<Sighting> sightingList = service.getAllSightings();
        model.addAttribute("sightingList", sightingList);

        Map<Sighting, List<MetaHuman>> map = new LinkedHashMap();
        List<MetaHuman> list = new ArrayList();

        for (Sighting sighting : sightingList) {
            list = MHservice.getAllMetaHumansBySightingID(sighting.getSightingID());
            map.put(sighting, list);
        }

        model.addAttribute("map", map);

        return "sightings";
    }

    @RequestMapping(value = "/displayIndex", method = RequestMethod.GET)
    public String displayIndex(HttpServletRequest request, Model model) {

        List<Sighting> sightingList10 = service.getRecentSightings();
        Map<Sighting, List<MetaHuman>> map = new LinkedHashMap();
        List<MetaHuman> list = new ArrayList();

        for (Sighting sighting : sightingList10) {
            list = MHservice.getAllMetaHumansBySightingID(sighting.getSightingID());
            map.put(sighting, list);
        }

        model.addAttribute("map", map);

        model.addAttribute("sightingList10", sightingList10);

        return "index";
    }

    @RequestMapping(value = "/displaySightingInfo", method = RequestMethod.GET)
    public String displaySightingInfo(HttpServletRequest request, Model model) {

        String sightingIdParameter = request.getParameter("sightingId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        Sighting sighting = service.getSightingById(sightingIdParameter);
        model.addAttribute("sighting", sighting);

        List<MetaHuman> metaHumanList = MHservice.getAllMetaHumansBySightingID(sightingIdParameter);
        model.addAttribute("metaHumanList", metaHumanList);

        return "viewSighting";
    }

    @RequestMapping(value = "/displayEditSightingForm", method = RequestMethod.GET)
    public String displayEditSightingForm(HttpServletRequest request, Model model) {

        String sightingIdParameter = request.getParameter("sightingId");
        Sighting sighting = service.getSightingById(sightingIdParameter);
        model.addAttribute("sighting", sighting);

        List<MetaHuman> metaHumanList = MHservice.getAllMetaHumans();
        model.addAttribute("metaHumanList", metaHumanList);

        List<MetaHuman> sightingMetaHumanList = MHservice.getAllMetaHumansBySightingID(sightingIdParameter);
        model.addAttribute("sightingMetaHumanList", sightingMetaHumanList);

        List<Location> locationList = locService.getAllLocations();
        model.addAttribute("locationList", locationList);

        return "editSighting";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {

        String sightingIdParameter = request.getParameter("sightingId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        service.deleteSighting(sightingIdParameter);

        return "redirect:sightings";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(@Valid @ModelAttribute("sighting") Sighting sighting, BindingResult result,
            @RequestParam("locSelect") String locID, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "editSighting";
        }

        sighting.setLocation(locService.getLocationById(locID));
        service.updateSighting(sighting);

        List<MetaHuman> sightingMetaHumanList = MHservice.getAllMetaHumansBySightingID(sighting.getSightingID()); //original list of MHs sighting had

        String[] selectedMHIDArray = request.getParameterValues("MHselect");
        List<MetaHuman> newMetaHumanList = new ArrayList();

        for (String currentMHID : selectedMHIDArray) {
            newMetaHumanList.add(MHservice.getMetaHumanById(currentMHID));
        }

        for (MetaHuman currMH : newMetaHumanList) {
            if (!sightingMetaHumanList.stream().anyMatch(mH -> mH.getMetaHumanID().equals(currMH.getMetaHumanID()))) {
                MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();
                bridge.setSighting(sighting);
                MetaHuman mH = MHservice.getMetaHumanById(currMH.getMetaHumanID());
                bridge.setMetaHuman(mH);
                MHSightingBridgeService.addMetaHumanSightingBridge(bridge);
            }
        }

        for (MetaHuman currentMH : sightingMetaHumanList) {
            if (!newMetaHumanList.stream().anyMatch(mH -> mH.getMetaHumanID().equals(currentMH.getMetaHumanID()))) {
                MetaHumanSightingBridge toDelete = MHSightingBridgeService.getMetaHumanSightingBridgeByMetaHumanAndSightingId(currentMH.getMetaHumanID(), sighting.getSightingID());
                MHSightingBridgeService.deleteMetaHumanSightingBridge(toDelete.getMetaHumanSightingBridgeID());
            }
        }

        return "redirect:sightings";
    }

    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(HttpServletRequest request) {

        Sighting sighting = new Sighting();
        sighting.setName(request.getParameter("name"));
        sighting.setLocation(locService.getLocationById(request.getParameter("location")));

        LocalDate ld = LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        sighting.setDate(ld);

        service.addSighting(sighting);

        String[] MHArray = request.getParameterValues("metaHuman");

        MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();

        for (String currentMHID : MHArray) { /////////////////////////////////////////////////////////////// DOESN'T NEGATE DUPLICATES
            MetaHuman metaHuman = MHservice.getMetaHumanById(currentMHID);

            bridge.setMetaHuman(metaHuman);
            bridge.setSighting(sighting);

            MHSightingBridgeService.addMetaHumanSightingBridge(bridge);
        }

        return "redirect:sightings";
    }

    @RequestMapping(value = "/displayCreateSightingForm", method = RequestMethod.GET)
    public String displayCreateSightingForm(HttpServletRequest request, Model model) {

        List<MetaHuman> metaHumanList = MHservice.getAllMetaHumans();
        model.addAttribute("metaHumanList", metaHumanList);

        List<Location> locationList = locService.getAllLocations();
        model.addAttribute("locationList", locationList);

        return "createSighting";
    }

}
