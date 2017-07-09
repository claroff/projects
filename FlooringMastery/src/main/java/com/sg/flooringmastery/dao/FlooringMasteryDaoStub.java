/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chandler
 */
public class FlooringMasteryDaoStub implements FlooringMasteryDao {

    FlooringMasteryOrderFileDaoStub orderFileDao;

    FlooringMasteryDaoStub(FlooringMasteryOrderFileDaoStub orderFileDao) {
        this.orderFileDao = orderFileDao;
    }

    List<Order> orderList = new ArrayList<>();

    public static final String DELIMITER = ",";

    @Override
    public void removeOrder(String orderDate, Integer orderNumber, boolean userSure, boolean mode) throws FlooringMasteryPersistenceException {
        List<Order> list;
        try {
            list = orderFileDao.loadOrderFile(orderDate);
            try {
                Order order = getOrder(orderNumber, list);
                //boolean userSure = view.removeCheck();
                if (userSure) {
                    if (mode) {
                        //view.displayTrainingRemove();
                    } else {
                        if (list.size() == 1) {
                            orderFileDao.deleteOrderFile(list);
                        } else {
                            list.remove(order);
                            orderFileDao.writeOrderFileFull(list);
                        }
                    }
                }
            } catch (Exception e) {
                try {
                    list = getOrderList();
                    Order order = getOrder(orderNumber, list);
                    //boolean userSure = view.removeCheck();
                    if (userSure) {
                        list.remove(order);
                    }
                } catch (Exception ex) {
                    //view.displayOrderNotFound();
                }
            }

        } catch (FlooringMasteryPersistenceException e) {
            try {
                list = getOrderList();
                Order order = getOrder(orderNumber, list);
                //boolean userSure = view.removeCheck();
                if (userSure) {
                    list.remove(order);
                }
            } catch (Exception ex) {
                //view.displayOrderNotFound();
            }
        }
    }

    @Override
    public Order getOrder(Integer orderNumber, List<Order> list) {
        for (Order currentOrder : list) {
            if (currentOrder.getOrderNumber() == orderNumber) {
                Order tempOrder = currentOrder;
                //list.remove(currentOrder); MIGHT BREAK SOMETHING UNLESS RESTORED
                return tempOrder;
            }
        }
        return null;
    }

    @Override
    public List getOrderList() {
        return orderList;
    }

    @Override
    public void addOrderToList(Order order) {
        orderList.add(order);
    }

    @Override
    public List<Order> combineLists(String orderDate) {
        List<Order> combinedList = new ArrayList<>();
        List<Order> list = new ArrayList<>();
        try {
            list = orderFileDao.loadOrderFile(orderDate);
        } catch (Exception e) {

        }
        List<Order> memList = getOrderList();
        for (Order order : memList) {
            if (order.getDate().format(DateTimeFormatter.ofPattern("MMddyyyy")).equals(orderDate)) {
                combinedList.add(order);
            }
        }
        for (Order order : list) {
            combinedList.add(order);
        }

        return combinedList;
    }

    @Override
    public void editOrder(String orderDate, List<Order> list, Order order) throws FlooringMasteryPersistenceException {
        //only needed for file writing
    }

    @Override
    public List getOneOrderListFromBothLists(Order order, String orderDate) {
        try {
            return orderFileDao.loadOrderFile(orderDate);
        } catch (FlooringMasteryPersistenceException e) {
            return getOrderList();
        }
    }

}
