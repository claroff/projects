/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderNumDao;
import com.sg.flooringmastery.dao.FlooringMasteryOrderNumDaoFileImpl;
import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class FlooringMasteryOrderCalcServiceTest {

    FlooringMasteryOrderNumDao dao;
    FlooringMasteryOrderCalcService service;

    public FlooringMasteryOrderCalcServiceTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("orderNumDaoStub", FlooringMasteryOrderNumDao.class);
        service = ctx.getBean("orderCalcService", FlooringMasteryOrderCalcService.class);
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
     * Test of calcMaterialCost method, of class
     * FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcMaterialCost() {

        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(bd);
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

        service.calcMaterialCost(order);
        bd = new BigDecimal("13.99");
        assertEquals(order.getMatCost(), bd);
    }

    /**
     * Test of calcLaborCost method, of class FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcLaborCost() {
        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(bd);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9999-09-09"));
        order.setLaborCost(BigDecimal.ONE);
        order.setLaborCostSqFt(bd);
        order.setMatCost(BigDecimal.ONE);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(BigDecimal.TEN);
        order.setTaxRate(BigDecimal.ONE);
        order.setTotalCost(BigDecimal.TEN);

        service.calcLaborCost(order);
        bd = new BigDecimal("13.99");
        assertEquals(order.getLaborCost(), bd);
    }

    /**
     * Test of calcTax method, of class FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcTax() {
        //(order.getMatCost().add(order.getLaborCost()).multiply(order.getTaxRate().divide(bd)));
        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(bd);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9999-09-09"));
        order.setLaborCost(bd);
        order.setLaborCostSqFt(bd);
        order.setMatCost(bd);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(BigDecimal.TEN);
        order.setTaxRate(bd);
        order.setTotalCost(BigDecimal.TEN);

        service.calcTax(order);
        bd = new BigDecimal("0.28");
        assertEquals(order.getTax(), bd);
    }

    /**
     * Test of calcTotalCost method, of class FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcTotalCost() {
        //(order.getMatCost().add(order.getLaborCost()).add(order.getTax()))
        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(bd);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9999-09-09"));
        order.setLaborCost(bd);
        order.setLaborCostSqFt(bd);
        order.setMatCost(bd);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(bd);
        order.setTaxRate(bd);
        order.setTotalCost(BigDecimal.TEN);

        service.calcTotalCost(order);
        bd = new BigDecimal("11.22");
        assertEquals(order.getTotalCost(), bd);

    }

    /**
     * Test of calcCosts method, of class FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcOrderNumber() throws Exception {
//          int orderNum = numDao.loadOrderNumberFile();
//        order.setOrderNumber(orderNum);
//        numDao.writeOrderNumberFile(order);
//        return order;

        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(bd);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9999-09-09"));
        order.setLaborCost(bd);
        order.setLaborCostSqFt(bd);
        order.setMatCost(bd);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(bd);
        order.setTaxRate(bd);
        order.setTotalCost(BigDecimal.TEN);

        int firstNum = dao.loadOrderNumberFile();

        service.calcOrderNumber(order);

        assertEquals(order.getOrderNumber(), firstNum);
        order.setOrderNumber(firstNum - 1);
        dao.writeOrderNumberFile(order);

    }

    /**
     * Test of checkListNumbers method, of class
     * FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCheckListNumbers() throws Exception {
        //feels like no need to test
    }

    /**
     * Test of calcState method, of class FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcState() {
//        order.setState(stateList.get(stateNum).getState());
//        order.setTaxRate(stateList.get(stateNum).getTaxRate());

        List<State> stateList = new ArrayList<>();
        State state = new State();
        state.setState("CA");
        BigDecimal bd = new BigDecimal("7.25");
        state.setTaxRate(bd);
        stateList.add(state);
        Order order = new Order();

        service.calcState(order, 0, stateList);

        assertEquals(order.getState(), "CA");
        assertEquals(order.getTaxRate(), bd);
    }

    /**
     * Test of calcMats method, of class FlooringMasteryOrderCalcService.
     */
    @Test
    public void testCalcMats() {
//        order.setProductType(matList.get(matNum).getType());
//        order.setCostSqFt(matList.get(matNum).getCostSqFt());
//        order.setLaborCostSqFt(matList.get(matNum).getLaborCostSqFt());

        Order order = new Order();

        BigDecimal bd = new BigDecimal("3.74");

        order.setArea(bd);
        order.setCostSqFt(BigDecimal.ZERO);
        order.setCustomerName("TestName");
        order.setDate(LocalDate.parse("9999-09-09"));
        order.setLaborCost(bd);
        order.setLaborCostSqFt(BigDecimal.ZERO);
        order.setMatCost(bd);
        order.setOrderNumber(1);
        order.setProductType("wood");
        order.setState("OH");
        order.setTax(bd);
        order.setTaxRate(bd);
        order.setTotalCost(BigDecimal.TEN);

        Material mat = new Material();
        mat.setType("testMat");
        mat.setCostSqFt(bd);
        mat.setLaborCostSqFt(bd);

        int matNum = 0;

        List<Material> matList = new ArrayList<>();
        matList.add(mat);

        service.calcMats(order, matNum, matList);

        assertEquals(order.getProductType(), "testMat");
        assertEquals(order.getLaborCostSqFt(), bd);
        assertEquals(order.getCostSqFt(), bd);
    }

}
