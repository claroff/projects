/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderFileDao;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class FlooringMasteryOrderFileServiceTest {

    FlooringMasteryOrderFileDao orderFileDao;
    FlooringMasteryOrderFileService service;

    public FlooringMasteryOrderFileServiceTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        orderFileDao = ctx.getBean("orderFileDaoStub", FlooringMasteryOrderFileDao.class);
        service = ctx.getBean("orderFileService", FlooringMasteryOrderFileService.class);
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
     * Test of writeOrderFile method, of class FlooringMasteryOrderFileService.
     */
    @Test
    public void testWriteLoadOrderFile() throws Exception {
        //no need, just calls dao
    }

    /**
     * Test of splitList method, of class FlooringMasteryOrderFileService.
     */
    @Test
    public void testSplitList() throws Exception {

        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(bd);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9998-09-09"));
        order.setLaborCost(BigDecimal.ONE);
        order.setLaborCostSqFt(BigDecimal.ONE);
        order.setMatCost(BigDecimal.ONE);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(BigDecimal.TEN);
        order.setTaxRate(BigDecimal.ONE);
        order.setTotalCost(BigDecimal.TEN);

        Order order2 = new Order();

        order2.setArea(bd);
        order2.setCostSqFt(bd);
        order2.setCustomerName("TestName");
        order2.setDate(LocalDate.parse("9999-09-09"));
        order2.setLaborCost(BigDecimal.ONE);
        order2.setLaborCostSqFt(BigDecimal.ONE);
        order2.setMatCost(BigDecimal.ONE);
        order2.setOrderNumber(2);
        order2.setProductType("wood");
        order2.setState("OH");
        order2.setTax(BigDecimal.TEN);
        order2.setTaxRate(BigDecimal.ONE);
        order2.setTotalCost(BigDecimal.TEN);

        List<Order> orderList = new ArrayList<>();

        orderList.add(order);
        orderList.add(order2);

        List<Order> splitList = service.splitList(orderList);

        assertEquals(splitList.size(), 1);

    }

//    @Test
//    public void testSplitListEdit() throws Exception {
//
//    }
    @Test
    public void testSwitchMode() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        if (modeSwitch) {
//            this.orderFileDao = ctx.getBean("orderTrainingDao", FlooringMasteryOrderFileDao.class);
//        } else {
//            this.orderFileDao = ctx.getBean("orderFileDao", FlooringMasteryOrderFileDao.class);
//        }

        FlooringMasteryOrderFileDao originalDao = this.service.getOrderFileDao();

        service.switchMode(true);

        assertNotEquals(originalDao, this.service.getOrderFileDao());

        FlooringMasteryOrderFileDao trainingDao = this.service.getOrderFileDao();

        service.switchMode(false);

        assertNotEquals(trainingDao, this.service.getOrderFileDao());
        assertNotEquals(trainingDao, originalDao);
    }

}
