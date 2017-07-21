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
public class MetaHumanSightingBridgeServiceTest {

    private MetaHumanSightingBridgeService service;
    private MetaHumanService MHservice;
    private SightingService sservice;
    private LocationService locservice;

    public MetaHumanSightingBridgeServiceTest() {
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

        service = ctx.getBean("MetaHumanSightingBridgeService", MetaHumanSightingBridgeService.class);
        MHservice = ctx.getBean("MetaHumanService", MetaHumanService.class);
        sservice = ctx.getBean("SightingService", SightingService.class);
        locservice = ctx.getBean("LocationService", LocationService.class);

        List<Sighting> sightings = sservice.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sservice.deleteSighting(currentSighting.getSightingID());
        }

        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

    }

    @After
    public void tearDown() {

        List<Sighting> sightings = sservice.getAllSightings();
        for (Sighting currentSighting : sightings) {
            sservice.deleteSighting(currentSighting.getSightingID());
        }

        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

    }

    /**
     * Test of addMetaHumanSightingBridge method, of class
     * MetaHumanSightingBridgeservice.
     */
    @Test
    public void testAddDeleteMetaHumanSightingBridgeGetByMetaHumanID() {

        MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHservice.getMetaHumanById(metaHuman.getMetaHumanID()));

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locservice.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());
        sighting.setLocation(locservice.getLocationById(location.getLocationID()));

        sservice.addSighting(sighting);

        bridge.setSighting(sservice.getSightingById(sighting.getSightingID()));

        service.addMetaHumanSightingBridge(bridge);

        MetaHumanSightingBridge fromDb = service.getMetaHumanSightingBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanSightingBridgeID(), bridge.getMetaHumanSightingBridgeID());

        service.deleteMetaHumanSightingBridge(bridge.getMetaHumanSightingBridgeID());
        MHservice.deleteMetaHuman(metaHuman.getMetaHumanID());
        sservice.deleteSighting(sighting.getSightingID());
        locservice.deleteLocation(sighting.getLocation().getLocationID());

        assertNull(service.getMetaHumanSightingBridgeByMetaHumanId(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of deleteMetaHumanSightingBridge method, of class
     * MetaHumanSightingBridgeservice.
     */
    @Test
    public void testAddDeleteMetaHumanSightingBridgeGetBySightingID() {

        MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHservice.getMetaHumanById(metaHuman.getMetaHumanID()));

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locservice.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());
        sighting.setLocation(locservice.getLocationById(location.getLocationID()));

        sservice.addSighting(sighting);

        bridge.setSighting(sservice.getSightingById(sighting.getSightingID()));

        service.addMetaHumanSightingBridge(bridge);

        MetaHumanSightingBridge fromDb = service.getMetaHumanSightingBridgeBySightingId(sighting.getSightingID());
        assertEquals(fromDb.getMetaHumanSightingBridgeID(), bridge.getMetaHumanSightingBridgeID());

        service.deleteMetaHumanSightingBridge(bridge.getMetaHumanSightingBridgeID());
        MHservice.deleteMetaHuman(metaHuman.getMetaHumanID());
        sservice.deleteSighting(sighting.getSightingID());
        locservice.deleteLocation(sighting.getLocation().getLocationID());

        assertNull(service.getMetaHumanSightingBridgeBySightingId(sighting.getSightingID()));
    }

}
