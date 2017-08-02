/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

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
public class ImageServiceTest {

    ImageService imageService;
    BlogPostService blogPostService;
    UserService userService;

    public ImageServiceTest() {
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
        imageService = ctx.getBean("ImageService", ImageService.class);
        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);
        userService = ctx.getBean("UserService", UserService.class);

        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        if (blogPosts != null) {
            for (BlogPost currentBlogPost : blogPosts) {
                blogPostService.deleteBlogPost(currentBlogPost.getBlogPostID());
            }
        }

        List<User> user = userService.getAllUsers();
        if (user != null) {
            for (User currentUser : user) {
                userService.deleteUser(currentUser.getUserID());
            }
        }

        List<Image> image = imageService.getAllImages();
        if (image != null) {
            for (Image currentImage : image) {
                imageService.deleteImage(currentImage.getImageID());
            }
        }

    }

    @After
    public void tearDown() {
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        if (blogPosts != null) {
            for (BlogPost currentBlogPost : blogPosts) {
                blogPostService.deleteBlogPost(currentBlogPost.getBlogPostID());
            }
        }

        List<User> user = userService.getAllUsers();
        if (user != null) {
            for (User currentUser : user) {
                userService.deleteUser(currentUser.getUserID());
            }
        }

        List<Image> image = imageService.getAllImages();
        if (image != null) {
            for (Image currentImage : image) {
                imageService.deleteImage(currentImage.getImageID());
            }
        }
    }

    @Test
    public void testAddGetDeleteImage() {
        Image image = new Image();
        image.setImageName("Kitty Cat Pic");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageService.addImage(image);

        Image fromService = imageService.getImageByID(image.getImageID());
        assertEquals(fromService.getImageID(), image.getImageID());

        imageService.deleteImage(image.getImageID());

        assertNull(imageService.getImageByID(image.getImageID()));
    }

    @Test
    public void testUpdateImage() {
        Image image = new Image();
        image.setImageName("Kitty Cat Pic");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageService.addImage(image);

        String imageName = "Kitty Cat Pic";
        Image fromDB = imageService.getImageByID(image.getImageID());
        assertEquals(fromDB.getImageName(), imageName);

        String dogName = "Dog Pic";
        image.setImageName(dogName);
        imageService.updateImage(image);

        Image fromDB2 = imageService.getImageByID(image.getImageID());
        assertEquals(fromDB2.getImageName(), dogName);
    }

    @Test
    public void testGetAllImages() {
        Image image = new Image();
        image.setImageName("Kitty Cat Pic");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageService.addImage(image);

        Image image2 = new Image();
        image2.setImageName("Dog Pic");
        image2.setImage(bytes);

        imageService.addImage(image2);

        List<Image> imageList = imageService.getAllImages();
        assertEquals(imageList.size(), 2);
    }

    /**
     * Test of insertImage method, of class ImageService.
     */
    @Test
    public void testInsertImage() {

        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};

        imageService.insertImage(bytes, "test");

        assertEquals(1, imageService.getAllImages().size());

    }

    /**
     * Test of getAllImagesByBlogPost method, of class ImageService.
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
//        imageService.addImage(image);
//        user.setImage(imageService.getImageByID(image.getImageID()));
//
//        userService.addUser(user);
//
//        BlogPost post = new BlogPost();
//        post.setBlogContent("CONTENT");
//        post.setBlogImage(image);
//        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
//        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
//        post.setIsActive(true);
//        post.setTitle("TITLE");
//        post.setUser(user);
//        blogPostService.addBlogPost(post);
//
//        assertEquals(1, imageService.getAllImagesByBlogPost(post.getBlogPostID()));
    }

    /**
     * Test of getImageByUserID method, of class ImageService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        assertEquals(image.getImageID(), imageService.getImageByUserID(user.getUserID()).getImageID());
    }

    /**
     * Test of getImageByBlogPostID method, of class ImageService.
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
//        imageService.addImage(image);
//        user.setImage(imageService.getImageByID(image.getImageID()));
//
//        userService.addUser(user);
//
//        BlogPost post = new BlogPost();
//        post.setBlogContent("CONTENT");
//        post.setBlogImage(image);
//        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
//        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
//        post.setIsActive(true);
//        post.setTitle("TITLE");
//        post.setUser(user);
//        blogPostService.addBlogPost(post);
//
//        assertEquals(image.getImageID(), imageService.getImageByBlogPostID(post.getBlogPostID()).getImageID());
    }

    /**
     * Test of uploadImage method, of class ImageService.
     */
    @Test
    public void testUploadImage() {

        //no human way to test
    }

}
