/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import com.sg.superheroes.model.Organization;
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
public class MetaHumanOrganizationBridgeServiceTest {

    private MetaHumanOrganizationBridgeService service;
    private MetaHumanService MHservice;
    private OrganizationService orgservice;
    private LocationService locservice;

    public MetaHumanOrganizationBridgeServiceTest() {
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

        service = ctx.getBean("MetaHumanOrganizationBridgeService", MetaHumanOrganizationBridgeService.class);
        MHservice = ctx.getBean("MetaHumanService", MetaHumanService.class);
        orgservice = ctx.getBean("OrganizationService", OrganizationService.class);
        locservice = ctx.getBean("LocationService", LocationService.class);

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
        List<Organization> organizations = orgservice.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            orgservice.deleteOrganization(currentOrganization.getOrganizationID());
        }
        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }

    }

    @After
    public void tearDown() {
        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
        List<Organization> organizations = orgservice.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            orgservice.deleteOrganization(currentOrganization.getOrganizationID());
        }
        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }
    }

    /**
     * Test of deleteMetaHumanOrganizationBridge method, of class
     * MetaHumanOrganizationBridgeservice.
     */
    @Test
    public void testAddDeleteMetaHumanOrganizationBridgeGetByMetaHumanID() {

        MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHservice.getMetaHumanById(metaHuman.getMetaHumanID()));

        Organization organization = new Organization();
        organization.setName("Org");
        organization.setDescription("An Org");
        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locservice.addLocation(location);
        organization.setLocation(locservice.getLocationById(location.getLocationID()));

        orgservice.addOrganization(organization);

        bridge.setOrganization(orgservice.getOrganizationById(organization.getOrganizationID()));

        service.addMetaHumanOrganizationBridge(bridge);

        MetaHumanOrganizationBridge fromDb = service.getMetaHumanOrganizationBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanOrganizationBridgeID(), bridge.getMetaHumanOrganizationBridgeID());

        service.deleteMetaHumanOrganizationBridge(bridge.getMetaHumanOrganizationBridgeID());
        MHservice.deleteMetaHuman(metaHuman.getMetaHumanID());
        orgservice.deleteOrganization(organization.getOrganizationID());
        locservice.deleteLocation(organization.getLocation().getLocationID());

        assertNull(service.getMetaHumanOrganizationBridgeByMetaHumanId(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of getMetaHumanOrganizationBridgeByMetaHumanId method, of class
     * MetaHumanOrganizationBridgeservice.
     */
    @Test
    public void testAddDeleteMetaHumanOrganizationBridgeByOrganizationID() {

        MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHservice.getMetaHumanById(metaHuman.getMetaHumanID()));

        Organization organization = new Organization();
        organization.setName("Org");
        organization.setDescription("An Org");
        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locservice.addLocation(location);
        organization.setLocation(locservice.getLocationById(location.getLocationID()));

        orgservice.addOrganization(organization);

        bridge.setOrganization(orgservice.getOrganizationById(organization.getOrganizationID()));

        service.addMetaHumanOrganizationBridge(bridge);

        MetaHumanOrganizationBridge fromDb = service.getMetaHumanOrganizationBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanOrganizationBridgeID(), bridge.getMetaHumanOrganizationBridgeID());

        service.deleteMetaHumanOrganizationBridge(bridge.getMetaHumanOrganizationBridgeID());
        MHservice.deleteMetaHuman(metaHuman.getMetaHumanID());
        orgservice.deleteOrganization(organization.getOrganizationID());
        locservice.deleteLocation(organization.getLocation().getLocationID());

        assertNull(service.getMetaHumanOrganizationBridgeByOrganizationId(organization.getOrganizationID()));
    }

}
