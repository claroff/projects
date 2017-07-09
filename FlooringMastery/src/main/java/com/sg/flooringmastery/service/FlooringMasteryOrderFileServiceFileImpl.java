/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderFileDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class FlooringMasteryOrderFileServiceFileImpl implements FlooringMasteryOrderFileService {

    FlooringMasteryOrderFileDao orderFileDao;

    public FlooringMasteryOrderFileServiceFileImpl(FlooringMasteryOrderFileDao orderFileDao) {
        this.orderFileDao = orderFileDao;
    }

    @Override
    public void writeOrderFile(List<Order> orderList) throws FlooringMasteryPersistenceException {
        orderFileDao.writeOrderFile(orderList);
    }

    @Override
    public void writeOrderFileFull(List<Order> orderList) throws FlooringMasteryPersistenceException {
        orderFileDao.writeOrderFileFull(orderList);
    }

    @Override
    public List<Order> loadOrderFile(String date) throws FlooringMasteryPersistenceException {
        // try {
        return orderFileDao.loadOrderFile(date);
        //} catch (FlooringMasteryPersistenceException e) {
//            List<Order> tempList = new ArrayList<>();
//            List<Order> list = dao.getOrderList();
//            for (Order order : list) {
//                if (order.getDate().format(DateTimeFormatter.ofPattern("MMddyyyy")).equals(date)) {
//                    tempList.add(order);
//                }
//            }
//            return tempList;
        //}
    }

    @Override
    public List splitList(List<Order> list) throws FlooringMasteryPersistenceException {
        List<Order> tempList = new ArrayList<>();

        LocalDate firstDate = list.get(0).getDate();
        Iterator<Order> iterator = list.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (firstDate.compareTo(order.getDate()) == 0) {
                tempList.add(order);
                iterator.remove();
            }
        }
        return tempList;
    }

//    @Override
//    public List splitListEdit(List<Order> list) throws FlooringMasteryPersistenceException {
//        List<Order> tempList = new ArrayList<>();
//
//        LocalDate firstDate = list.get(0).getDate();
//        Iterator<Order> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            Order order = iterator.next();
//            if (firstDate.compareTo(order.getDate()) == 0) {
//                tempList.add(order);
//                iterator.remove();
//            }
//        }
//        return tempList;
//    }
    @Override
    public void switchMode(boolean modeSwitch) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        if (modeSwitch) {
            this.orderFileDao = ctx.getBean("orderTrainingDao", FlooringMasteryOrderFileDao.class);
        } else {
            this.orderFileDao = ctx.getBean("orderFileDao", FlooringMasteryOrderFileDao.class);
        }
    }

    @Override
    public FlooringMasteryOrderFileDao getOrderFileDao() {
        return orderFileDao;
    }

    @Override
    public void deleteOrderFile(List<Order> orderList) throws FlooringMasteryPersistenceException {
        orderFileDao.deleteOrderFile(orderList);
    }

}
