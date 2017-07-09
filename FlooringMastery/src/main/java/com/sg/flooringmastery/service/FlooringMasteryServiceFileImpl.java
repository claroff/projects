/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryOrderFileDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class FlooringMasteryServiceFileImpl implements FlooringMasteryService {

    FlooringMasteryDao dao;

    public FlooringMasteryServiceFileImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    @Override
    public List getOrderList() {
        return dao.getOrderList();
    }

    @Override
    public void addOrderToList(Order order) {
        dao.addOrderToList(order);
    }

    @Override
    public void removeOrder(String orderDate, Integer orderNumber, boolean userSure, boolean mode) throws FlooringMasteryPersistenceException {
        dao.removeOrder(orderDate, orderNumber, userSure, mode);
    }

    @Override
    public Order getOrder(Integer orderNumber, List<Order> list) {
        return dao.getOrder(orderNumber, list);
    }

    @Override
    public List<Order> combineLists(String orderDate) {
        return dao.combineLists(orderDate);
    }

    @Override
    public void editOrder(String orderDate, List<Order> list, Order order) throws FlooringMasteryPersistenceException {
        dao.editOrder(orderDate, list, order);
    }

    @Override
    public List getOneOrderListFromBothLists(Order order, String orderDate) throws FlooringMasteryPersistenceException {
        return dao.getOneOrderListFromBothLists(order, orderDate);
    }

}
