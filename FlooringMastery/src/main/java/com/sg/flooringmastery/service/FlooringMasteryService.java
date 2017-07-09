/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryService {

    public void editOrder(String orderDate, List<Order> list, Order order) throws FlooringMasteryPersistenceException;

    public List getOrderList();

    public List getOneOrderListFromBothLists(Order order, String orderDate) throws FlooringMasteryPersistenceException;

    public void addOrderToList(Order order);

    public Order getOrder(Integer orderNumber, List<Order> list);

    public void removeOrder(String orderDate, Integer orderNumber, boolean userSure, boolean mode)
            throws FlooringMasteryPersistenceException;

    public List<Order> combineLists(String orderDate);

}
