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
import java.util.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class SightingDaoTest {

    private SightingDao dao;
    private LocationDao locDao;
    private MetaHumanDao MHDao;
    private MetaHumanSightingBridgeDao mhsbDao;

    public SightingDaoTest() {
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

        dao = ctx.getBean("SightingDao", SightingDao.class);
        locDao = ctx.getBean("LocationDao", LocationDao.class);
        MHDao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);
        mhsbDao = ctx.getBean("MetaHumanSightingBridgeDao", MetaHumanSightingBridgeDao.class);

        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting currentsighting : sightings) {
            dao.deleteSighting(currentsighting.getSightingID());
        }

        List<MetaHuman> metaHumans = MHDao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHDao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }
    }

    @After
    public void tearDown() {
        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting currentsighting : sightings) {
            dao.deleteSighting(currentsighting.getSightingID());
        }

        List<MetaHuman> metaHumans = MHDao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHDao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }

    }

    /**
     * Test of addSighting method, of class SightingDao.
     */
    @Test
    public void testAddGetDeleteSighting() {
        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting);

        Sighting fromDb = dao.getSightingById(sighting.getSightingID());
        assertEquals(fromDb.getSightingID(), sighting.getSightingID());

        dao.deleteSighting(sighting.getSightingID());
        locDao.deleteLocation(location.getLocationID());

        assertNull(dao.getSightingById(sighting.getSightingID()));
    }

    /**
     * Test of updateSighting method, of class SightingDao.
     */
    @Test
    public void testAddUpdateSighting() {

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting);

        String name = "Sighting";
        assertEquals(name, dao.getSightingById(sighting.getSightingID()).getName());

        String newName = "Banana Sighting";
        sighting.setName(newName);
        dao.updateSighting(sighting);

        Sighting fromDb = dao.getSightingById(sighting.getSightingID());
        assertEquals(fromDb.getName(), newName);
    }

    /**
     * Test of getAllSightings method, of class SightingDao.
     */
    @Test
    public void testGetAllSightings() {

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setName("Sighting");
        sighting2.setDate(LocalDate.now());

        Location location2 = new Location();

        location2.setName("Location");
        location2.setDescription("a location");
        location2.setAddress("12382 place");
        location2.setLatitude("2403W");
        location2.setLongitude("230304E");

        locDao.addLocation(location2);
        sighting2.setLocation(locDao.getLocationById(location2.getLocationID()));

        dao.addSighting(sighting2);

        List<Sighting> sList = dao.getAllSightings();
        assertEquals(sList.size(), 2);
    }

    @Test
    public void testGetRecentSightings() {

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setName("Sighting");
        sighting2.setDate(LocalDate.now());

        sighting2.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting2);

        Sighting sighting3 = new Sighting();
        sighting3.setName("Sighting");
        sighting3.setDate(LocalDate.now());

        sighting3.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting3);

        Sighting sighting4 = new Sighting();
        sighting4.setName("Sighting");
        sighting4.setDate(LocalDate.now());

        sighting4.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting4);

        Sighting sighting5 = new Sighting();
        sighting5.setName("Sighting");
        sighting5.setDate(LocalDate.now());

        sighting5.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting5);

        Sighting sighting6 = new Sighting();
        sighting6.setName("Sighting");
        sighting6.setDate(LocalDate.now());

        sighting6.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting6);

        Sighting sighting7 = new Sighting();
        sighting7.setName("Sighting");
        sighting7.setDate(LocalDate.now());

        sighting7.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting7);

        Sighting sighting8 = new Sighting();
        sighting8.setName("Sighting");
        sighting8.setDate(LocalDate.now());

        sighting8.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting8);

        Sighting sighting9 = new Sighting();
        sighting9.setName("Sighting");
        sighting9.setDate(LocalDate.now());

        sighting9.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting9);

        Sighting sighting10 = new Sighting();
        sighting10.setName("Sighting");
        sighting10.setDate(LocalDate.of(3000, Month.MARCH, 2));

        sighting10.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting10);

        Sighting sighting11 = new Sighting();
        sighting11.setName("Sighting");
        sighting11.setDate(LocalDate.now());

        sighting11.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting11);

        List<Sighting> sList = dao.getRecentSightings();
        assertEquals(sList.size(), 10);
        assertFalse(sList.contains(sighting10));
    }

    /**
     * Test of getAllSightingsByDate method, of class SightingDao.
     */
    @Test
    public void testGetAllSightingsByDate() {

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.of(1988, Month.MARCH, 22));

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setName("Sighting");
        sighting2.setDate(LocalDate.now());

        Location location2 = new Location();

        location2.setName("Location");
        location2.setDescription("a location");
        location2.setAddress("12382 place");
        location2.setLatitude("2403W");
        location2.setLongitude("230304E");

        locDao.addLocation(location2);
        sighting2.setLocation(locDao.getLocationById(location2.getLocationID()));

        dao.addSighting(sighting2);

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHDao.addMetaHuman(metaHuman);

        MetaHumanSightingBridge mhsb = new MetaHumanSightingBridge();
        mhsb.setMetaHuman(metaHuman);
        mhsb.setSighting(sighting);

        mhsbDao.addMetaHumanSightingBridge(mhsb);

        LocalDate localDate = LocalDate.of(1988, Month.MARCH, 22);

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        assertEquals(1, dao.getAllSightingsByDate(date).size());
    }

}
