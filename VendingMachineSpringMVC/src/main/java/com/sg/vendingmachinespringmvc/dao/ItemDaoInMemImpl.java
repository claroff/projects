/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chandler
 */
public class ItemDaoInMemImpl implements ItemDao {

    private Map<Integer, Item> itemMap = new HashMap<>();

    private static int itemIdCounter = 0;

    @Override
    public Item addItem(Item item) {
        item.setId(itemIdCounter);
        itemIdCounter++;

        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public void removeItem(int itemId) {
        itemMap.remove(itemId);
    }

    @Override
    public void updateItem(Item item) {
        itemMap.put(item.getId(), item);
    }

    @Override
    public void purchaseItem(Item item) {
        item.setStock(item.getStock() - 1);
        itemMap.put(item.getId(), item);
    }

    @Override
    public List<Item> getAllItems() {
        Collection<Item> i = itemMap.values();
        return new ArrayList(i);
    }

    @Override
    public Item getItemById(int itemId) {
        return itemMap.get(itemId);
    }

}
