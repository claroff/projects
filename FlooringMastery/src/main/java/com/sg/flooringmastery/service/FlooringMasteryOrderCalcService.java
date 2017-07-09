/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.State;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryOrderCalcService {

    public void calcMaterialCost(Order order);

    public void calcLaborCost(Order order);

    public void calcTax(Order order);

    public void calcTotalCost(Order order);

    public Order calcCosts(Order order);

    public Order calcOrderNumber(Order order)
            throws FlooringMasteryPersistenceException;

    public List<Order> checkListNumbers(List<Order> list)
            throws FlooringMasteryPersistenceException;

    public Order calcState(Order order, Integer stateNum, List<State> stateList);

    public Order calcMats(Order order, Integer matNum, List<Material> matList);
}
