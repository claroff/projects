/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

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
public class LocationServiceTest {

    private LocationService service;
    private MetaHumanService MHservice;
    private SightingService sservice;
    private MetaHumanSightingBridgeService mhsbservice;

    public LocationServiceTest() {
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

        service = ctx.getBean("LocationService", LocationService.class);
        MHservice = ctx.getBean("MetaHumanService", MetaHumanService.class);
        sservice = ctx.getBean("SightingService", SightingService.class);
        mhsbservice = ctx.getBean("MetaHumanSightingBridgeService", MetaHumanSightingBridgeService.class);

        List<Sighting> sightings = sservice.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sservice.deleteSighting(currentsighting.getSightingID());
        }

        List<Location> locations = service.getAllLocations();
        for (Location currentLocation : locations) {
            service.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
    }

    @After
    public void tearDown() {

        List<Sighting> sightings = sservice.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sservice.deleteSighting(currentsighting.getSightingID());
        }

        List<Location> locations = service.getAllLocations();
        for (Location currentLocation : locations) {
            service.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
    }

    /**
     * Test of addLocation method, of class Locationservice.
     */
    @Test
    public void testAddGetDeleteLocation() {

        Location location = new Location();
        location.setName("Park");
        location.setDescription("That one");
        location.setAddress("Park park lane");
        location.setLatitude("208323N");
        location.setLongitude("2033204W");

        service.addLocation(location);

        Location fromDb = service.getLocationById(location.getLocationID());
        assertEquals(fromDb.getLocationID(), location.getLocationID());

        service.deleteLocation(location.getLocationID());

        assertNull(service.getLocationById(location.getLocationID()));
    }

    /**
     * Test of updateLocation method, of class Locationservice.
     */
    @Test
    public void testAddUpdateLocation() {

        Location location = new Location();
        location.setName("Park");
        location.setDescription("That one");
        location.setAddress("Park park lane");
        location.setLatitude("208323N");
        location.setLongitude("2033204W");

        service.addLocation(location);

        String name = "Park";
        assertEquals(service.getLocationById(location.getLocationID()).getName(), name);

        String npark = "Totally not a park";
        location.setName(npark);
        service.updateLocation(location);

        Location fromDb = service.getLocationById(location.getLocationID());
        assertEquals(fromDb.getName(), npark);
    }

    /**
     * Test of getAllLocations method, of class Locationservice.
     */
    @Test
    public void testGetAllLocations() {

        Location location = new Location();
        location.setName("Park");
        location.setDescription("That one");
        location.setAddress("Park park lane");
        location.setLatitude("208323N");
        location.setLongitude("2033204W");

        service.addLocation(location);

        Location location2 = new Location();
        location2.setName("Park");
        location2.setDescription("That one");
        location2.setAddress("Park park lane");
        location2.setLatitude("208323N");
        location2.setLongitude("2033204W");

        service.addLocation(location2);

        List<Location> locList = service.getAllLocations();
        assertEquals(locList.size(), 2);
    }

    /**
     * Test of getAllLocationsByMetaHumanID method, of class Locationservice.
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

        service.addLocation(location);
        sighting.setLocation(service.getLocationById(location.getLocationID()));

        sservice.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setName("Sighting");
        sighting2.setDate(LocalDate.now());

        Location location2 = new Location();

        location2.setName("Location");
        location2.setDescription("a location");
        location2.setAddress("12382 place");
        location2.setLatitude("2403W");
        location2.setLongitude("230304E");

        service.addLocation(location2);
        sighting2.setLocation(service.getLocationById(location2.getLocationID()));
        sservice.addSighting(sighting2);

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman2);

        MetaHumanSightingBridge mhsb = new MetaHumanSightingBridge();
        mhsb.setSighting(sighting);
        mhsb.setMetaHuman(metaHuman);

        MetaHumanSightingBridge mhsb2 = new MetaHumanSightingBridge();
        mhsb2.setSighting(sighting2);
        mhsb2.setMetaHuman(metaHuman2);

        mhsbservice.addMetaHumanSightingBridge(mhsb);
        mhsbservice.addMetaHumanSightingBridge(mhsb2);
        String MHid = metaHuman.getMetaHumanID();
        assertEquals(1, service.getAllLocationsByMetaHumanID(metaHuman.getMetaHumanID()).size());
    }

}
