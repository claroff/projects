/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import com.sg.vendingmachinespringmvc.model.Money;

/**
 *
 * @author chandler
 */
public interface MoneyDao {

    public Money getMoney();

    public Money addDollar(Money money);

    public Money addQuarter(Money money);

    public Money addNickel(Money money);

    public Money addDime(Money money);

    public Money clearMoney(Money money);

    public Money purchaseItem(Money money, Item item);

}
