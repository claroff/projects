/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Material;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class FlooringMasteryMaterialDaoTest {

    FlooringMasteryMaterialDao dao;

    public FlooringMasteryMaterialDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("materialDaoStub", FlooringMasteryMaterialDao.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadMaterials method, of class FlooringMasteryMaterialDao.
     */
    @Test
    public void testWriteLoadMaterials() throws Exception {
        List<Material> originalList = dao.loadMaterials();
        List<Material> newList = dao.loadMaterials();

        Material newMat = new Material();
        newMat.setType("testMat");
        newMat.setCostSqFt(BigDecimal.ONE);
        newMat.setLaborCostSqFt(BigDecimal.ZERO);

        newList.add(newMat);
        dao.writeMaterials(newList);
        newList = dao.loadMaterials();

        assertEquals(originalList.size() + 1, newList.size());

        dao.writeMaterials(originalList);

    }

}
