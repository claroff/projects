/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.controller;

import com.sg.superheroes.service.LocationService;
import com.sg.superheroes.model.Location;
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

/**
 *
 * @author chandler
 */
@Controller
public class LocationController {

    LocationService service;

    @Inject
    public LocationController(LocationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public String displayAllLocations(HttpServletRequest request, Model model) {

        List<Location> locationList = service.getAllLocations();

        model.addAttribute("locationList", locationList);

        return "locations";
    }

    @RequestMapping(value = "/displayLocationInfo", method = RequestMethod.GET)
    public String displayLocationInfo(HttpServletRequest request, Model model) {

        String locationIdParameter = request.getParameter("locationId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        Location location = service.getLocationById(locationIdParameter);
        model.addAttribute("location", location);

        return "viewLocation";
    }

    @RequestMapping(value = "/displayEditLocationForm", method = RequestMethod.GET)
    public String displayEditOrganizationForm(HttpServletRequest request, Model model) {

        String locationIdParameter = request.getParameter("locationId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        Location location = service.getLocationById(locationIdParameter);
        model.addAttribute("location", location);

        return "editLocation";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {

        String locationIdParameter = request.getParameter("locationId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        service.deleteLocation(locationIdParameter);

        return "redirect:locations";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("location") Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "editLocation";
        }

        service.updateLocation(location);

        return "redirect:locations";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(HttpServletRequest request) {

        Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));

        service.addLocation(location);

        return "redirect:locations";
    }

    @RequestMapping(value = "/displayCreateLocationForm", method = RequestMethod.GET)
    public String displayCreateLocationForm(HttpServletRequest request, Model model) {

        return "createLocation";
    }

}
