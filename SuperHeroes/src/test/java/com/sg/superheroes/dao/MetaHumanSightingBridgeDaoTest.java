/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanSightingBridge;
import com.sg.superheroes.model.Sighting;
import java.time.LocalDate;
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
public class MetaHumanSightingBridgeDaoTest {

    private MetaHumanSightingBridgeDao dao;
    private MetaHumanDao MHdao;
    private SightingDao sDao;
    private LocationDao locDao;

    public MetaHumanSightingBridgeDaoTest() {
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

        dao = ctx.getBean("MetaHumanSightingBridgeDao", MetaHumanSightingBridgeDao.class);
        MHdao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);
        sDao = ctx.getBean("SightingDao", SightingDao.class);
        locDao = ctx.getBean("LocationDao", LocationDao.class);

        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sDao.deleteSighting(currentSighting.getSightingID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

    }

    @After
    public void tearDown() {

        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sDao.deleteSighting(currentSighting.getSightingID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

    }

    /**
     * Test of addMetaHumanSightingBridge method, of class
     * MetaHumanSightingBridgeDao.
     */
    @Test
    public void testAddDeleteMetaHumanSightingBridgeGetByMetaHumanID() {

        MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHdao.getMetaHumanById(metaHuman.getMetaHumanID()));

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        sDao.addSighting(sighting);

        bridge.setSighting(sDao.getSightingById(sighting.getSightingID()));

        dao.addMetaHumanSightingBridge(bridge);

        MetaHumanSightingBridge fromDb = dao.getMetaHumanSightingBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanSightingBridgeID(), bridge.getMetaHumanSightingBridgeID());

        dao.deleteMetaHumanSightingBridge(bridge.getMetaHumanSightingBridgeID());
        MHdao.deleteMetaHuman(metaHuman.getMetaHumanID());
        sDao.deleteSighting(sighting.getSightingID());
        locDao.deleteLocation(sighting.getLocation().getLocationID());

        assertNull(dao.getMetaHumanSightingBridgeByMetaHumanId(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of deleteMetaHumanSightingBridge method, of class
     * MetaHumanSightingBridgeDao.
     */
    @Test
    public void testAddDeleteMetaHumanSightingBridgeGetBySightingID() {

        MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHdao.getMetaHumanById(metaHuman.getMetaHumanID()));

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        sDao.addSighting(sighting);

        bridge.setSighting(sDao.getSightingById(sighting.getSightingID()));

        dao.addMetaHumanSightingBridge(bridge);

        MetaHumanSightingBridge fromDb = dao.getMetaHumanSightingBridgeBySightingId(sighting.getSightingID());
        assertEquals(fromDb.getMetaHumanSightingBridgeID(), bridge.getMetaHumanSightingBridgeID());

        dao.deleteMetaHumanSightingBridge(bridge.getMetaHumanSightingBridgeID());
        MHdao.deleteMetaHuman(metaHuman.getMetaHumanID());
        sDao.deleteSighting(sighting.getSightingID());
        locDao.deleteLocation(sighting.getLocation().getLocationID());

        assertNull(dao.getMetaHumanSightingBridgeBySightingId(sighting.getSightingID()));
    }

}
