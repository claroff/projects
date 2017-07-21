/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.controller;

import com.sg.superheroes.service.MetaHumanService;
import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import com.sg.superheroes.model.MetaHumanPowerBridge;
import com.sg.superheroes.model.Organization;
import com.sg.superheroes.model.Power;
import com.sg.superheroes.service.MetaHumanOrganizationBridgeService;
import com.sg.superheroes.service.MetaHumanPowerBridgeService;
import com.sg.superheroes.service.OrganizationService;
import com.sg.superheroes.service.PowerService;
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
public class MetaHumanController {

    MetaHumanService service;
    OrganizationService orgService;
    PowerService powService;
    MetaHumanOrganizationBridgeService MHOrgBridgeService;
    MetaHumanPowerBridgeService MHPowBridgeService;

    @Inject
    public MetaHumanController(MetaHumanService service, OrganizationService orgService,
            PowerService powService, MetaHumanOrganizationBridgeService MHOrgBridgeService,
            MetaHumanPowerBridgeService MHPowBridgeService) {
        this.service = service;
        this.orgService = orgService;
        this.powService = powService;
        this.MHOrgBridgeService = MHOrgBridgeService;
        this.MHPowBridgeService = MHPowBridgeService;
    }

    @RequestMapping(value = "/metahumans", method = RequestMethod.GET)
    public String displayAllMetaHumans(HttpServletRequest request, Model model) {

        List<MetaHuman> metaHumanList = service.getAllMetaHumans();

        model.addAttribute("metaHumanList", metaHumanList);

        return "metahumans";
    }

    @RequestMapping(value = "/displayMetaHumanInfo", method = RequestMethod.GET)
    public String displayMetaHumanInfo(HttpServletRequest request, Model model) {

        String metaHumanIdParameter = request.getParameter("metaHumanId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        MetaHuman metaHuman = service.getMetaHumanById(metaHumanIdParameter);
        model.addAttribute("metaHuman", metaHuman);

        List<Organization> organizationList = orgService.getAllOrganizationsByMetaHumanID(metaHumanIdParameter);
        model.addAttribute("organizationList", organizationList);

        List<Power> powerList = powService.getAllPowersByMetaHumanId(metaHumanIdParameter);
        model.addAttribute("powerList", powerList);

        return "viewMetaHuman";
    }

    @RequestMapping(value = "/displayEditMetaHumanForm", method = RequestMethod.GET)
    public String displayEditMetaHumanForm(HttpServletRequest request, Model model) {

        String metaHumanIdParameter = request.getParameter("metaHumanId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        MetaHuman metaHuman = service.getMetaHumanById(metaHumanIdParameter);
        model.addAttribute("metaHuman", metaHuman);

        List<Organization> organizationList = orgService.getAllOrganizations();
        model.addAttribute("organizationList", organizationList);

        List<Organization> metaHumanOrgList = orgService.getAllOrganizationsByMetaHumanID(metaHumanIdParameter);
        model.addAttribute("metaHumanOrgList", metaHumanOrgList);

        List<Power> powerList = powService.getAllPowers();
        model.addAttribute("powerList", powerList);

        List<Power> metaHumanPowList = powService.getAllPowersByMetaHumanId(metaHumanIdParameter);
        model.addAttribute("metaHumanPowList", metaHumanPowList);

        return "editMetaHuman";
    }

    @RequestMapping(value = "/displayCreateMetaHumanForm", method = RequestMethod.GET)
    public String displayCreateMetaHumanForm(HttpServletRequest request, Model model) {

        List<Organization> organizationList = orgService.getAllOrganizations();
        model.addAttribute("organizationList", organizationList);

        List<Power> powerList = powService.getAllPowers();
        model.addAttribute("powerList", powerList);

        return "createMetaHuman";
    }

    @RequestMapping(value = "/deleteMetaHuman", method = RequestMethod.GET)
    public String deleteMetaHuman(HttpServletRequest request) {

        String metaHumanIdParameter = request.getParameter("metaHumanId");
        //int metaHumanId = Integer.parseInt(metaHumanIdParameter);
        service.deleteMetaHuman(metaHumanIdParameter);

        return "redirect:metahumans";
    }

    @RequestMapping(value = "/editMetaHuman", method = RequestMethod.POST)
    public String editMetaHuman(@Valid @ModelAttribute("metaHuman") MetaHuman metaHuman, BindingResult result,
            HttpServletRequest request, @RequestParam("orgSelect") String organization) {

        if (result.hasErrors()) {
            return "editMetaHuman";
        }

        service.updateMetaHuman(metaHuman);

        List<Organization> metaHumanOrgList = orgService.getAllOrganizationsByMetaHumanID(metaHuman.getMetaHumanID()); //original list of orgs MH had

        if (!metaHumanOrgList.isEmpty()) {

            String[] selectedOrgIDArray = request.getParameterValues("orgSelect");
            List<Organization> newOrgList = new ArrayList();

            for (String currentOrgID : selectedOrgIDArray) {
                if (!currentOrgID.equals("0")) {
                    newOrgList.add(orgService.getOrganizationById(currentOrgID));
                }
            }

            if (newOrgList.isEmpty()) {
                for (Organization currentOrg : metaHumanOrgList) {
                    MetaHumanOrganizationBridge toDelete = MHOrgBridgeService.getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(metaHuman.getMetaHumanID(), currentOrg.getOrganizationID());
                    if (toDelete != null) {
                        MHOrgBridgeService.deleteMetaHumanOrganizationBridge(toDelete.getMetaHumanOrganizationBridgeID());
                    }
                }
            }

            //Adds new metaHuman/Organization associations when new organizations from forms exist
            for (Organization currOrg : newOrgList) {
                if (!metaHumanOrgList.stream().anyMatch(org -> org.getOrganizationID().equals(currOrg.getOrganizationID()))) {
                    MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();
                    bridge.setMetaHuman(metaHuman);
                    Organization org = orgService.getOrganizationById(currOrg.getOrganizationID());
                    bridge.setOrganization(org);
                    MHOrgBridgeService.addMetaHumanOrganizationBridge(bridge);
                }

                //Deletes metaHuman/Organization associations when something from the original list is not in the new edited list
                for (Organization currentOrg : metaHumanOrgList) {
                    if (!newOrgList.stream().anyMatch(org -> org.getOrganizationID().equals(currentOrg.getOrganizationID()))) {
                        MetaHumanOrganizationBridge toDelete = MHOrgBridgeService.getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(metaHuman.getMetaHumanID(), currentOrg.getOrganizationID());
                        if (toDelete != null) {
                            MHOrgBridgeService.deleteMetaHumanOrganizationBridge(toDelete.getMetaHumanOrganizationBridgeID());
                        }
                    }
                }
            }
        }

        List<Power> metaHumanPowList = powService.getAllPowersByMetaHumanId(metaHuman.getMetaHumanID()); //original list of pows MH had

        if (!metaHumanPowList.isEmpty()) {

            String[] selectedPowIDArray = request.getParameterValues("powSelect");
            List<Power> newPowList = new ArrayList();

            for (String currentPowID : selectedPowIDArray) {
                if (!currentPowID.equals("0")) {
                    newPowList.add(powService.getPowerById(currentPowID));
                }
            }

            if (newPowList.isEmpty()) {
                for (Power currentPow : metaHumanPowList) {
                    MetaHumanPowerBridge toDelete = MHPowBridgeService.getMetaHumanPowerBridgeByMetaHumanAndPowerId(metaHuman.getMetaHumanID(), currentPow.getPowerID());
                    if (toDelete != null) {
                        MHPowBridgeService.deleteMetaHumanPowerBridge(toDelete.getMetaHumanPowerBridgeID());
                    }
                }
            }

            //Adds new metaHuman/Power associations when new powers from forms exist
            for (Power currPow : newPowList) {
                if (!metaHumanPowList.stream().anyMatch(pow -> pow.getPowerID().equals(currPow.getPowerID()))) {
                    MetaHumanPowerBridge powBridge = new MetaHumanPowerBridge();
                    powBridge.setMetaHuman(metaHuman);
                    Power pow = powService.getPowerById(currPow.getPowerID());
                    powBridge.setPower(pow);
                    MHPowBridgeService.addMetaHumanPowerBridge(powBridge);
                }

                //Deletes metaHuman/Power associations when something from the original list is not in the new edited list
                for (Power currentPow : metaHumanPowList) {
                    if (!newPowList.stream().anyMatch(pow -> pow.getPowerID().equals(currentPow.getPowerID()))) {
                        MetaHumanPowerBridge toDelete = MHPowBridgeService.getMetaHumanPowerBridgeByMetaHumanAndPowerId(metaHuman.getMetaHumanID(), currentPow.getPowerID());
                        if (toDelete != null) {
                            MHPowBridgeService.deleteMetaHumanPowerBridge(toDelete.getMetaHumanPowerBridgeID());
                        }
                    }
                }
            }
        }
        return "redirect:metahumans";
    }

    @RequestMapping(value = "/createMetaHuman", method = RequestMethod.POST)
    public String createMetaHuman(HttpServletRequest request) {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName(request.getParameter("name"));
        metaHuman.setIdentity(request.getParameter("identity"));
        service.addMetaHuman(metaHuman);

        String[] orgArray = request.getParameterValues("organization");

        MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();

        for (String currentOrgID : orgArray) {
            Organization org = orgService.getOrganizationById(currentOrgID);

            bridge.setMetaHuman(metaHuman);
            bridge.setOrganization(org);

            MHOrgBridgeService.addMetaHumanOrganizationBridge(bridge);

        }

        String[] powerArray = request.getParameterValues("power");
        MetaHumanPowerBridge powBridge = new MetaHumanPowerBridge();

        for (String currentPowerID : powerArray) {
            Power power = powService.getPowerById(currentPowerID);

            powBridge.setMetaHuman(metaHuman);
            powBridge.setPower(power);

            MHPowBridgeService.addMetaHumanPowerBridge(powBridge);
        }

        return "redirect:metahumans";
    }
}
