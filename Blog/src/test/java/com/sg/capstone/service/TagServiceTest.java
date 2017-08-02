/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.PostTagBridge;
import com.sg.capstone.model.Tag;
import com.sg.capstone.model.User;
import java.time.LocalDate;
import java.time.Month;
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
public class TagServiceTest {

    TagService tagService;
    BlogPostService blogPostService;
    ImageService imageService;
    UserService userService;
    PostTagBridgeService postTagBridgeService;

    public TagServiceTest() {
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

        tagService = ctx.getBean("TagService", TagService.class);
        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);
        userService = ctx.getBean("UserService", UserService.class);
        postTagBridgeService = ctx.getBean("PostTagBridgeService", PostTagBridgeService.class);

        List<BlogPost> blogList = blogPostService.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostService.deleteBlogPost(post.getBlogPostID());
        }

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            userService.deleteUser(user.getUserID());
        }

        List<Image> imageList = imageService.getAllImages();
        for (Image image : imageList) {
            imageService.deleteImage(image.getImageID());
        }

        List<Tag> tags = tagService.getAllTags();
        for (Tag tag : tags) {
            tagService.deleteTag(tag.getTagID());
        }
    }

    @After
    public void tearDown() {
        List<BlogPost> blogList = blogPostService.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostService.deleteBlogPost(post.getBlogPostID());
        }

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            userService.deleteUser(user.getUserID());
        }

        List<Image> imageList = imageService.getAllImages();
        for (Image image : imageList) {
            imageService.deleteImage(image.getImageID());
        }

        List<Tag> tags = tagService.getAllTags();
        for (Tag tag : tags) {
            tagService.deleteTag(tag.getTagID());
        }
    }

    /**
     * Test of addTag method, of class TagService.
     */
    @Test
    public void testAddGetDeleteTag() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        Tag fromDB = tagService.getTagByID(tag.getTagID());
        assertEquals(fromDB.getTagID(), tag.getTagID());

        tagService.deleteTag(tag.getTagID());
        assertNull(tagService.getTagByID(tag.getTagID()));
    }

    /**
     * Test of updateTag method, of class TagService.
     */
    @Test
    public void testUpdateTag() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        tag.setTagName("EDITED NAME");
        tagService.updateTag(tag);

        assertEquals(tag.getTagName(), "EDITED NAME");
    }

    /**
     * Test of getAllTags method, of class TagService.
     */
    @Test
    public void testGetAllTags() {
        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        Tag tag2 = new Tag();
        tag2.setTagName("SECOND TAG");
        tagService.addTag(tag2);

        assertEquals(tagService.getAllTags().size(), 2);
    }

    /**
     * Test of getTagByBlogPostID method, of class TagService.
     */
    @Test
    public void testGetTagByBlogPostID() {

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

        BlogPost post = new BlogPost();
        post.setBlogContent("CONTENT");
        post.setBlogImage(image);
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(tag.getTagID(), tagService.getTagByBlogPostID(post.getBlogPostID()).getTagID());
    }

}
