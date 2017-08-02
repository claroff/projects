/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
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
public class PostTagBridgeDaoTest {

    PostTagBridgeDao postTagBridgeDao;
    TagDao tagDao;
    BlogPostDao blogPostDao;
    CategoryDao categoryDao;
    ImageDao imageDao;
    UserDao userDao;
    BlogPostCategoryBridgeDao blogPostCategoryBridgeDao;

    public PostTagBridgeDaoTest() {
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

        postTagBridgeDao
                = ctx.getBean("PostTagBridgeDao", PostTagBridgeDao.class);
        tagDao = ctx.getBean("TagDao", TagDao.class);
        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        categoryDao = ctx.getBean("CategoryDao", CategoryDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);
        blogPostCategoryBridgeDao = ctx.getBean("BlogPostCategoryBridgeDao", BlogPostCategoryBridgeDao.class);

        List<BlogPost> blogList = blogPostDao.getAllBlogPosts();
        if (blogList != null) {
            for (BlogPost post : blogList) {
                blogPostDao.deleteBlogPost(post.getBlogPostID());
            }
        }

        List<User> userList = userDao.getAllUsers();
        if (userList != null) {
            for (User user : userList) {
                userDao.deleteUser(user.getUserID());
            }
        }

        List<Image> imageList = imageDao.getAllImages();
        if (imageList != null) {
            for (Image image : imageList) {
                imageDao.deleteImage(image.getImageID());
            }
        }

        List<Tag> tags = tagDao.getAllTags();
        if (tags != null) {
            for (Tag tag : tags) {
                tagDao.deleteTag(tag.getTagID());
            }
        }

    }

    @After
    public void tearDown() {

        List<BlogPost> blogList = blogPostDao.getAllBlogPosts();
        if (blogList != null) {
            for (BlogPost post : blogList) {
                blogPostDao.deleteBlogPost(post.getBlogPostID());
            }
        }

        List<User> userList = userDao.getAllUsers();
        if (userList != null) {
            for (User user : userList) {
                userDao.deleteUser(user.getUserID());
            }
        }

        List<Image> imageList = imageDao.getAllImages();
        if (imageList != null) {
            for (Image image : imageList) {
                imageDao.deleteImage(image.getImageID());
            }
        }

        List<Tag> tags = tagDao.getAllTags();
        if (tags != null) {
            for (Tag tag : tags) {
                tagDao.deleteTag(tag.getTagID());
            }
        }
    }

    /**
     * Test of addPostTagBridge method, of class PostTagBridgeDao.
     */
    @Test
    public void testAddPostTagBridge() {

        Image image = new Image();
        image.setImageName("imageName");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);
        imageDao.addImage(image);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setImage(image);
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

        BlogPost fromDB = blogPostDao.getBlogPostById(post.getBlogPostID());
        assertEquals(fromDB.getBlogPostID(), post.getBlogPostID());

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        PostTagBridge ptb = new PostTagBridge();
        ptb.setBlogPost(post);
        ptb.setTag(tag);

        postTagBridgeDao.addPostTagBridge(ptb);

        PostTagBridge res = postTagBridgeDao.getPostTagBridgeByID(ptb.getPostTagID());
        assertEquals(ptb.getPostTagID(), res.getPostTagID());

    }

    /**
     * Test of addPostTagBridge method, of class PostTagBridgeDao.
     */
    @Test
    public void testAddGetDeletePostTagBridge() {
        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        PostTagBridge fromDB = postTagBridgeDao.getPostTagBridgeByID(bridge.getPostTagID());
        assertEquals(fromDB.getPostTagID(), bridge.getPostTagID());

        postTagBridgeDao.deletePostTagBridge(bridge.getPostTagID());
        assertNull(postTagBridgeDao.getPostTagBridgeByID(bridge.getPostTagID()));
    }

    /**
     * Test of editPostTagBridge method, of class PostTagBridgeDao.
     */
    @Test
    public void testEditPostTagBridge() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.now());
        post2.setEndDate(null);
        post2.setIsActive(true);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostDao.addBlogPost(post2);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        bridge.setBlogPost(post2);
        postTagBridgeDao.editPostTagBridge(bridge);
        assertEquals(bridge.getBlogPost().getBlogPostID(), post2.getBlogPostID());
    }

    /**
     * Test of getAllPostTagBridges method, of class PostTagBridgeDao.
     */
    @Test
    public void testGetAllPostTagBridges() {

        List<PostTagBridge> bridgeList = postTagBridgeDao.getAllPostTagBridges();

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        PostTagBridge bridge2 = new PostTagBridge();
        bridge2.setBlogPost(post);
        bridge2.setTag(tag);

        PostTagBridge bridge3 = new PostTagBridge();
        bridge3.setBlogPost(post);
        bridge3.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);
        postTagBridgeDao.addPostTagBridge(bridge2);
        postTagBridgeDao.addPostTagBridge(bridge3);

        assertEquals(3, postTagBridgeDao.getAllPostTagBridges().size());
    }

    /**
     * Test of getPostTagBridgeByTagAndPostIDs method, of class
     * PostTagBridgeDao.
     */
    @Test
    public void testGetPostTagBridgeByTagAndPostIDs() {
        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        assertEquals(bridge.getPostTagID(), postTagBridgeDao.getPostTagBridgeByTagAndPostIDs(tag.getTagID(), post.getBlogPostID()).getPostTagID());
    }

    /**
     * Test of getAllPostTagBridgesByPostID method, of class PostTagBridgeDao.
     */
    @Test
    public void testGetAllPostTagBridgesByPostID() {
        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        assertEquals(1, postTagBridgeDao.getAllPostTagBridgesByPostID(post.getBlogPostID()).size());
    }

    /**
     * Test of getAllPostTagBridgesByTagID method, of class PostTagBridgeDao.
     */
    @Test
    public void testGetAllPostTagBridgesByTagID() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        assertEquals(1, postTagBridgeDao.getAllPostTagBridgesByTagID(tag.getTagID()).size());
    }

}
