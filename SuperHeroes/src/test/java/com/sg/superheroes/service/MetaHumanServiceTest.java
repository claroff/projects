/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import com.sg.superheroes.model.MetaHumanSightingBridge;
import com.sg.superheroes.model.Organization;
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
public class MetaHumanServiceTest {

    private MetaHumanService service;
    private MetaHumanSightingBridgeService mhsbservice;
    private MetaHumanOrganizationBridgeService mhobservice;
    private OrganizationService oservice;
    private SightingService sservice;
    private LocationService locservice;

    public MetaHumanServiceTest() {
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

        service = ctx.getBean("MetaHumanService", MetaHumanService.class);
        mhsbservice = ctx.getBean("MetaHumanSightingBridgeService", MetaHumanSightingBridgeService.class);
        mhobservice = ctx.getBean("MetaHumanOrganizationBridgeService", MetaHumanOrganizationBridgeService.class);
        sservice = ctx.getBean("SightingService", SightingService.class);
        locservice = ctx.getBean("LocationService", LocationService.class);
        oservice = ctx.getBean("OrganizationService", OrganizationService.class);

        List<Sighting> sightings = sservice.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sservice.deleteSighting(currentsighting.getSightingID());
        }

        List<MetaHuman> metaHumans = service.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            service.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Organization> organizations = oservice.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oservice.deleteOrganization(currentOrganization.getOrganizationID());
        }

        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }

    }

    @After
    public void tearDown() {

        List<Sighting> sightings = sservice.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sservice.deleteSighting(currentsighting.getSightingID());
        }

        List<MetaHuman> metaHumans = service.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            service.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Organization> organizations = oservice.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oservice.deleteOrganization(currentOrganization.getOrganizationID());
        }

        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }

    }

    /**
     * Test of addMetaHuman method, of class MetaHumanservice.
     */
    @Test
    public void testAddGetDeleteMetaHuman() {
        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        service.addMetaHuman(metaHuman);

        MetaHuman fromDb = service.getMetaHumanById(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanID(), metaHuman.getMetaHumanID());

//        service.deleteMetaHumanOrganizationBridgeWhenDeletingMetaHuman(metaHuman.getMetaHumanID());
        service.deleteMetaHuman(metaHuman.getMetaHumanID());

        assertNull(service.getMetaHumanById(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of updateMetaHuman method, of class MetaHumanservice.
     */
    @Test
    public void testAddUpdateMetaHuman() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        service.addMetaHuman(metaHuman);
        String gMan = "Garbage Man";
        assertEquals(service.getMetaHumanById(metaHuman.getMetaHumanID()).getName(), gMan);

        String bMan = "Banana Man";
        metaHuman.setName(bMan);
        service.updateMetaHuman(metaHuman);

        MetaHuman fromDb = service.getMetaHumanById(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getName(), bMan);
    }

    /**
     * Test of getAllMetaHumans method, of class MetaHumanservice.
     */
    @Test
    public void testGetAllMetaHumans() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        service.addMetaHuman(metaHuman);
        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Trash Man");
        metaHuman2.setIdentity("It's me!");

        service.addMetaHuman(metaHuman2);

        List<MetaHuman> mhList = service.getAllMetaHumans();
        assertEquals(mhList.size(), 2);
    }

    /**
     * Test of getAllMetaHumansByLocationID method, of class MetaHumanservice.
     */
    @Test
    public void testGetAllMetaHumansByLocationID() {

        Sighting sighting = new Sighting();
        sighting.setName("Sighting");
        sighting.setDate(LocalDate.now());

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locservice.addLocation(location);
        sighting.setLocation(locservice.getLocationById(location.getLocationID()));

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

        locservice.addLocation(location2);
        sighting2.setLocation(locservice.getLocationById(location2.getLocationID()));
        sservice.addSighting(sighting2);

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        service.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        service.addMetaHuman(metaHuman2);

        MetaHumanSightingBridge mhsb = new MetaHumanSightingBridge();
        mhsb.setSighting(sighting);
        mhsb.setMetaHuman(metaHuman);

        MetaHumanSightingBridge mhsb2 = new MetaHumanSightingBridge();
        mhsb2.setSighting(sighting2);
        mhsb2.setMetaHuman(metaHuman2);

        mhsbservice.addMetaHumanSightingBridge(mhsb);
        mhsbservice.addMetaHumanSightingBridge(mhsb2);
        //String MHid = metaHuman.getMetaHumanID();
        assertEquals(1, service.getAllMetaHumansByLocationID(location.getLocationID()).size());
    }

    /**
     * Test of getAllMetaHumansByOrganizationID method, of class
     * MetaHumanservice.
     */
    @Test
    public void testGetAllMetaHumansByOrganizationID() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        service.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        service.addMetaHuman(metaHuman2);

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locservice.addLocation(location);

        Organization organization = new Organization();
        organization.setName("Organization");
        organization.setDescription("an org");
        organization.setLocation(location);

        oservice.addOrganization(organization);

        MetaHumanOrganizationBridge mhob = new MetaHumanOrganizationBridge();
        mhob.setMetaHuman(metaHuman);
        mhob.setOrganization(organization);

        mhobservice.addMetaHumanOrganizationBridge(mhob);

        assertEquals(1, service.getAllMetaHumansByOrganizationID(organization.getOrganizationID()).size());
    }
}
