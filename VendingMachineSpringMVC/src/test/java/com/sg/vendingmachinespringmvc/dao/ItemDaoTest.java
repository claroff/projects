/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class ItemDaoTest {

    private ItemDao dao;

    public ItemDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("itemDao", ItemDao.class);

        // remove all of the Items
        List<Item> items = dao.getAllItems();
        for (Item currentItem : items) {
            dao.removeItem(currentItem.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addGetDeleteItem() {
        // Create new contact
        Item nc = new Item();
        nc.setName("Carrots");
        nc.setStock(10);
        nc.setPrice(new BigDecimal("4.99"));

        dao.addItem(nc);
        Item fromDb = dao.getItemById(nc.getId());
        assertEquals(fromDb.getId(), nc.getId());
        dao.removeItem(nc.getId());
        assertNull(dao.getItemById(nc.getId()));
    }

    @Test
    public void addUpdateItem() {
        // Create new contact
        Item nc = new Item();
        nc.setName("Carrots");
        nc.setStock(10);
        nc.setPrice(new BigDecimal("4.99"));

        dao.addItem(nc);
        nc.setPrice(new BigDecimal("3.99"));
        dao.updateItem(nc);
        Item fromDb = dao.getItemById(nc.getId());
        assertEquals(fromDb.getId(), nc.getId());
    }

    @Test
    public void getAllItems() {
        // Create new contact
        Item nc = new Item();
        nc.setName("Carrots");
        nc.setStock(10);
        nc.setPrice(new BigDecimal("4.99"));

        dao.addItem(nc);
        // Create new contact
        Item nc2 = new Item();
        nc2.setName("Hummus");
        nc2.setStock(10);
        nc2.setPrice(new BigDecimal("6.99"));

        dao.addItem(nc2);
        List<Item> iList = dao.getAllItems();
        assertEquals(iList.size(), 2);
    }

}
