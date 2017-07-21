/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.controller;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import com.sg.superheroes.service.OrganizationService;
import com.sg.superheroes.model.Organization;
import com.sg.superheroes.service.LocationService;
import com.sg.superheroes.service.MetaHumanOrganizationBridgeService;
import com.sg.superheroes.service.MetaHumanService;
import java.util.ArrayList;
import java.util.List;
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
public class OrganizationController {

    OrganizationService service;
    MetaHumanService MHservice;
    MetaHumanOrganizationBridgeService MHOrgBridgeService;
    LocationService locService;

    @Inject
    public OrganizationController(OrganizationService service, MetaHumanService MHservice,
            MetaHumanOrganizationBridgeService MHOrgBridgeService, LocationService locService) {
        this.service = service;
        this.MHservice = MHservice;
        this.MHOrgBridgeService = MHOrgBridgeService;
        this.locService = locService;
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    public String displayAllOrganizations(HttpServletRequest request, Model model) {

        List<Organization> organizationList = service.getAllOrganizations();
        model.addAttribute("organizationList", organizationList);

        return "organizations";
    }

    @RequestMapping(value = "/displayOrganizationInfo", method = RequestMethod.GET)
    public String displayOrganizationInfo(HttpServletRequest request, Model model) {

        String organizationIdParameter = request.getParameter("organizationId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        Organization organization = service.getOrganizationById(organizationIdParameter);
        model.addAttribute("organization", organization);

        List<MetaHuman> metaHumanList = MHservice.getAllMetaHumansByOrganizationID(organizationIdParameter);
        model.addAttribute("metaHumanList", metaHumanList);

        return "viewOrganization";
    }

    @RequestMapping(value = "/displayEditOrganizationForm", method = RequestMethod.GET)
    public String displayEditOrganizationForm(HttpServletRequest request, Model model) {

        String organizationIdParameter = request.getParameter("organizationId");

        Organization organization = service.getOrganizationById(organizationIdParameter);
        model.addAttribute("organization", organization);

        List<MetaHuman> metaHumanList = MHservice.getAllMetaHumans();
        model.addAttribute("metaHumanList", metaHumanList);

        List<MetaHuman> orgMetaHumanList = MHservice.getAllMetaHumansByOrganizationID(organizationIdParameter);
        model.addAttribute("orgMetaHumanList", orgMetaHumanList);

        List<Location> locationList = locService.getAllLocations();
        model.addAttribute("locationList", locationList);

        return "editOrganization";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request) {

        String organizationIdParameter = request.getParameter("organizationId");

        service.deleteOrganization(organizationIdParameter);

        return "redirect:organizations";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganization(@Valid @ModelAttribute("organization") Organization organization, BindingResult result,
            HttpServletRequest request, @RequestParam("MHselect") String metaHuman, @RequestParam("locSelect") String locID) {

        if (result.hasErrors()) {
            return "editOrganization";
        }

        organization.setLocation(locService.getLocationById(locID));
        service.updateOrganization(organization);

        List<MetaHuman> orgMetaHumanList = MHservice.getAllMetaHumansByOrganizationID(organization.getOrganizationID()); //original list of MHs org had

        String[] selectedMHIDArray = request.getParameterValues("MHselect");
        List<MetaHuman> newMetaHumanList = new ArrayList();

        for (String currentMHID : selectedMHIDArray) {
            newMetaHumanList.add(MHservice.getMetaHumanById(currentMHID));
        }

        for (MetaHuman currMH : newMetaHumanList) {
            if (!orgMetaHumanList.stream().anyMatch(mH -> mH.getMetaHumanID().equals(currMH.getMetaHumanID()))) {
                MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();
                bridge.setOrganization(organization);
                MetaHuman mH = MHservice.getMetaHumanById(currMH.getMetaHumanID());
                bridge.setMetaHuman(mH);
                MHOrgBridgeService.addMetaHumanOrganizationBridge(bridge);
            }
        }

        for (MetaHuman currentMH : orgMetaHumanList) {
            if (!newMetaHumanList.stream().anyMatch(mH -> mH.getMetaHumanID().equals(currentMH.getMetaHumanID()))) {
                MetaHumanOrganizationBridge toDelete = MHOrgBridgeService.getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(currentMH.getMetaHumanID(), organization.getOrganizationID());
                MHOrgBridgeService.deleteMetaHumanOrganizationBridge(toDelete.getMetaHumanOrganizationBridgeID());
            }
        }

        return "redirect:organizations";
    }

    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createOrganization(HttpServletRequest request) {

        Organization organization = new Organization();
        organization.setName(request.getParameter("name"));
        organization.setDescription(request.getParameter("description"));
        organization.setLocation(locService.getLocationById(request.getParameter("location")));

        service.addOrganization(organization);

        String[] MHArray = request.getParameterValues("metaHuman");

        MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();

        for (String currentMHID : MHArray) { /////////////////////////////////////////////////////////////// DOESN'T NEGATE DUPLICATES
            MetaHuman metaHuman = MHservice.getMetaHumanById(currentMHID);

            bridge.setMetaHuman(metaHuman);
            bridge.setOrganization(organization);

            MHOrgBridgeService.addMetaHumanOrganizationBridge(bridge);
        }

        return "redirect:organizations";
    }

    @RequestMapping(value = "/displayCreateOrganizationForm", method = RequestMethod.GET)
    public String displayCreateOrganizationForm(HttpServletRequest request, Model model) {

        List<MetaHuman> metaHumanList = MHservice.getAllMetaHumans();
        model.addAttribute("metaHumanList", metaHumanList);

        List<Location> locationList = locService.getAllLocations();
        model.addAttribute("locationList", locationList);

        return "createOrganization";
    }

}
