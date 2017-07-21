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
public class OrganizationServiceTest {

    private OrganizationService service;
    private LocationService locservice;
    private MetaHumanOrganizationBridgeService mhobservice;
    private MetaHumanService MHservice;

    public OrganizationServiceTest() {
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

        service = ctx.getBean("OrganizationService", OrganizationService.class);
        locservice = ctx.getBean("LocationService", LocationService.class);
        mhobservice = ctx.getBean("MetaHumanOrganizationBridgeService", MetaHumanOrganizationBridgeService.class);
        MHservice = ctx.getBean("MetaHumanService", MetaHumanService.class);

        List<MetaHuman> metaHumans = MHservice.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHservice.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Organization> organizations = service.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            service.deleteOrganization(currentOrganization.getOrganizationID());
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

        List<Organization> organizations = service.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            service.deleteOrganization(currentOrganization.getOrganizationID());
        }

        List<Location> locations = locservice.getAllLocations();
        for (Location currentLocation : locations) {
            locservice.deleteLocation(currentLocation.getLocationID());
        }
    }

    /**
     * Test of addOrganization method, of class Organizationservice.
     */
    @Test
    public void testAddGetDeleteOrganization() {
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

        service.addOrganization(organization);

        Organization fromDb = service.getOrganizationById(organization.getOrganizationID());
        assertEquals(fromDb.getOrganizationID(), organization.getOrganizationID());

        service.deleteOrganization(organization.getOrganizationID());
        locservice.deleteLocation(location.getLocationID());

        assertNull(service.getOrganizationById(organization.getOrganizationID()));
    }

    /**
     * Test of updateOrganization method, of class Organizationservice.
     */
    @Test
    public void testAddUpdateOrganization() {
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

        service.addOrganization(organization);

        String name = "Org";
        assertEquals(name, service.getOrganizationById(organization.getOrganizationID()).getName());

        String newName = "Banana Power";
        organization.setName(newName);
        service.updateOrganization(organization);

        Organization fromDb = service.getOrganizationById(organization.getOrganizationID());
        assertEquals(fromDb.getName(), newName);
    }

    /**
     * Test of getAllOrganizations method, of class Organizationservice.
     */
    @Test
    public void testGetAllOrganizations() {
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

        service.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("Org");
        organization2.setDescription("An Org");
        Location location2 = new Location();

        location2.setName("Location");
        location2.setDescription("a location");
        location2.setAddress("12382 place");
        location2.setLatitude("2403W");
        location2.setLongitude("230304E");

        locservice.addLocation(location2);
        organization2.setLocation(locservice.getLocationById(location2.getLocationID()));

        service.addOrganization(organization2);

        List<Organization> orgList = service.getAllOrganizations();
        assertEquals(orgList.size(), 2);
    }

    /**
     * Test of getAllOrganizationsByMetaHumanID method, of class
     * Organizationservice.
     */
    @Test
    public void testGetAllOrganizationsByMetaHumanID() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        MHservice.addMetaHuman(metaHuman2);

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

        service.addOrganization(organization);

        MetaHumanOrganizationBridge mhob = new MetaHumanOrganizationBridge();
        mhob.setMetaHuman(metaHuman);
        mhob.setOrganization(organization);

        mhobservice.addMetaHumanOrganizationBridge(mhob);

        assertEquals(1, service.getAllOrganizationsByMetaHumanID(metaHuman.getMetaHumanID()).size());
    }

}
