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
import com.sg.capstone.model.User;
import java.time.LocalDate;
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
 * @author admin
 */
public class BlogPostCategoryBridgeDaoTest {

    BlogPostCategoryBridgeDao blogPostCategoryBridgeDao;
    CategoryDao categoryDao;
    BlogPostDao blogPostDao;
    ImageDao imageDao;
    UserDao userDao;

    public BlogPostCategoryBridgeDaoTest() {
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
        userDao = ctx.getBean("UserDao", UserDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        categoryDao = ctx.getBean("CategoryDao", CategoryDao.class);
        blogPostCategoryBridgeDao
                = ctx.getBean("BlogPostCategoryBridgeDao",
                        BlogPostCategoryBridgeDao.class);

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

        List<Category> catList = categoryDao.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryDao.deleteCategory(cat.getCategoryID());
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

        List<Category> catList = categoryDao.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryDao.deleteCategory(cat.getCategoryID());
            }
        }
    }

    @Test
    public void testAddGetDeleteBlogPostCategoryBridge() {
        Category category = new Category();
        category.setCategoryName("Music");

        categoryDao.addCategory(category);

        Image image = new Image();
        image.setImageName("Guitars");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageDao.addImage(image);

        User user = new User();
        user.setUserName("John Frusciante");
        user.setUserPassword("rhcp");
        user.setImage(image);
        user.setIsActive(true);

        userDao.addUser(user);

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Music in the 90s");
        blogPost.setStartDate(start);
        blogPost.setEndDate(end);
        blogPost.setBlogContent("You'll NEVER be a legend like Prince.");
        blogPost.setIsActive(true);
        blogPost.setBlogImage(image);
        blogPost.setUser(user);

        blogPostDao.addBlogPost(blogPost);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(blogPost);
        bridge.setCategory(category);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromDao
                = blogPostCategoryBridgeDao
                        .getBlogPostCategoryBridgeById(bridge
                                .getBlogPostCategoryID());
        assertEquals(fromDao.getBlogPostCategoryID(), bridge.getBlogPostCategoryID());

        blogPostCategoryBridgeDao
                .deleteBlogPostCategoryBridge(bridge.getBlogPostCategoryID());
        BlogPostCategoryBridge bridgeDeleted = blogPostCategoryBridgeDao
                .getBlogPostCategoryBridgeById(bridge.getBlogPostCategoryID());
        assertNull(bridgeDeleted);
    }

    @Test
    public void testUpdateBlogPostCategoryBridge() {
        Category category = new Category();
        category.setCategoryName("Music");

        categoryDao.addCategory(category);

        Image image = new Image();
        image.setImageName("Guitars");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageDao.addImage(image);

        User user = new User();
        user.setUserName("John Frusciante");
        user.setUserPassword("rhcp");
        user.setImage(image);
        user.setIsActive(true);

        userDao.addUser(user);

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Music in the 90s");
        blogPost.setStartDate(start);
        blogPost.setEndDate(end);
        blogPost.setBlogContent("You will NEVER be a legend like Prince.");
        blogPost.setIsActive(true);
        blogPost.setBlogImage(image);
        blogPost.setUser(user);

        blogPostDao.addBlogPost(blogPost);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(blogPost);
        bridge.setCategory(category);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromDao = blogPostCategoryBridgeDao.getBlogPostCategoryBridgeById(bridge.getBlogPostCategoryID());
        //String categoryText = "Music";
        assertEquals(fromDao.getCategory().getCategoryID(), category.getCategoryID());
        Category category2 = new Category();
        category2.setCategoryName("Music");
        categoryDao.addCategory(category2);

        bridge.setCategory(category2);
        blogPostCategoryBridgeDao.updateBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromDao2 = blogPostCategoryBridgeDao.getBlogPostCategoryBridgeById(bridge.getBlogPostCategoryID());

        assertEquals(category2.getCategoryID(), fromDao2.getCategory().getCategoryID());
    }

    @Test
    public void testGetAllBlogPostCategoryBridge() {
        Category category = new Category();
        category.setCategoryName("Music");

        categoryDao.addCategory(category);

        Image image = new Image();
        image.setImageName("Guitars");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageDao.addImage(image);

        User user = new User();
        user.setUserName("John Frusciante");
        user.setUserPassword("rhcp");
        user.setImage(image);
        user.setIsActive(true);

        userDao.addUser(user);

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Music in the 90s");
        blogPost.setStartDate(start);
        blogPost.setEndDate(end);
        blogPost.setBlogContent("You'll NEVER be a legend like Prince.");
        blogPost.setIsActive(true);
        blogPost.setBlogImage(image);
        blogPost.setUser(user);

        blogPostDao.addBlogPost(blogPost);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(blogPost);
        bridge.setCategory(category);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromDao
                = blogPostCategoryBridgeDao
                        .getBlogPostCategoryBridgeById(bridge
                                .getBlogPostCategoryID());
        assertEquals(fromDao.getBlogPostCategoryID(), bridge.getBlogPostCategoryID());

        Category category2 = new Category();
        category2.setCategoryName("Food");

        categoryDao.addCategory(category2);

        Image image2 = new Image();
        image2.setImageName("Chicken Marsala");
        byte[] bytes2 = {(byte) 204, 29, (byte) 207, (byte) 217};
        image2.setImage(bytes2);

        imageDao.addImage(image2);

        User user2 = new User();
        user2.setUserName("Gordan Ramsay");
        user2.setUserPassword("filetOfBeef");
        user2.setImage(image2);
        user2.setIsActive(true);

        userDao.addUser(user2);

        LocalDate start2 = LocalDate.now();
        LocalDate end2 = LocalDate.now();

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setTitle("Chicken Marsala w/Asparagus");
        blogPost2.setStartDate(start2);
        blogPost2.setEndDate(end2);
        blogPost2.setBlogContent("Tasty!");
        blogPost2.setIsActive(true);
        blogPost2.setBlogImage(image2);
        blogPost2.setUser(user2);

        blogPostDao.addBlogPost(blogPost2);

        BlogPostCategoryBridge bridge2 = new BlogPostCategoryBridge();
        bridge2.setBlogPost(blogPost2);
        bridge2.setCategory(category2);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge2);

        BlogPostCategoryBridge fromDao2
                = blogPostCategoryBridgeDao
                        .getBlogPostCategoryBridgeById(bridge2
                                .getBlogPostCategoryID());
        assertEquals(fromDao2.getBlogPostCategoryID(), bridge2.getBlogPostCategoryID());

        List<BlogPostCategoryBridge> bpcbList
                = blogPostCategoryBridgeDao.getAllBlogPostCategoryBridges();
        assertEquals(bpcbList.size(), 2);
    }

    /**
     * Test of getAllBlogPostCategoryBridgesByBlogPostAndCategoryID method, of
     * class BlogPostCategoryBridgeDao.
     */
    @Test
    public void testGetAllBlogPostCategoryBridgesByBlogPostAndCategoryID() {

        Category category = new Category();
        category.setCategoryName("Music");

        categoryDao.addCategory(category);

        Category category2 = new Category();
        category2.setCategoryName("Food");

        categoryDao.addCategory(category2);

        Image image2 = new Image();
        image2.setImageName("Chicken Marsala");
        byte[] bytes2 = {(byte) 204, 29, (byte) 207, (byte) 217};
        image2.setImage(bytes2);

        imageDao.addImage(image2);

        User user2 = new User();
        user2.setUserName("Gordan Ramsay");
        user2.setUserPassword("filetOfBeef");
        user2.setImage(image2);
        user2.setIsActive(true);

        userDao.addUser(user2);

        LocalDate start2 = LocalDate.now();
        LocalDate end2 = LocalDate.now();

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setTitle("Chicken Marsala w/Asparagus");
        blogPost2.setStartDate(start2);
        blogPost2.setEndDate(end2);
        blogPost2.setBlogContent("Tasty!");
        blogPost2.setIsActive(true);
        blogPost2.setBlogImage(image2);
        blogPost2.setUser(user2);

        blogPostDao.addBlogPost(blogPost2);

        BlogPostCategoryBridge bridge2 = new BlogPostCategoryBridge();
        bridge2.setBlogPost(blogPost2);
        bridge2.setCategory(category2);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge2);

//        BlogPostCategoryBridge fromDao2
//                = blogPostCategoryBridgeDao
//                        .getBlogPostCategoryBridgeById(bridge2
//                                .getBlogPostCategoryID());
        List<BlogPostCategoryBridge> bpcbList
                = blogPostCategoryBridgeDao.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPost2.getBlogPostID(), category2.getCategoryID());
        assertEquals(bpcbList.size(), 1);
        List<BlogPostCategoryBridge> emptyList = blogPostCategoryBridgeDao.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPost2.getBlogPostID(), category.getCategoryID());
        assertEquals(emptyList.size(), 0);
    }

    @Test
    public void testGetAllActiveBlogPostsByCategoryID() {

        Category category = new Category();
        category.setCategoryName("Music");

        categoryDao.addCategory(category);

        Category category2 = new Category();
        category2.setCategoryName("Food");

        categoryDao.addCategory(category2);

        Image image2 = new Image();
        image2.setImageName("Chicken Marsala");
        byte[] bytes2 = {(byte) 204, 29, (byte) 207, (byte) 217};
        image2.setImage(bytes2);

        imageDao.addImage(image2);

        User user2 = new User();
        user2.setUserName("Gordan Ramsay");
        user2.setUserPassword("filetOfBeef");
        user2.setImage(image2);
        user2.setIsActive(true);

        userDao.addUser(user2);

        LocalDate start2 = LocalDate.now();
        LocalDate end2 = LocalDate.now();

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setTitle("Chicken Marsala w/Asparagus");
        blogPost2.setStartDate(start2);
        blogPost2.setEndDate(end2);
        blogPost2.setBlogContent("Tasty!");
        blogPost2.setIsActive(true);
        blogPost2.setBlogImage(image2);
        blogPost2.setUser(user2);

        blogPostDao.addBlogPost(blogPost2);

        BlogPostCategoryBridge bridge2 = new BlogPostCategoryBridge();
        bridge2.setBlogPost(blogPost2);
        bridge2.setCategory(category2);

        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(bridge2);

//        BlogPostCategoryBridge fromDao2
//                = blogPostCategoryBridgeDao
//                        .getBlogPostCategoryBridgeById(bridge2
//                                .getBlogPostCategoryID());
        List<BlogPostCategoryBridge> bpcbList
                = blogPostCategoryBridgeDao.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPost2.getBlogPostID(), category2.getCategoryID());
        assertEquals(bpcbList.size(), 1);
        List<BlogPostCategoryBridge> emptyList = blogPostCategoryBridgeDao.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPost2.getBlogPostID(), category.getCategoryID());
        assertEquals(emptyList.size(), 0);
    }

}
