/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.Authority;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.User;
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
public class AuthorityDaoTest {

    AuthorityDao authorityDao;
    UserDao userDao;
    ImageDao imageDao;

    public AuthorityDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        authorityDao = ctx.getBean("AuthorityDao", AuthorityDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);

        List<Authority> authorityList = authorityDao.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityDao.deleteAuthority(authority.getAuthorityID());
        }

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            userDao.deleteUser(user.getUserID());
        }
    }

    @After
    public void tearDown() {

        List<Authority> authorityList = authorityDao.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityDao.deleteAuthority(authority.getAuthorityID());
        }

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            userDao.deleteUser(user.getUserID());
        }
    }

    /**
     * Test of getAllAuthorities method, of class AuthorityDao.
     */
    @Test
    public void testAddGetAllAuthorities() {
        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");

        Image image = new Image();
        image.setImageName("imageName");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);
        imageDao.addImage(image);
        user.setImage(imageDao.getImageByID(image.getImageID()));

        userDao.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("HEY");
        authority.setUser(user);
        authorityDao.addAuthority(authority);

        assertEquals(1, authorityDao.getAllAuthorities(user.getUserID()).size());

    }

    /**
     * Test of getAllAuthoritiesForReal method, of class AuthorityDao.
     */
    @Test
    public void testGetAllAuthoritiesForReal() {
        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");

        Image image = new Image();
        image.setImageName("imageName");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);
        imageDao.addImage(image);
        user.setImage(imageDao.getImageByID(image.getImageID()));

        userDao.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("HEY");
        authority.setUser(user);
        authorityDao.addAuthority(authority);

        assertEquals(1, authorityDao.getAllAuthoritiesForReal().size());
    }

    /**
     * Test of deleteAuthority method, of class AuthorityDao.
     */
    @Test
    public void testDeleteAuthority() {
        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");

        Image image = new Image();
        image.setImageName("imageName");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);
        imageDao.addImage(image);
        user.setImage(imageDao.getImageByID(image.getImageID()));

        userDao.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("HEY");
        authority.setUser(user);
        authorityDao.addAuthority(authority);

        authorityDao.deleteAuthority(authority.getAuthorityID());

        assertEquals(0, authorityDao.getAllAuthorities(user.getUserID()).size());

    }

}
