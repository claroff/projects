/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.util.List;

/**
 *
 * @author Chandler
 */
public interface ItemDao {

    public Item addItem(Item item);

    public void removeItem(int itemId);

    public void updateItem(Item item);

    public Item getItemById(int itemId);

    public List<Item> getAllItems();

    public void purchaseItem(Item item);

}
