/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.StaticPage;
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
public class StaticPageDaoTest {

    private StaticPageDao dao;

    public StaticPageDaoTest() {
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

        dao = ctx.getBean("StaticPageDao", StaticPageDao.class);

        List<StaticPage> staticPages = dao.getAllStaticPages();
        for (StaticPage currentStaticPage : staticPages) {
            dao.deleteStaticPage(currentStaticPage.getStaticPageID());
        }
    }

    @After
    public void tearDown() {

        List<StaticPage> staticPages = dao.getAllStaticPages();
        for (StaticPage currentStaticPage : staticPages) {
            dao.deleteStaticPage(currentStaticPage.getStaticPageID());
        }

    }

    /**
     * Test of addStaticPage and GetStaticPageById methods, of class
     * StaticPageDao.
     */
    @Test
    public void testAddGetDeleteStaticPage() {

        StaticPage staticPage = new StaticPage();
        staticPage.setStaticPageName("Test Page");
        staticPage.setNavIndex(1);
        staticPage.setStaticPageContent("CONTENT");

        dao.addStaticPage(staticPage);

        StaticPage fromDb = dao.getStaticPageByID(staticPage.getStaticPageID());

        assertEquals(fromDb.getStaticPageID(), staticPage.getStaticPageID());

        dao.deleteStaticPage(staticPage.getStaticPageID());

        assertNull(dao.getStaticPageByID(staticPage.getStaticPageID()));

    }

    /**
     * Test of updateStaticPage method, of class StaticPageDao.
     */
    @Test
    public void testUpdateStaticPage() {

        StaticPage staticPage = new StaticPage();
        staticPage.setStaticPageName("Test Page");
        staticPage.setNavIndex(1);
        staticPage.setStaticPageContent("CONTENT");

        dao.addStaticPage(staticPage);

        staticPage.setStaticPageName("Updated name");
        staticPage.setNavIndex(200);
        staticPage.setStaticPageContent("Updated Content");

        dao.updateStaticPage(staticPage);

        assertEquals(staticPage.getStaticPageName(), "Updated name");
        assertEquals(staticPage.getNavIndex(), 200);
        assertEquals(staticPage.getStaticPageContent(), "Updated Content");
    }

    /**
     * Test of getAllStaticPages method, of class StaticPageDao.
     */
    @Test
    public void testGetAllStaticPages() {

        StaticPage staticPage = new StaticPage();
        staticPage.setStaticPageName("Test Page");
        staticPage.setNavIndex(1);
        staticPage.setStaticPageContent("CONTENT");

        dao.addStaticPage(staticPage);

        StaticPage staticPage2 = new StaticPage();
        staticPage2.setStaticPageName("Test Page");
        staticPage2.setNavIndex(2);
        staticPage2.setStaticPageContent("CONTENT");

        dao.addStaticPage(staticPage2);

        assertEquals(2, dao.getAllStaticPages().size());
    }

}
