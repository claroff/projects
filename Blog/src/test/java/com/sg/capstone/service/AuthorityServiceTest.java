/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

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
public class AuthorityServiceTest {

    AuthorityService authorityService;
    UserService userService;
    ImageService imageService;

    public AuthorityServiceTest() {
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
        authorityService = ctx.getBean("AuthorityService", AuthorityService.class);
        userService = ctx.getBean("UserService", UserService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);

        List<Authority> authorityList = authorityService.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityService.deleteAuthority(authority.getAuthorityID());
        }

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            userService.deleteUser(user.getUserID());
        }
    }

    @After
    public void tearDown() {

        List<Authority> authorityList = authorityService.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityService.deleteAuthority(authority.getAuthorityID());
        }

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            userService.deleteUser(user.getUserID());
        }
    }

    /**
     * Test of getAllAuthorities method, of class AuthorityService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("HEY");
        authority.setUser(user);
        authorityService.addAuthority(authority);

        assertEquals(1, authorityService.getAllAuthorities(user.getUserID()).size());

    }

    /**
     * Test of getAllAuthoritiesForReal method, of class AuthorityService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("HEY");
        authority.setUser(user);
        authorityService.addAuthority(authority);

        assertEquals(1, authorityService.getAllAuthoritiesForReal().size());
    }

    /**
     * Test of deleteAuthority method, of class AuthorityService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("HEY");
        authority.setUser(user);
        authorityService.addAuthority(authority);

        authorityService.deleteAuthority(authority.getAuthorityID());

        assertEquals(0, authorityService.getAllAuthorities(user.getUserID()).size());

    }

}
