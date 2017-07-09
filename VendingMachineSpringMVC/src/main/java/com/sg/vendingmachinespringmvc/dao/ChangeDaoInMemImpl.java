/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Money;
import java.math.BigDecimal;

/**
 *
 * @author chandler
 */
public class ChangeDaoInMemImpl implements ChangeDao {
    
    Change onlyChange = new Change();
    
    @Override
    public Change makeChange(Money money) {
        BigDecimal amount = money.getAmount();
        
        while (amount.compareTo(new BigDecimal("0.25")) == 1 || (amount.compareTo(new BigDecimal("0.25")) == 0)) {
            onlyChange.setQuarters(onlyChange.getQuarters() + 1);
            amount = amount.subtract(new BigDecimal("0.25"));
        }
        while (amount.compareTo(new BigDecimal("0.10")) == 1 || (amount.compareTo(new BigDecimal("0.10")) == 0)) {
            onlyChange.setDimes(onlyChange.getDimes() + 1);
            amount = amount.subtract(new BigDecimal("0.10"));
        }
        while (amount.compareTo(new BigDecimal("0.05")) == 1 || (amount.compareTo(new BigDecimal("0.05")) == 0)) {
            onlyChange.setNickels(onlyChange.getNickels() + 1);
            amount = amount.subtract(new BigDecimal("0.05"));
        }
        while (amount.compareTo(new BigDecimal("0.01")) == 1 || (amount.compareTo(new BigDecimal("0.01")) == 0)) {
            onlyChange.setPennies(onlyChange.getPennies() + 1);
            amount = amount.subtract(new BigDecimal("0.01"));
        }
        return onlyChange;
    }
    
    @Override
    public void clearChange() {
        onlyChange.setQuarters(0);
        onlyChange.setDimes(0);
        onlyChange.setNickels(0);
        onlyChange.setPennies(0);
    }
    
    @Override
    public Change getChange() {
        return onlyChange;
    }
    
}
