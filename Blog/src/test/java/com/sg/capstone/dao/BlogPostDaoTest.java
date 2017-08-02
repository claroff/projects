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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class BlogPostDaoTest {

    BlogPostDao blogPostDao;
    CategoryDao categoryDao;
    ImageDao imageDao;
    UserDao userDao;
    TagDao tagDao;
    PostTagBridgeDao postTagBridgeDao;
    BlogPostCategoryBridgeDao blogPostCategoryBridgeDao;

    public BlogPostDaoTest() {
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

        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        categoryDao = ctx.getBean("CategoryDao", CategoryDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);
        tagDao = ctx.getBean("TagDao", TagDao.class);
        postTagBridgeDao = ctx.getBean("PostTagBridgeDao", PostTagBridgeDao.class);
        blogPostCategoryBridgeDao = ctx.getBean("BlogPostCategoryBridgeDao", BlogPostCategoryBridgeDao.class);

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

        List<Category> catList = categoryDao.getAllCategories();
        for (Category cat : catList) {
            categoryDao.deleteCategory(cat.getCategoryID());
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

        List<Category> catList = categoryDao.getAllCategories();
        for (Category cat : catList) {
            categoryDao.deleteCategory(cat.getCategoryID());
        }

        List<Tag> tags = tagDao.getAllTags();
        for (Tag tag : tags) {
            tagDao.deleteTag(tag.getTagID());
        }

    }

    /**
     * Test of addBlogPost method, of class BlogPostDao.
     */
    @Test
    public void testAddGetDeleteBlogPost() {

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);
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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        BlogPost fromDB = blogPostDao.getBlogPostById(post.getBlogPostID());
        assertEquals(fromDB.getBlogPostID(), post.getBlogPostID());

        blogPostDao.deleteBlogPost(post.getBlogPostID());
        assertNull(blogPostDao.getBlogPostById(post.getBlogPostID()));
    }

    /**
     * Test of updateBlogPost method, of class BlogPostDao.
     */
    @Test
    public void testUpdateBlogPost() {

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        post.setTitle("NEW TITLE");
        blogPostDao.updateBlogPost(post);
        assertEquals(post.getTitle(), "NEW TITLE");
    }

    /**
     * Test of getAllBlogPosts method, of class BlogPostDao.
     */
    @Test
    public void testGetAllBlogPosts() {

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        assertEquals(1, blogPostDao.getAllBlogPosts().size());
    }

    /**
     * Test of getAllBlogPostsByCategoryID method, of class BlogPostDao.
     */
    @Test
    public void testGetAllBlogPostsByCategoryID() {
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
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        assertEquals(1, blogPostDao.getAllBlogPostsByCategoryID(category.getCategoryID()).size());
    }

    /**
     * Test of getAllBlogPostsByUserID method, of class BlogPostDao.
     */
    @Test
    public void testGetAllBlogPostsByUserID() {
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
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        assertEquals(1, blogPostDao.getAllBlogPostsByUserID(user.getUserID()).size());

    }

    /**
     * Test of getAllBlogPostsByTagID method, of class BlogPostDao.
     */
    @Test
    public void testGetAllBlogPostsByTagID() {
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
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeDao.addPostTagBridge(bridge);

        assertEquals(1, blogPostDao.getAllBlogPostsByTagID(tag.getTagID()).size());
    }

    /**
     * Test of getAllBlogPostsByDate method, of class BlogPostDao.
     */
    @Test
    public void testGetAllBlogPostsByDate() {
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
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        List<BlogPost> dateList = blogPostDao.getAllBlogPostsByDate(post.getStartDate());

        assertEquals(dateList.size(), 1);
    }

    /**
     * Test of getAllActiveBlogPosts method, of class BlogPostDao.
     */
    @Test
    public void testGetAllActiveBlogPosts() {

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);
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

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post2.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post2.setIsActive(false);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostDao.addBlogPost(post2);

        assertEquals(blogPostDao.getAllActiveBlogPosts().size(), 1);

    }

    /**
     * Test of getAllActiveUnexpiredBlogPosts method, of class BlogPostDao.
     */
    @Test
    public void testGetAllActiveUnexpiredBlogPosts() {

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);
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

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post2.setEndDate(LocalDate.of(2001, Month.MARCH, 1));
        post2.setIsActive(false);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostDao.addBlogPost(post2);

        BlogPost post3 = new BlogPost();
        post3.setBlogContent("CONTENT");
        post3.setBlogImage(image);
        post3.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post3.setIsActive(false);
        post3.setTitle("TITLE");
        post3.setUser(user);
        blogPostDao.addBlogPost(post3);

        assertEquals(blogPostDao.getAllActiveUnexpiredBlogPosts().size(), 1);
    }

    /**
     * Test of getAllBlogPostByActive method, of class BlogPostDao.
     */
    @Test
    public void testGetAllBlogPostByActive() {

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);
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

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post2.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post2.setIsActive(false);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostDao.addBlogPost(post2);

        assertEquals(1, blogPostDao.getAllBlogPostByActive(true).size());
    }

    /**
     * Test of setIsActiveToInActive method, of class BlogPostDao.
     */
    @Test
    public void testSetIsActiveToInActive() {
        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);
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

        blogPostDao.setIsActiveToInActive(post.getBlogPostID());

        BlogPost fromDB = blogPostDao.getBlogPostById(post.getBlogPostID());

        assertFalse(fromDB.isIsActive());
    }

    /**
     * Test of setIsActiveToActive method, of class BlogPostDao.
     */
    @Test
    public void testSetIsActiveToActive() {

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(false);
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

        blogPostDao.setIsActiveToActive(post.getBlogPostID());

        BlogPost fromDB = blogPostDao.getBlogPostById(post.getBlogPostID());

        assertTrue(fromDB.isIsActive());
    }

    /**
     * Test of getBlogPostByBlogPostCategoryBridgeID method, of class
     * BlogPostDao.
     */
    @Test
    public void testGetBlogPostByBlogPostCategoryBridgeID() {

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
        post.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostDao.addBlogPost(post);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        assertEquals(post.getBlogPostID(), blogPostDao.getBlogPostByBlogPostCategoryBridgeID(bridge.getBlogPostCategoryID()).getBlogPostID());
    }

}
