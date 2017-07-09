/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.MoneyDao;
import com.sg.vendingmachinespringmvc.model.Money;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author chandler
 */
@Controller
public class MoneyController {

    MoneyDao dao;

    @Inject
    public MoneyController(MoneyDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/addDollar", method = RequestMethod.GET)
    public String displayDollarPage(Model model) {

        Money money = dao.getMoney();
        dao.addDollar(money);

        model.addAttribute("money", money);

        return "redirect:displayItemsPage";
    }

    @RequestMapping(value = "/addQuarter", method = RequestMethod.GET)
    public String displayQuarterPage(Model model) {

        Money money = dao.getMoney();
        dao.addQuarter(money);

        model.addAttribute("money", money);

        return "redirect:displayItemsPage";
    }

    @RequestMapping(value = "/addDime", method = RequestMethod.GET)
    public String displayDimePage(Model model) {

        Money money = dao.getMoney();
        dao.addDime(money);

        model.addAttribute("money", money);

        return "redirect:displayItemsPage";
    }

    @RequestMapping(value = "/addNickel", method = RequestMethod.GET)
    public String displayNickelPage(Model model) {

        Money money = dao.getMoney();
        dao.addNickel(money);

        model.addAttribute("money", money);

        return "redirect:displayItemsPage";
    }

}
