/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class FlooringMasteryOrderFileDaoTest {

    FlooringMasteryOrderFileDao dao;

    public FlooringMasteryOrderFileDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("orderFileDaoStub", FlooringMasteryOrderFileDao.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadOrderFile method, of class FlooringMasteryOrderFileDao.
     */
    @Test
    public void testLoadWriteOrderFileAndFull() throws Exception {

        String date = "01010001";

        List<Order> orderList = dao.loadOrderFile(date);
        List<Order> newList = new ArrayList<>();

        Order order = new Order();

        order.setArea(BigDecimal.ONE);
        order.setCostSqFt(BigDecimal.ONE);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("0001-01-01"));
        order.setLaborCost(BigDecimal.ONE);
        order.setLaborCostSqFt(BigDecimal.ONE);
        order.setMatCost(BigDecimal.ONE);
        order.setOrderNumber(2);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(BigDecimal.TEN);
        order.setTaxRate(BigDecimal.ONE);
        order.setTotalCost(BigDecimal.TEN);

        newList.add(order);

        dao.writeOrderFile(newList);

        newList = dao.loadOrderFile(date);
        assertEquals(newList.size(), orderList.size() + 1);

        dao.writeOrderFileFull(orderList);
        List<Order> fullyWritten = dao.loadOrderFile(date);

        assertEquals(fullyWritten.size(), orderList.size());
    }

}
