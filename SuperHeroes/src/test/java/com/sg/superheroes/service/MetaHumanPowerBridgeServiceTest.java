/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanPowerBridge;
import com.sg.superheroes.model.Power;
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
public class MetaHumanPowerBridgeServiceTest {

    private MetaHumanPowerBridgeService service;
    private MetaHumanService MHservice;
    private PowerService powservice;

    public MetaHumanPowerBridgeServiceTest() {
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

        service = ctx.getBean("MetaHumanPowerBridgeService", MetaHumanPowerBridgeService.class);
        MHservice = ctx.getBean("MetaHumanService", MetaHumanService.class);
        powservice = ctx.getBean("PowerService", PowerService.class);

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Power> powers = powservice.getAllPowers();
        for (Power currentPower : powers) {
            powservice.deletePower(currentPower.getPowerID());
        }

    }

    @After
    public void tearDown() {
        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Power> powers = powservice.getAllPowers();
        for (Power currentPower : powers) {
            powservice.deletePower(currentPower.getPowerID());
        }
    }

    /**
     * Test of addMetaHumanPowerBridge method, of class
     * MetaHumanPowerBridgeservice.
     */
    @Test
    public void testAddDeleteMetaHumanPowerBridgeGetByMetaHumanId() {
        MetaHumanPowerBridge bridge = new MetaHumanPowerBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHservice.getMetaHumanById(metaHuman.getMetaHumanID()));

        Power power = new Power();
        power.setDescription("Super duper power");

        powservice.addPower(power);
        bridge.setPower(powservice.getPowerById(power.getPowerID()));

        service.addMetaHumanPowerBridge(bridge);

        MetaHumanPowerBridge fromDb = service.getMetaHumanPowerBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanPowerBridgeID(), bridge.getMetaHumanPowerBridgeID());

        service.deleteMetaHumanPowerBridge(bridge.getMetaHumanPowerBridgeID());
        MHservice.deleteMetaHuman(metaHuman.getMetaHumanID());
        powservice.deletePower(power.getPowerID());

        assertNull(service.getMetaHumanPowerBridgeByMetaHumanId(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of getMetaHumanPowerBridgeByPowerId method, of class
     * MetaHumanPowerBridgeservice.
     */
    @Test
    public void testAddDeleteMetaHumanPowerBridgeGetByPowerId() {

        MetaHumanPowerBridge bridge = new MetaHumanPowerBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHservice.getMetaHumanById(metaHuman.getMetaHumanID()));

        Power power = new Power();
        power.setDescription("Super duper power");

        powservice.addPower(power);
        bridge.setPower(powservice.getPowerById(power.getPowerID()));

        service.addMetaHumanPowerBridge(bridge);

        MetaHumanPowerBridge fromDb = service.getMetaHumanPowerBridgeByPowerId(power.getPowerID());
        assertEquals(fromDb.getMetaHumanPowerBridgeID(), bridge.getMetaHumanPowerBridgeID());

        service.deleteMetaHumanPowerBridge(bridge.getMetaHumanPowerBridgeID());
        MHservice.deleteMetaHuman(metaHuman.getMetaHumanID());
        powservice.deletePower(power.getPowerID());

        assertNull(service.getMetaHumanPowerBridgeByMetaHumanId(power.getPowerID()));
    }
}
