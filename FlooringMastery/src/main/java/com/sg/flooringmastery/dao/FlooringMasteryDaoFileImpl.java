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
 * @author chandler
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {

    FlooringMasteryOrderFileDao orderFileDao;
    FlooringMasteryStateDao stateDao;
    FlooringMasteryMaterialDao matDao;

    FlooringMasteryDaoFileImpl(FlooringMasteryOrderFileDao orderFileDao, FlooringMasteryStateDao stateDao, FlooringMasteryMaterialDao matDao) {
        this.orderFileDao = orderFileDao;
        this.stateDao = stateDao;
        this.matDao = matDao;
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
                if (order == null) {
                    throw new Exception();
                }
                if (userSure) {
                    if (mode) {
                        //Do nothing in training
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
                    if (userSure) {
                        list.remove(order);
                    }
                } catch (Exception ex) {

                }
            }

        } catch (FlooringMasteryPersistenceException e) {
            try {
                list = getOrderList();
                Order order = getOrder(orderNumber, list);
                if (userSure) {
                    list.remove(order);
                }
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void editOrder(String orderDate, List<Order> list, Order order) throws FlooringMasteryPersistenceException {
        LocalDate dateAsDate = LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("MMddyyyy"));
        Order oldDateOrder = new Order();
        oldDateOrder.setDate(dateAsDate);
        List<Order> oldDayList = new ArrayList<>();
        boolean fromFile = false;
        List<Order> fromMem = getOrderList();

        if (list != orderList) {
            fromFile = true;
        }

        oldDayList.add(oldDateOrder);

        LocalDate oldDate = order.getDate();

        if (fromFile) {
            if (oldDate.equals(order.getDate())) {
                //This handles day unchanged
                orderFileDao.writeOrderFileFull(list);
            } else {
                //IF DAY CHANGED:

                if (list.size() == 1) { //if only order in file delete list
                    orderFileDao.deleteOrderFile(oldDayList);
                    list.remove(order);
                } else {
                    list.remove(order);
                    orderFileDao.writeOrderFileFull(list);
                }

                List<Order> newDayEditedOrder = new ArrayList<>();
                newDayEditedOrder.add(order);
                //  this will be the only order in this list

                orderFileDao.writeOrderFile(newDayEditedOrder);

                //  SAVE NEW DAY (append)
            }

        }

    }

    @Override
    public Order getOrder(Integer orderNumber, List<Order> list) {
        for (Order currentOrder : list) {
            if (currentOrder.getOrderNumber() == orderNumber) {
                Order tempOrder = currentOrder;
                return tempOrder;
            }
        }
        return null;
    }

    @Override
    public List<Order> getOneOrderListFromBothLists(Order order, String orderDate) throws FlooringMasteryPersistenceException {
        if (getOrderList().contains(order)) {
            return getOrderList();
        } else {
            return orderFileDao.loadOrderFile(orderDate);
        }
    }

    @Override
    public List<Order> getOrderList() {
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

}
