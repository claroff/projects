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
public class OrganizationDaoTest {

    private OrganizationDao dao;
    private LocationDao locDao;
    private MetaHumanOrganizationBridgeDao mhobDao;
    private MetaHumanDao MHdao;

    public OrganizationDaoTest() {
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

        dao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        locDao = ctx.getBean("LocationDao", LocationDao.class);
        mhobDao = ctx.getBean("MetaHumanOrganizationBridgeDao", MetaHumanOrganizationBridgeDao.class);
        MHdao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);

        List<MetaHuman> metaHumans = MHdao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            MHdao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Organization> organizations = dao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            dao.deleteOrganization(currentOrganization.getOrganizationID());
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

        List<Organization> organizations = dao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            dao.deleteOrganization(currentOrganization.getOrganizationID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }
    }

    /**
     * Test of addOrganization method, of class OrganizationDao.
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

        locDao.addLocation(location);
        organization.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addOrganization(organization);

        Organization fromDb = dao.getOrganizationById(organization.getOrganizationID());
        assertEquals(fromDb.getOrganizationID(), organization.getOrganizationID());

        dao.deleteOrganization(organization.getOrganizationID());
        locDao.deleteLocation(location.getLocationID());

        assertNull(dao.getOrganizationById(organization.getOrganizationID()));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDao.
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

        locDao.addLocation(location);
        organization.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addOrganization(organization);

        String name = "Org";
        assertEquals(name, dao.getOrganizationById(organization.getOrganizationID()).getName());

        String newName = "Banana Power";
        organization.setName(newName);
        dao.updateOrganization(organization);

        Organization fromDb = dao.getOrganizationById(organization.getOrganizationID());
        assertEquals(fromDb.getName(), newName);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDao.
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

        locDao.addLocation(location);
        organization.setLocation(locDao.getLocationById(location.getLocationID()));

        dao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("Org");
        organization2.setDescription("An Org");
        Location location2 = new Location();

        location2.setName("Location");
        location2.setDescription("a location");
        location2.setAddress("12382 place");
        location2.setLatitude("2403W");
        location2.setLongitude("230304E");

        locDao.addLocation(location2);
        organization2.setLocation(locDao.getLocationById(location2.getLocationID()));

        dao.addOrganization(organization2);

        List<Organization> orgList = dao.getAllOrganizations();
        assertEquals(orgList.size(), 2);
    }

    /**
     * Test of getAllOrganizationsByMetaHumanID method, of class
     * OrganizationDao.
     */
    @Test
    public void testGetAllOrganizationsByMetaHumanID() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        MHdao.addMetaHuman(metaHuman2);

        Location location = new Location();

        location.setName("Location");
        location.setDescription("a location");
        location.setAddress("12382 place");
        location.setLatitude("2403W");
        location.setLongitude("230304E");

        locDao.addLocation(location);

        Organization organization = new Organization();
        organization.setName("Organization");
        organization.setDescription("an org");
        organization.setLocation(location);

        dao.addOrganization(organization);

        MetaHumanOrganizationBridge mhob = new MetaHumanOrganizationBridge();
        mhob.setMetaHuman(metaHuman);
        mhob.setOrganization(organization);

        mhobDao.addMetaHumanOrganizationBridge(mhob);

        assertEquals(1, dao.getAllOrganizationsByMetaHumanID(metaHuman.getMetaHumanID()).size());
    }

}
