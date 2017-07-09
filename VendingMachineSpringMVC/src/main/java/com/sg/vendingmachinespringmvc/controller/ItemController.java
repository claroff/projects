/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.ChangeDao;
import com.sg.vendingmachinespringmvc.dao.ItemDao;
import com.sg.vendingmachinespringmvc.dao.MessageDao;
import com.sg.vendingmachinespringmvc.dao.MoneyDao;
import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import com.sg.vendingmachinespringmvc.model.Message;
import com.sg.vendingmachinespringmvc.model.Money;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Chandler
 */
@Controller
public class ItemController {

    ItemDao dao;
    MoneyDao moneyDao;
    ChangeDao changeDao;
    MessageDao messageDao;

    @Inject
    public ItemController(ItemDao dao, MoneyDao moneyDao, ChangeDao changeDao, MessageDao messageDao) {
        this.dao = dao;
        this.moneyDao = moneyDao;
        this.changeDao = changeDao;
        this.messageDao = messageDao;
    }

    @RequestMapping(value = "/displayItemsPage", method = RequestMethod.GET)
    public String displayItemsPage(Model model) {

//        Item testItem = new Item();
//        testItem.setName("Carrots");
//        testItem.setPrice(new BigDecimal("0.50"));
//        testItem.setStock(2);
//        if (dao.getItemById(0) == null) { //TEST CODE DELETE LATER
//            dao.addItem(testItem);
//        }
//
//        Item testItem2 = new Item();
//        testItem2.setName("Test");
//        testItem2.setPrice(new BigDecimal("0.50"));
//        testItem2.setStock(2);
//        if (dao.getItemById(1) == null) { //TEST CODE DELETE LATER
//            dao.addItem(testItem2);
//        }
        List<Item> itemList = dao.getAllItems();

        model.addAttribute("itemList", itemList);

        Money money = moneyDao.getMoney();

        model.addAttribute("money", money);

        Change changeObj = changeDao.getChange();
        String qString = " quarters ";
        if (changeObj.getQuarters() == 1) {
            qString = " quarter ";
        }
        String dString = "dimes";
        if (changeObj.getDimes() == 1) {
            dString = " dime ";
        }
        String nString = " nickels ";
        if (changeObj.getNickels() == 1) {
            nString = " nickel ";
        }
        String pString = " pennies";
        if (changeObj.getPennies() == 1) {
            pString = " penny";
        }
        String quarterData = String.valueOf(changeObj.getQuarters());
        if (changeObj.getQuarters() == 0) {
            qString = "";
            quarterData = "";
        }
        String dimeData = String.valueOf(changeObj.getDimes());
        if (changeObj.getDimes() == 0) {
            dString = "";
            dimeData = "";
        }
        String nickelData = String.valueOf(changeObj.getNickels());
        if (changeObj.getNickels() == 0) {
            nString = "";
            nickelData = "";
        }
        String pennyData = String.valueOf(changeObj.getPennies());
        if (changeObj.getPennies() == 0) {
            pString = "";
            pennyData = "";
        }

        String change = quarterData + qString + dimeData + dString + nickelData + nString + pennyData + pString;

        model.addAttribute("change", change);

        String message = messageDao.getMessage().getMessage();

        model.addAttribute("message", message);

        return "items";
    }

    @RequestMapping(value = "/displayItemDetails", method = RequestMethod.GET)
    public String displayItemDetails(HttpServletRequest request, Model model) {
        String itemIdParameter = request.getParameter("itemId");
        int itemId = Integer.parseInt(itemIdParameter);

        Item item = dao.getItemById(itemId);

        model.addAttribute("item", item);

        List<Item> itemList = dao.getAllItems();

        model.addAttribute("itemList", itemList);

        Money money = moneyDao.getMoney();

        model.addAttribute("money", money);

        String message = messageDao.getMessage().getMessage();

        model.addAttribute("message", message);

        return "itemDetails";
    }

    @RequestMapping(value = "/purchaseItem", method = RequestMethod.POST)
    public String purchaseItem(@ModelAttribute("item") Item item, HttpServletRequest request) {

        Message message = messageDao.getMessage();
        Money money = moneyDao.getMoney();
        int comparison = money.getAmount().compareTo(item.getPrice());

        if (item.getStock() > 0) {

            if (comparison == 0 || comparison == 1) {
                dao.purchaseItem(item);
                moneyDao.purchaseItem(money, item);

                changeDao.makeChange(money);
                moneyDao.clearMoney(money);

                message.setMessage("Thank you!!!");
            } else {
                String moneyShort = item.getPrice().subtract(money.getAmount()).toString();
                message.setMessage("Please deposit: $" + moneyShort);
            }
        } else {
            message.setMessage("SOLD OUT!!!");
        }
        return "redirect:displayItemsPage";
    }
}
