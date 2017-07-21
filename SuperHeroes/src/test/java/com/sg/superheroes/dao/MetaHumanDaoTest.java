/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

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
public class MetaHumanDaoTest {

    private MetaHumanDao dao;
    private MetaHumanSightingBridgeDao mhsbDao;
    private MetaHumanOrganizationBridgeDao mhobDao;
    private OrganizationDao oDao;
    private SightingDao sDao;
    private LocationDao locDao;

    public MetaHumanDaoTest() {
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

        dao = ctx.getBean("MetaHumanDao", MetaHumanDao.class);
        mhsbDao = ctx.getBean("MetaHumanSightingBridgeDao", MetaHumanSightingBridgeDao.class);
        mhobDao = ctx.getBean("MetaHumanOrganizationBridgeDao", MetaHumanOrganizationBridgeDao.class);
        sDao = ctx.getBean("SightingDao", SightingDao.class);
        locDao = ctx.getBean("LocationDao", LocationDao.class);
        oDao = ctx.getBean("OrganizationDao", OrganizationDao.class);

        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sDao.deleteSighting(currentsighting.getSightingID());
        }

        List<MetaHuman> metaHumans = dao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            dao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Organization> organizations = oDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oDao.deleteOrganization(currentOrganization.getOrganizationID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }

    }

    @After
    public void tearDown() {

        List<Sighting> sightings = sDao.getAllSightings();
        for (Sighting currentsighting : sightings) {
            sDao.deleteSighting(currentsighting.getSightingID());
        }

        List<MetaHuman> metaHumans = dao.getAllMetaHumans();
        for (MetaHuman currentMetaHuman : metaHumans) {
            dao.deleteMetaHuman(currentMetaHuman.getMetaHumanID());
        }

        List<Organization> organizations = oDao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            oDao.deleteOrganization(currentOrganization.getOrganizationID());
        }

        List<Location> locations = locDao.getAllLocations();
        for (Location currentLocation : locations) {
            locDao.deleteLocation(currentLocation.getLocationID());
        }

    }

    /**
     * Test of addMetaHuman method, of class MetaHumanDao.
     */
    @Test
    public void testAddGetDeleteMetaHuman() {
        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman);

        MetaHuman fromDb = dao.getMetaHumanById(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getMetaHumanID(), metaHuman.getMetaHumanID());

//        dao.deleteMetaHumanOrganizationBridgeWhenDeletingMetaHuman(metaHuman.getMetaHumanID());
        dao.deleteMetaHuman(metaHuman.getMetaHumanID());

        assertNull(dao.getMetaHumanById(metaHuman.getMetaHumanID()));
    }

    /**
     * Test of updateMetaHuman method, of class MetaHumanDao.
     */
    @Test
    public void testAddUpdateMetaHuman() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman);
        String gMan = "Garbage Man";
        assertEquals(dao.getMetaHumanById(metaHuman.getMetaHumanID()).getName(), gMan);

        String bMan = "Banana Man";
        metaHuman.setName(bMan);
        dao.updateMetaHuman(metaHuman);

        MetaHuman fromDb = dao.getMetaHumanById(metaHuman.getMetaHumanID());
        assertEquals(fromDb.getName(), bMan);
    }

    /**
     * Test of getAllMetaHumans method, of class MetaHumanDao.
     */
    @Test
    public void testGetAllMetaHumans() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman);
        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Trash Man");
        metaHuman2.setIdentity("It's me!");

        dao.addMetaHuman(metaHuman2);

        List<MetaHuman> mhList = dao.getAllMetaHumans();
        assertEquals(mhList.size(), 2);
    }

    /**
     * Test of getAllMetaHumansByLocationID method, of class MetaHumanDao.
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

        locDao.addLocation(location);
        sighting.setLocation(locDao.getLocationById(location.getLocationID()));

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

        locDao.addLocation(location2);
        sighting2.setLocation(locDao.getLocationById(location2.getLocationID()));
        sDao.addSighting(sighting2);

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman2);

        MetaHumanSightingBridge mhsb = new MetaHumanSightingBridge();
        mhsb.setSighting(sighting);
        mhsb.setMetaHuman(metaHuman);

        MetaHumanSightingBridge mhsb2 = new MetaHumanSightingBridge();
        mhsb2.setSighting(sighting2);
        mhsb2.setMetaHuman(metaHuman2);

        mhsbDao.addMetaHumanSightingBridge(mhsb);
        mhsbDao.addMetaHumanSightingBridge(mhsb2);
        //String MHid = metaHuman.getMetaHumanID();
        assertEquals(1, dao.getAllMetaHumansByLocationID(location.getLocationID()).size());
    }

    /**
     * Test of getAllMetaHumansByOrganizationID method, of class MetaHumanDao.
     */
    @Test
    public void testGetAllMetaHumansByOrganizationID() {

        MetaHuman metaHuman = new MetaHuman();
        metaHuman.setName("Garbage Man");
        metaHuman.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman);

        MetaHuman metaHuman2 = new MetaHuman();
        metaHuman2.setName("Garbage Man");
        metaHuman2.setIdentity("It's you!");

        dao.addMetaHuman(metaHuman2);

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

        oDao.addOrganization(organization);

        MetaHumanOrganizationBridge mhob = new MetaHumanOrganizationBridge();
        mhob.setMetaHuman(metaHuman);
        mhob.setOrganization(organization);

        mhobDao.addMetaHumanOrganizationBridge(mhob);

        assertEquals(1, dao.getAllMetaHumansByOrganizationID(organization.getOrganizationID()).size());
    }
}
