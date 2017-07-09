/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderNumDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author chandler
 */
public class FlooringMasteryOrderCalcServiceImpl implements FlooringMasteryOrderCalcService {

    FlooringMasteryOrderNumDao numDao;

    public FlooringMasteryOrderCalcServiceImpl(FlooringMasteryOrderNumDao numDao) {
        this.numDao = numDao;
    }

    @Override
    public void calcMaterialCost(Order order) {
        BigDecimal matCost = (order.getCostSqFt().multiply(order.getArea())).setScale(2, RoundingMode.HALF_UP);
        order.setMatCost(matCost);
    }

    @Override
    public void calcLaborCost(Order order) {
        BigDecimal laborCost = (order.getLaborCostSqFt().multiply(order.getArea())).setScale(2, RoundingMode.HALF_UP);
        order.setLaborCost(laborCost);
    }

    @Override
    public void calcTax(Order order) {
        BigDecimal bd = new BigDecimal("100");
        BigDecimal taxCost = (order.getMatCost().add(order.getLaborCost()).multiply(order.getTaxRate().divide(bd))).setScale(2, RoundingMode.HALF_UP);
        order.setTax(taxCost);
    }

    @Override
    public void calcTotalCost(Order order) {
        BigDecimal totalCost = (order.getMatCost().add(order.getLaborCost()).add(order.getTax())).setScale(2, RoundingMode.HALF_UP);
        order.setTotalCost(totalCost);
    }

    @Override
    public Order calcCosts(Order order) {
        calcMaterialCost(order);
        calcLaborCost(order);
        calcTax(order);
        calcTotalCost(order);

        return order;
    }

    @Override
    public List<Order> checkListNumbers(List<Order> list) throws FlooringMasteryPersistenceException {
        for (Order order : list) {
            order = calcOrderNumber(order);
        }
        return list;
    }

    @Override
    public Order calcOrderNumber(Order order) throws FlooringMasteryPersistenceException {
        int orderNum = numDao.loadOrderNumberFile();
        order.setOrderNumber(orderNum);
        numDao.writeOrderNumberFile(order);
        return order;
    }

    @Override
    public Order calcState(Order order, Integer stateNum, List<State> stateList) {
        order.setState(stateList.get(stateNum).getState());
        order.setTaxRate(stateList.get(stateNum).getTaxRate());

        return order;
    }

    @Override
    public Order calcMats(Order order, Integer matNum, List<Material> matList) {
        order.setProductType(matList.get(matNum).getType());
        order.setCostSqFt(matList.get(matNum).getCostSqFt());
        order.setLaborCostSqFt(matList.get(matNum).getLaborCostSqFt());

        return order;
    }

}
