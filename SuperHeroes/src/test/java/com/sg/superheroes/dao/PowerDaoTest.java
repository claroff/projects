/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Power;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class PowerDaoTest {

    private PowerDao dao;

    public PowerDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        dao = ctx.getBean("PowerDao", PowerDao.class);

        List<Power> powers = dao.getAllPowers();
        for (Power currentPower : powers) {
            dao.deletePower(currentPower.getPowerID());
        }
    }

    @After
    public void tearDown() {
        List<Power> powers = dao.getAllPowers();
        for (Power currentPower : powers) {
            dao.deletePower(currentPower.getPowerID());
        }

    }

    /**
     * Test of addPower method, of class PowerDao.
     */
    @Test
    public void testAddGetDeletePower() {
        Power power = new Power();
        power.setDescription("Super duper power");

        dao.addPower(power);

        Power fromDb = dao.getPowerById(power.getPowerID());
        assertEquals(fromDb.getPowerID(), power.getPowerID());

        dao.deletePower(power.getPowerID());

        assertNull(dao.getPowerById(power.getPowerID()));
    }

    /**
     * Test of updatePower method, of class PowerDao.
     */
    @Test
    public void testAddUpdatePower() {
        Power power = new Power();
        power.setDescription("Super duper power");

        dao.addPower(power);

        String powerName = "Super duper power";
        assertEquals(powerName, dao.getPowerById(power.getPowerID()).getDescription());

        String newPower = "Banana Power";
        power.setDescription(newPower);
        dao.updatePower(power);

        Power fromDb = dao.getPowerById(power.getPowerID());
        assertEquals(fromDb.getDescription(), newPower);
    }

    /**
     * Test of getAllPowers method, of class PowerDao.
     */
    @Test
    public void testGetAllPowers() {

        Power power = new Power();
        power.setDescription("Super duper power");

        dao.addPower(power);

        Power power2 = new Power();
        power.setDescription("Banana power");

        dao.addPower(power2);

        List<Power> powList = dao.getAllPowers();
        assertEquals(powList.size(), 2);
    }

}
