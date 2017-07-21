/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

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
public class PowerServiceTest {

    private PowerService service;

    public PowerServiceTest() {
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

        service = ctx.getBean("PowerService", PowerService.class);

        List<Power> powers = service.getAllPowers();
        for (Power currentPower : powers) {
            service.deletePower(currentPower.getPowerID());
        }
    }

    @After
    public void tearDown() {
        List<Power> powers = service.getAllPowers();
        for (Power currentPower : powers) {
            service.deletePower(currentPower.getPowerID());
        }

    }

    /**
     * Test of addPower method, of class PowerService.
     */
    @Test
    public void testAddGetDeletePower() {
        Power power = new Power();
        power.setDescription("Super duper power");

        service.addPower(power);

        Power fromDb = service.getPowerById(power.getPowerID());
        assertEquals(fromDb.getPowerID(), power.getPowerID());

        service.deletePower(power.getPowerID());

        assertNull(service.getPowerById(power.getPowerID()));
    }

    /**
     * Test of updatePower method, of class PowerService.
     */
    @Test
    public void testAddUpdatePower() {
        Power power = new Power();
        power.setDescription("Super duper power");

        service.addPower(power);

        String powerName = "Super duper power";
        assertEquals(powerName, service.getPowerById(power.getPowerID()).getDescription());

        String newPower = "Banana Power";
        power.setDescription(newPower);
        service.updatePower(power);

        Power fromDb = service.getPowerById(power.getPowerID());
        assertEquals(fromDb.getDescription(), newPower);
    }

    /**
     * Test of getAllPowers method, of class PowerService.
     */
    @Test
    public void testGetAllPowers() {

        Power power = new Power();
        power.setDescription("Super duper power");

        service.addPower(power);

        Power power2 = new Power();
        power.setDescription("Banana power");

        service.addPower(power2);

        List<Power> powList = service.getAllPowers();
        assertEquals(powList.size(), 2);
    }

}
