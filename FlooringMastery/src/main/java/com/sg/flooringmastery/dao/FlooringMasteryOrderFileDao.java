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
public interface FlooringMasteryOrderFileDao {

    public List<Order> loadOrderFile(String date)
            throws FlooringMasteryPersistenceException;

    public void writeOrderFile(List<Order> orderList)
            throws FlooringMasteryPersistenceException;

    public void writeOrderFileFull(List<Order> orderList)
            throws FlooringMasteryPersistenceException;

    public void deleteOrderFile(List<Order> orderList)
            throws FlooringMasteryPersistenceException;
}
