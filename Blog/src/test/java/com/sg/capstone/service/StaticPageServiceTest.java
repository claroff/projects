/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.StaticPage;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class StaticPageServiceTest {

    private StaticPageService service;

    public StaticPageServiceTest() {
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

        service = ctx.getBean("StaticPageService", StaticPageService.class);

        List<StaticPage> staticPages = service.getAllStaticPages();
        for (StaticPage currentStaticPage : staticPages) {
            service.deleteStaticPage(currentStaticPage.getStaticPageID());
        }
    }

    @After
    public void tearDown() {

        List<StaticPage> staticPages = service.getAllStaticPages();
        for (StaticPage currentStaticPage : staticPages) {
            service.deleteStaticPage(currentStaticPage.getStaticPageID());
        }
    }

    /**
     * Test of addStaticPage and GetStaticPageById methods, of class
     * StaticPageService.
     */
    @Test
    public void testAddGetStaticPage() {

        StaticPage staticPage = new StaticPage();
        staticPage.setStaticPageName("Test Page");
        staticPage.setNavIndex(1);
        staticPage.setStaticPageContent("CONTENT");

        service.addStaticPage(staticPage);

        StaticPage fromDb = service.getStaticPageByID(staticPage.getStaticPageID());

    }

    /**
     * Test of updateStaticPage method, of class StaticPageService.
     */
    @Test
    public void testUpdateStaticPage() {
    }

    /**
     * Test of deleteStaticPage method, of class StaticPageService.
     */
    @Test
    public void testDeleteStaticPage() {
    }

    /**
     * Test of getAllStaticPages method, of class StaticPageService.
     */
    @Test
    public void testGetAllStaticPages() {
    }

}
