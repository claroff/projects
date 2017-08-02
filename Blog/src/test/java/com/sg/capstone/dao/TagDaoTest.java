/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

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
public class TagDaoTest {

    TagDao tagDao;
    BlogPostDao blogPostDao;
    ImageDao imageDao;
    UserDao userDao;
    PostTagBridgeDao postTagBridgeDao;

    public TagDaoTest() {
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

        tagDao = ctx.getBean("TagDao", TagDao.class);
        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);
        postTagBridgeDao = ctx.getBean("PostTagBridgeDao", PostTagBridgeDao.class);

        List<BlogPost> blogList = blogPostDao.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostDao.deleteBlogPost(post.getBlogPostID());
        }

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            userDao.deleteUser(user.getUserID());
        }

        List<Image> imageList = imageDao.getAllImages();
        for (Image image : imageList) {
            imageDao.deleteImage(image.getImageID());
        }

        List<Tag> tags = tagDao.getAllTags();
        for (Tag tag : tags) {
            tagDao.deleteTag(tag.getTagID());
        }
    }

    @After
    public void tearDown() {
        List<BlogPost> blogList = blogPostDao.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostDao.deleteBlogPost(post.getBlogPostID());
        }

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            userDao.deleteUser(user.getUserID());
        }

        List<Image> imageList = imageDao.getAllImages();
        for (Image image : imageList) {
            imageDao.deleteImage(image.getImageID());
        }

        List<Tag> tags = tagDao.getAllTags();
        for (Tag tag : tags) {
            tagDao.deleteTag(tag.getTagID());
        }
    }

    /**
     * Test of addTag method, of class TagDao.
     */
    @Test
    public void testAddGetDeleteTag() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Tag fromDB = tagDao.getTagByID(tag.getTagID());
        assertEquals(fromDB.getTagID(), tag.getTagID());

        tagDao.deleteTag(tag.getTagID());
        assertNull(tagDao.getTagByID(tag.getTagID()));
    }

    /**
     * Test of updateTag method, of class TagDao.
     */
    @Test
    public void testUpdateTag() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        tag.setTagName("EDITED NAME");
        tagDao.updateTag(tag);

        assertEquals(tag.getTagName(), "EDITED NAME");
    }

    /**
     * Test of getAllTags method, of class TagDao.
     */
    @Test
    public void testGetAllTags() {
        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Tag tag2 = new Tag();
        tag2.setTagName("SECOND TAG");
        tagDao.addTag(tag2);

        assertEquals(tagDao.getAllTags().size(), 2);
    }

    /**
     * Test of getTagByBlogPostID method, of class TagDao.
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
        imageDao.addImage(image);
        user.setImage(imageDao.getImageByID(image.getImageID()));

        userDao.addUser(user);

        BlogPost post = new BlogPost();
        post.setBlogContent("CONTENT");
        post.setBlogImage(image);
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        assertEquals(tag.getTagID(), tagDao.getTagByBlogPostID(post.getBlogPostID()).getTagID());
    }

}
