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
public class LocationDaoTest {

    private LocationDao dao;
    private MetaHumanDao MHdao;
    private SightingDao sDao;
    private MetaHumanSightingBridgeDao mhsbDao;

    public LocationDaoTest() {
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

        dao = ctx.getBean("LocationDao", LocationDao.class);
        MHdao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);
        sDao = ctx.getBean("SightingDao", SightingDao.class);
        mhsbDao = ctx.getBean("MetaHumanSightingBridgeDao", MetaHumanSightingBridgeDao.class);

        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sDao.deleteSighting(currentsighting.getSightingID());
        }

        List<Location> locations = dao.getAllLocations();
        for (Location currentLocation : locations) {
            dao.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
    }

    @After
    public void tearDown() {

        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sDao.deleteSighting(currentsighting.getSightingID());
        }

        List<Location> locations = dao.getAllLocations();
        for (Location currentLocation : locations) {
            dao.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
    }

    /**
     * Test of addLocation method, of class LocationDao.
     */
    @Test
    public void testAddGetDeleteLocation() {

        Location location = new Location();
        location.setName("Park");
        location.setDescription("That one");
        location.setAddress("Park park lane");
        location.setLatitude("208323N");
        location.setLongitude("2033204W");

        dao.addLocation(location);

        Location fromDb = dao.getLocationById(location.getLocationID());
        assertEquals(fromDb.getLocationID(), location.getLocationID());

        dao.deleteLocation(location.getLocationID());

        assertNull(dao.getLocationById(location.getLocationID()));
    }

    /**
     * Test of updateLocation method, of class LocationDao.
     */
    @Test
    public void testAddUpdateLocation() {

        Location location = new Location();
        location.setName("Park");
        location.setDescription("That one");
        location.setAddress("Park park lane");
        location.setLatitude("208323N");
        location.setLongitude("2033204W");

        dao.addLocation(location);

        String name = "Park";
        assertEquals(dao.getLocationById(location.getLocationID()).getName(), name);

        String npark = "Totally not a park";
        location.setName(npark);
        dao.updateLocation(location);

        Location fromDb = dao.getLocationById(location.getLocationID());
        assertEquals(fromDb.getName(), npark);
    }

    /**
     * Test of getAllLocations method, of class LocationDao.
     */
    @Test
    public void testGetAllLocations() {

        Location location = new Location();
        location.setName("Park");
        location.setDescription("That one");
        location.setAddress("Park park lane");
        location.setLatitude("208323N");
        location.setLongitude("2033204W");

        dao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Park");
        location2.setDescription("That one");
        location2.setAddress("Park park lane");
        location2.setLatitude("208323N");
        location2.setLongitude("2033204W");

        dao.addLocation(location2);

        List<Location> locList = dao.getAllLocations();
        assertEquals(locList.size(), 2);
    }

    /**
     * Test of getAllLocationsByMetaHumanID method, of class LocationDao.
     */
    @Test
    public void testGetAllLocationsByMetaHumanID() {

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        dao.addLocation(location);
        sighting.setLocation(dao.getLocationById(location.getLocationID()));

        sDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setName("Sighting");
        sighting2.setDate(LocalDate.now());

        Location location2 = new Location();

        location2.setName("Location");
        location2.setDescription("a location");
        location2.setAddress("12382 place");
        location2.setLatitude("2403W");
        location2.setLongitude("230304E");

        dao.addLocation(location2);
        sighting2.setLocation(dao.getLocationById(location2.getLocationID()));
        sDao.addSighting(sighting2);

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman2);

        MetaHumanSightingBridge mhsb = new MetaHumanSightingBridge();
        mhsb.setSighting(sighting);
        mhsb.setMetaHuman(metaHuman);

        MetaHumanSightingBridge mhsb2 = new MetaHumanSightingBridge();
        mhsb2.setSighting(sighting2);
        mhsb2.setMetaHuman(metaHuman2);

        mhsbDao.addMetaHumanSightingBridge(mhsb);
        mhsbDao.addMetaHumanSightingBridge(mhsb2);
        String MHid = metaHuman.getMetaHumanID();
        assertEquals(1, dao.getAllLocationsByMetaHumanID(metaHuman.getMetaHumanID()).size());
    }

}
