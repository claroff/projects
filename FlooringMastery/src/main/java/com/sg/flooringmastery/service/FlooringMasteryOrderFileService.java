/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderFileDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryOrderFileService {

    public void writeOrderFileFull(List<Order> orderList)
            throws FlooringMasteryPersistenceException;

    public void writeOrderFile(List<Order> orderList)
            throws FlooringMasteryPersistenceException;

    public List<Order> loadOrderFile(String date)
            throws FlooringMasteryPersistenceException;

    public List splitList(List<Order> list)
            throws FlooringMasteryPersistenceException;

//    public List splitListEdit(List<Order> list)
//            throws FlooringMasteryPersistenceException;
    public void switchMode(boolean modeSwitch);

    public FlooringMasteryOrderFileDao getOrderFileDao();

    public void deleteOrderFile(List<Order> orderList)
            throws FlooringMasteryPersistenceException;
}
