/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

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
public class MetaHumanOrganizationBridgeDaoTest {

    private MetaHumanOrganizationBridgeDao dao;
    private MetaHumanDao MHdao;
    private OrganizationDao orgDao;
    private LocationDao locDao;

    public MetaHumanOrganizationBridgeDaoTest() {
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

        dao = ctx.getBean("MetaHumanOrganizationBridgeDao", MetaHumanOrganizationBridgeDao.class);
        MHdao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);
        orgDao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        locDao = ctx.getBean("LocationDao", LocationDao.class);

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
        List<Organization> organizations = orgDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            orgDao.deleteOrganization(currentOrganization.getOrganizationID());
        }
        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }

    }

    @After
    public void tearDown() {
        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }
        List<Organization> organizations = orgDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            orgDao.deleteOrganization(currentOrganization.getOrganizationID());
        }
        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }
    }

    /**
     * Test of deleteMetaHumanOrganizationBridge method, of class
     * MetaHumanOrganizationBridgeDao.
     */
    @Test
    public void testAddDeleteMetaHumanOrganizationBridgeGetByMetaHumanID() {

        MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHdao.getMetaHumanById(metaHuman.getMetaHumanID()));

        Organization organization = new Organization();
        organization.setName("Org");
        organization.setDescription("An Org");
        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        organization.setLocation(locDao.getLocationById(location.getLocationID()));

        orgDao.addOrganization(organization);

        bridge.setOrganization(orgDao.getOrganizationById(organization.getOrganizationID()));

        dao.addMetaHumanOrganizationBridge(bridge);

        MetaHumanOrganizationBridge fromDb = dao.getMetaHumanOrganizationBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanOrganizationBridgeID(), bridge.getMetaHumanOrganizationBridgeID());

        dao.deleteMetaHumanOrganizationBridge(bridge.getMetaHumanOrganizationBridgeID());
        MHdao.deleteMetaHuman(metaHuman.getMetaHumanID());
        orgDao.deleteOrganization(organization.getOrganizationID());
        locDao.deleteLocation(organization.getLocation().getLocationID());

        assertNull(dao.getMetaHumanOrganizationBridgeByMetaHumanId(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of getMetaHumanOrganizationBridgeByMetaHumanId method, of class
     * MetaHumanOrganizationBridgeDao.
     */
    @Test
    public void testAddDeleteMetaHumanOrganizationBridgeByOrganizationID() {

        MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);
        bridge.setMetaHuman(MHdao.getMetaHumanById(metaHuman.getMetaHumanID()));

        Organization organization = new Organization();
        organization.setName("Org");
        organization.setDescription("An Org");
        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);
        organization.setLocation(locDao.getLocationById(location.getLocationID()));

        orgDao.addOrganization(organization);

        bridge.setOrganization(orgDao.getOrganizationById(organization.getOrganizationID()));

        dao.addMetaHumanOrganizationBridge(bridge);

        MetaHumanOrganizationBridge fromDb = dao.getMetaHumanOrganizationBridgeByMetaHumanId(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanOrganizationBridgeID(), bridge.getMetaHumanOrganizationBridgeID());

        dao.deleteMetaHumanOrganizationBridge(bridge.getMetaHumanOrganizationBridgeID());
        MHdao.deleteMetaHuman(metaHuman.getMetaHumanID());
        orgDao.deleteOrganization(organization.getOrganizationID());
        locDao.deleteLocation(organization.getLocation().getLocationID());

        assertNull(dao.getMetaHumanOrganizationBridgeByOrganizationId(organization.getOrganizationID()));
    }

}
