/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryOrderNumDao {

    public void writeOrderNumberFile(Order order)
            throws FlooringMasteryPersistenceException;

    public Integer loadOrderNumberFile()
            throws FlooringMasteryPersistenceException;

}
