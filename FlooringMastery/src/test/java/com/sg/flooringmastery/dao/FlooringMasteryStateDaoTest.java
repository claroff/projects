/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
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
public class FlooringMasteryStateDaoTest {

    FlooringMasteryStateDao dao;

    public FlooringMasteryStateDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("stateDaoStub", FlooringMasteryStateDao.class);
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
     * Test of writeStates method, of class FlooringMasteryStateDao.
     */
    @Test
    public void testWriteLoadStates() throws Exception {

        List<State> originalList = dao.loadStates();
        List<State> newList = dao.loadStates();

        State state = new State();
        state.setState("OH");
        state.setTaxRate(BigDecimal.ZERO);

        newList.add(state);

        dao.writeStates(newList);

        newList = dao.loadStates();

        assertEquals(originalList.size() + 1, newList.size());

        dao.writeStates(originalList);
    }

}
