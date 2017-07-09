/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryDao {

    public void addOrderToList(Order order);

    public void removeOrder(String orderDate, Integer orderNumber, boolean userSure, boolean mode)
            throws FlooringMasteryPersistenceException;

    public void editOrder(String orderDate, List<Order> list, Order order) throws FlooringMasteryPersistenceException;

    public List getOrderList();

    public List getOneOrderListFromBothLists(Order order, String orderDate) throws FlooringMasteryPersistenceException;

    public Order getOrder(Integer orderNumber, List<Order> list);

    public List<Order> combineLists(String orderDate);

}
