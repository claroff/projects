/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class FlooringMasteryOrderNumDaoTest {

    FlooringMasteryOrderNumDao dao;

    public FlooringMasteryOrderNumDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("orderNumDaoStub", FlooringMasteryOrderNumDao.class);
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
     * Test of writeOrderNumberFile method, of class FlooringMasteryOrderNumDao.
     */
    @Test
    public void testWriteLoadOrderNumberFile() throws Exception {
        int originalNum = dao.loadOrderNumberFile();

        Order order = new Order();

        order.setArea(BigDecimal.ONE);
        order.setCostSqFt(BigDecimal.ONE);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9999-09-09"));
        order.setLaborCost(BigDecimal.ONE);
        order.setLaborCostSqFt(BigDecimal.ONE);
        order.setMatCost(BigDecimal.ONE);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(BigDecimal.TEN);
        order.setTaxRate(BigDecimal.ONE);
        order.setTotalCost(BigDecimal.TEN);

        dao.writeOrderNumberFile(order);
        int newNumber = dao.loadOrderNumberFile();
        assertEquals(newNumber, 2);

        order.setOrderNumber(originalNum - 1);
        dao.writeOrderNumberFile(order);

    }

}
