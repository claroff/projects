/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import com.sg.vendingmachinespringmvc.model.Money;
import java.math.BigDecimal;

/**
 *
 * @author chandler
 */
public class MoneyDaoInMemImpl implements MoneyDao {

    Money onlyMoney = new Money();

    @Override
    public Money addDollar(Money money) {
        onlyMoney.setAmount(money.getAmount().add(BigDecimal.ONE));
        return money;
    }

    @Override
    public Money addQuarter(Money money) {
        onlyMoney.setAmount(money.getAmount().add(new BigDecimal("0.25")));
        return money;
    }

    @Override
    public Money addNickel(Money money) {
        onlyMoney.setAmount(money.getAmount().add(new BigDecimal("0.05")));
        return money;
    }

    @Override
    public Money addDime(Money money) {
        onlyMoney.setAmount(money.getAmount().add(new BigDecimal("0.10")));
        return money;
    }

    @Override
    public Money clearMoney(Money money) {
        onlyMoney.setAmount(BigDecimal.ZERO);
        return money;
    }

    @Override
    public Money getMoney() {
        return onlyMoney;
    }

    @Override
    public Money purchaseItem(Money money, Item item) {
        onlyMoney.setAmount(money.getAmount().subtract(item.getPrice()));
        return money;
    }

}
