/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class FlooringMasteryDaoTest {

    FlooringMasteryOrderFileDao orderFileDao;
    FlooringMasteryDao dao;

    public FlooringMasteryDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        orderFileDao = ctx.getBean("orderFileDaoStub", FlooringMasteryOrderFileDao.class);
        dao = ctx.getBean("daoStub", FlooringMasteryDao.class);
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
     * Test of addOrderToList method, of class FlooringMasteryDao.
     */
    @Test
    public void testAddGetOrder() {
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

        dao.addOrderToList(order);
        List<Order> listFromDao = dao.getOrderList();

        Order fromDao = dao.getOrder(1, listFromDao);

        assertEquals(fromDao, order);
    }

//    /**
//     * Test of removeOrder method, of class FlooringMasteryDao.
//     */
//    @Test
//    public void testRemoveOrder() throws Exception { //not working yet
//
//        Order order = new Order();
//
//        order.setArea(BigDecimal.ONE);
//        order.setCostSqFt(BigDecimal.ONE);
//        order.setCustomerName("TestName");
//        order.setDate(LocalDate.parse("9999-09-09"));
//        order.setLaborCost(BigDecimal.ONE);
//        order.setLaborCostSqFt(BigDecimal.ONE);
//        order.setMatCost(BigDecimal.ONE);
//        order.setOrderNumber(1);
//        order.setProductType("wood");
//        order.setState("OH");
//        order.setTax(BigDecimal.TEN);
//        order.setTaxRate(BigDecimal.ONE);
//        order.setTotalCost(BigDecimal.TEN);
//
//        dao.addOrderToList(order);
//        List<Order> listFromDao = dao.getOrderList();
//        int size = listFromDao.size();
//
//        assertEquals(1, size);
//
//        //dao.removeOrder(orderDate, orderNumber, userSure, mode);
//        List<Order> listAfterRemove = dao.getOrderList();
//        size = listAfterRemove.size();
//
//        assertEquals(0, size);
//    }
}
