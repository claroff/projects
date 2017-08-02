/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.User;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public class ImageDaoTest {

    ImageDao imageDao;
    BlogPostDao blogPostDao;
    UserDao userDao;

    public ImageDaoTest() {
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
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);

        List<BlogPost> blogPosts = blogPostDao.getAllBlogPosts();
        if (blogPosts != null) {
            for (BlogPost currentBlogPost : blogPosts) {
                blogPostDao.deleteBlogPost(currentBlogPost.getBlogPostID());
            }
        }

        List<User> user = userDao.getAllUsers();
        if (user != null) {
            for (User currentUser : user) {
                userDao.deleteUser(currentUser.getUserID());
            }
        }

        List<Image> image = imageDao.getAllImages();
        if (image != null) {
            for (Image currentImage : image) {
                imageDao.deleteImage(currentImage.getImageID());
            }
        }

    }

    @After
    public void tearDown() {
        List<BlogPost> blogPosts = blogPostDao.getAllBlogPosts();
        if (blogPosts != null) {
            for (BlogPost currentBlogPost : blogPosts) {
                blogPostDao.deleteBlogPost(currentBlogPost.getBlogPostID());
            }
        }

        List<User> user = userDao.getAllUsers();
        if (user != null) {
            for (User currentUser : user) {
                userDao.deleteUser(currentUser.getUserID());
            }
        }

        List<Image> image = imageDao.getAllImages();
        if (image != null) {
            for (Image currentImage : image) {
                imageDao.deleteImage(currentImage.getImageID());
            }
        }
    }

    @Test
    public void testAddGetDeleteImage() {
        Image image = new Image();
        image.setImageName("Kitty Cat Pic");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageDao.addImage(image);

        Image fromDao = imageDao.getImageByID(image.getImageID());
        assertEquals(fromDao.getImageID(), image.getImageID());

        imageDao.deleteImage(image.getImageID());

        assertNull(imageDao.getImageByID(image.getImageID()));
    }

    @Test
    public void testUpdateImage() {
        Image image = new Image();
        image.setImageName("Kitty Cat Pic");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageDao.addImage(image);

        String imageName = "Kitty Cat Pic";
        Image fromDB = imageDao.getImageByID(image.getImageID());
        assertEquals(fromDB.getImageName(), imageName);

        String dogName = "Dog Pic";
        image.setImageName(dogName);
        imageDao.updateImage(image);

        Image fromDB2 = imageDao.getImageByID(image.getImageID());
        assertEquals(fromDB2.getImageName(), dogName);
    }

    @Test
    public void testGetAllImages() {
        Image image = new Image();
        image.setImageName("Kitty Cat Pic");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageDao.addImage(image);

        Image image2 = new Image();
        image2.setImageName("Dog Pic");
        image2.setImage(bytes);

        imageDao.addImage(image2);

        List<Image> imageList = imageDao.getAllImages();
        assertEquals(imageList.size(), 2);
    }

    /**
     * Test of insertImage method, of class ImageDao.
     */
    @Test
    public void testInsertImage() {

        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};

        imageDao.insertImage(bytes, "test");

        assertEquals(1, imageDao.getAllImages().size());

    }

    /**
     * Test of getAllImagesByBlogPost method, of class ImageDao.
     */
    @Test
    public void testGetAllImagesByBlogPost() {
//        User user = new User();
//        user.setUserName("Name");
//        user.setUserPassword("password");
//
//        Image image = new Image();
//        image.setImageName("imageName");
//        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
//        image.setImage(bytes);
//        imageDao.addImage(image);
//        user.setImage(imageDao.getImageByID(image.getImageID()));
//
//        userDao.addUser(user);
//
//        BlogPost post = new BlogPost();
//        post.setBlogContent("CONTENT");
//        post.setBlogImage(image);
//        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
//        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
//        post.setIsActive(true);
//        post.setTitle("TITLE");
//        post.setUser(user);
//        blogPostDao.addBlogPost(post);
//
//        assertEquals(1, imageDao.getAllImagesByBlogPost(post.getBlogPostID()));
    }

    /**
     * Test of getImageByUserID method, of class ImageDao.
     */
    @Test
    public void testGetImageByUserID() {
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

        assertEquals(image.getImageID(), imageDao.getImageByUserID(user.getUserID()).getImageID());
    }

    /**
     * Test of getImageByBlogPostID method, of class ImageDao.
     */
    @Test
    public void testGetImageByBlogPostID() {

//        User user = new User();
//        user.setUserName("Name");
//        user.setUserPassword("password");
//
//        Image image = new Image();
//        image.setImageName("imageName");
//        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
//        image.setImage(bytes);
//        imageDao.addImage(image);
//        user.setImage(imageDao.getImageByID(image.getImageID()));
//
//        userDao.addUser(user);
//
//        BlogPost post = new BlogPost();
//        post.setBlogContent("CONTENT");
//        post.setBlogImage(image);
//        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
//        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
//        post.setIsActive(true);
//        post.setTitle("TITLE");
//        post.setUser(user);
//        blogPostDao.addBlogPost(post);
//
//        assertEquals(image.getImageID(), imageDao.getImageByBlogPostID(post.getBlogPostID()).getImageID());
    }

    /**
     * Test of uploadImage method, of class ImageDao.
     */
    @Test
    public void testUploadImage() {

        //no human way to test
    }

}
