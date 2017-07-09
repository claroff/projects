/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Money;

/**
 *
 * @author chandler
 */
public interface ChangeDao {

    public Change getChange();

    public Change makeChange(Money money);

    public void clearChange();

}
