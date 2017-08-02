/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.StaticPage;
import com.sg.capstone.service.ImageService;
import com.sg.capstone.service.StaticPageService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chandler
 */
@Controller
public class StaticPageController {

    private StaticPageService staticPageService;
    private ImageService imageService;

    public StaticPageController(StaticPageService staticPageService, ImageService imageService) {
        this.staticPageService = staticPageService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/displayStaticPage", method = RequestMethod.GET)
    public String displayStaticPage(HttpServletRequest request, Model model) {

        String staticPageIDParameter = request.getParameter("staticPageID");
        Long staticPageID = Long.parseLong(staticPageIDParameter);
        StaticPage staticPage = staticPageService.getStaticPageByID(staticPageID);
        model.addAttribute("staticPage", staticPage);

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

//        model.addAttribute("pageName", request.getParameter("staticPageName"));
        return "staticPage";

    }

    @RequestMapping(value = "/uploadStaticImage", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    //BURKE ADDED THIS FOR TINYMCE
    public Object uploadImage(@RequestParam("file") MultipartFile graphicFile)
            throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("status", "sucess");
        Long imageID = imageService.insertImage(graphicFile.getBytes(), graphicFile.getOriginalFilename());
        String imageString = "/image/" + imageID.toString();
        hashMap.put("location", imageString);

        return hashMap;
    }

    @RequestMapping(value = "/deleteStaticPage", method = RequestMethod.GET)
    public String deleteStaticPage(HttpServletRequest request) {

        String staticPageIDParameter = request.getParameter("staticPageID");
        Long staticPageID = Long.parseLong(staticPageIDParameter);
        staticPageService.deleteStaticPage(staticPageID);

        return "redirect:displayAdminPage"; //UNSURE WHERE TO REDIRECT FOR NOW
    }

    @RequestMapping(value = "/createStaticPage", method = RequestMethod.POST)
    public String createStaticPage(HttpServletRequest request) {

        StaticPage staticPage = new StaticPage();

        staticPage.setStaticPageName(request.getParameter("staticPageName"));
        staticPage.setStaticPageContent(request.getParameter("staticPageContent"));
        Integer navIndex = Integer.parseInt(request.getParameter("navIndex"));
        staticPage.setNavIndex(navIndex);

        staticPageService.addStaticPage(staticPage);

        return "redirect:displayAdminPage"; //UNSURE WHERE TO REDIRECT FOR NOW
    }

    @RequestMapping(value = "/displayCreateStaticPageForm", method = RequestMethod.GET)
    public String displayCreateStaticPageForm(HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        return "staticPageCreate";
    }

    @RequestMapping(value = "/editStaticPage", method = RequestMethod.POST)
    public String editStaticPage(@Valid @ModelAttribute("staticPage") StaticPage staticPage, BindingResult result) {

        if (result.hasErrors()) {
            return "editStaticPage";
        }

        staticPageService.updateStaticPage(staticPage);

        return "redirect:displayAdminPage"; //UNSURE WHERE TO REDIRECT FOR NOW
    }

    @RequestMapping(value = "/displayEditStaticPageForm", method = RequestMethod.GET)
    public String displayEditStaticPageForm(HttpServletRequest request, Model model) {

        String StaticPageIDParameter = request.getParameter("staticPageID");
        Long staticPageID = Long.parseLong(StaticPageIDParameter);

        StaticPage staticPage = staticPageService.getStaticPageByID(staticPageID);
        model.addAttribute("staticPage", staticPage);

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        return "staticPageEdit";
    }

}
