/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

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
public class MetaHumanPowerBridgeDaoTest {

    private MetaHumanPowerBridgeDao dao;
    private MetaHumanDao MHdao;
    private PowerDao powDao;

    public MetaHumanPowerBridgeDaoTest() {
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

        dao = ctx.getBean("MetaHumanPowerBridgeDao", MetaHumanPowerBridgeDao.class);
        MHdao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);
        powDao = ctx.getBean("PowerDao", PowerDao.class);

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Power> powers = powDao.getAllPowers();
        for (Power currentPower : powers) {
            powDao.deletePower(currentPower.getPowerID());
        }

    }

    @After
    public void tearDown() {
        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Power> powers = powDao.getAllPowers();
        for (Power currentPower : powers) {
            powDao.deletePower(currentPower.getPowerID());
        }
    }

    /**
     * Test of addMetaHumanPowerBridge method, of class MetaHumanPowerBridgeDao.
     */
    @Test
    public void testAddDeleteMetaHumanPowerBridgeGetByMetaHumanId() {
        MetaHumanPowerBridge bridge = new MetaHumanPowerBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHdao.getMetaHumanById(metaHuman.getMetaHumanID()));

        Power power = new Power();
        power.setDescription("Super duper power");

        powDao.addPower(power);
        bridge.setPower(powDao.getPowerById(power.getPowerID()));

        dao.addMetaHumanPowerBridge(bridge);

        MetaHumanPowerBridge fromDb = dao.getMetaHumanPowerBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanPowerBridgeID(), bridge.getMetaHumanPowerBridgeID());

        dao.deleteMetaHumanPowerBridge(bridge.getMetaHumanPowerBridgeID());
        MHdao.deleteMetaHuman(metaHuman.getMetaHumanID());
        powDao.deletePower(power.getPowerID());

        assertNull(dao.getMetaHumanPowerBridgeByMetaHumanId(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of getMetaHumanPowerBridgeByPowerId method, of class
     * MetaHumanPowerBridgeDao.
     */
    @Test
    public void testAddDeleteMetaHumanPowerBridgeGetByPowerId() {

        MetaHumanPowerBridge bridge = new MetaHumanPowerBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHdao.getMetaHumanById(metaHuman.getMetaHumanID()));

        Power power = new Power();
        power.setDescription("Super duper power");

        powDao.addPower(power);
        bridge.setPower(powDao.getPowerById(power.getPowerID()));

        dao.addMetaHumanPowerBridge(bridge);

        MetaHumanPowerBridge fromDb = dao.getMetaHumanPowerBridgeByPowerId(power.getPowerID());
        assertEquals(fromDb.getMetaHumanPowerBridgeID(), bridge.getMetaHumanPowerBridgeID());

        dao.deleteMetaHumanPowerBridge(bridge.getMetaHumanPowerBridgeID());
        MHdao.deleteMetaHuman(metaHuman.getMetaHumanID());
        powDao.deletePower(power.getPowerID());

        assertNull(dao.getMetaHumanPowerBridgeByMetaHumanId(power.getPowerID()));
    }
}
