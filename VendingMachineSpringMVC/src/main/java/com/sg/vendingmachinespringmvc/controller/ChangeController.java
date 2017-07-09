/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.ChangeDao;
import com.sg.vendingmachinespringmvc.dao.MessageDao;
import com.sg.vendingmachinespringmvc.dao.MoneyDao;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author chandler
 */
@Controller
public class ChangeController {

    ChangeDao dao;
    MessageDao messageDao;
    MoneyDao moneyDao;

    @Inject
    public ChangeController(ChangeDao dao, MessageDao messageDao, MoneyDao moneyDao) {
        this.dao = dao;
        this.messageDao = messageDao;
        this.moneyDao = moneyDao;
    }

    @RequestMapping(value = "/clearChange", method = RequestMethod.GET)
    public String displayItemDetails(HttpServletRequest request, Model model) {
        dao.clearChange();
        messageDao.clearMessage();

        if (!moneyDao.getMoney().getAmount().equals(new BigDecimal("0"))) {
            dao.makeChange(moneyDao.getMoney());
            moneyDao.clearMoney(moneyDao.getMoney());
        }

        return "redirect:displayItemsPage";
    }

}
