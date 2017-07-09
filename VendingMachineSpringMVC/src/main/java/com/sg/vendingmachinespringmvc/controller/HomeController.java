/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.ItemDao;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;
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
public class HomeController {

//    ItemDao dao;
//
//    @Inject
//    public HomeController(ItemDao dao) {
//        this.dao = dao;
//    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startUp() {

        return "redirect:/displayItemsPage";
    }

}
