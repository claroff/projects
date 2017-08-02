/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

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
public class BlogPostCategoryBridgeServiceTest {

    BlogPostCategoryBridgeService blogPostCategoryBridgeService;
    CategoryService categoryService;
    BlogPostService blogPostService;
    ImageService imageService;
    UserService userService;

    public BlogPostCategoryBridgeServiceTest() {
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
        userService = ctx.getBean("UserService", UserService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);
        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);
        categoryService = ctx.getBean("CategoryService", CategoryService.class);
        blogPostCategoryBridgeService
                = ctx.getBean("BlogPostCategoryBridgeService",
                        BlogPostCategoryBridgeService.class);

        List<BlogPost> blogList = blogPostService.getAllBlogPosts();
        if (blogList != null) {
            for (BlogPost post : blogList) {
                blogPostService.deleteBlogPost(post.getBlogPostID());
            }
        }

        List<User> userList = userService.getAllUsers();
        if (userList != null) {
            for (User user : userList) {
                userService.deleteUser(user.getUserID());
            }
        }

        List<Image> imageList = imageService.getAllImages();
        if (imageList != null) {
            for (Image image : imageList) {
                imageService.deleteImage(image.getImageID());
            }
        }

        List<Category> catList = categoryService.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryService.deleteCategory(cat.getCategoryID());
            }
        }
    }

    @After
    public void tearDown() {

        List<BlogPost> blogList = blogPostService.getAllBlogPosts();
        if (blogList != null) {
            for (BlogPost post : blogList) {
                blogPostService.deleteBlogPost(post.getBlogPostID());
            }
        }

        List<User> userList = userService.getAllUsers();
        if (userList != null) {
            for (User user : userList) {
                userService.deleteUser(user.getUserID());
            }
        }

        List<Image> imageList = imageService.getAllImages();
        if (imageList != null) {
            for (Image image : imageList) {
                imageService.deleteImage(image.getImageID());
            }
        }

        List<Category> catList = categoryService.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryService.deleteCategory(cat.getCategoryID());
            }
        }
    }

    @Test
    public void testAddGetDeleteBlogPostCategoryBridge() {
        Category category = new Category();
        category.setCategoryName("Music");

        categoryService.addCategory(category);

        Image image = new Image();
        image.setImageName("Guitars");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageService.addImage(image);

        User user = new User();
        user.setUserName("John Frusciante");
        user.setUserPassword("rhcp");
        user.setImage(image);
        user.setIsActive(true);

        userService.addUser(user);

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

        blogPostService.addBlogPost(blogPost);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(blogPost);
        bridge.setCategory(category);

        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromService
                = blogPostCategoryBridgeService
                        .getBlogPostCategoryBridgeById(bridge
                                .getBlogPostCategoryID());
        assertEquals(fromService.getBlogPostCategoryID(), bridge.getBlogPostCategoryID());

        blogPostCategoryBridgeService
                .deleteBlogPostCategoryBridge(bridge.getBlogPostCategoryID());
        BlogPostCategoryBridge bridgeDeleted = blogPostCategoryBridgeService
                .getBlogPostCategoryBridgeById(bridge.getBlogPostCategoryID());
        assertNull(bridgeDeleted);
    }

    @Test
    public void testUpdateBlogPostCategoryBridge() {
        Category category = new Category();
        category.setCategoryName("Music");

        categoryService.addCategory(category);

        Image image = new Image();
        image.setImageName("Guitars");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageService.addImage(image);

        User user = new User();
        user.setUserName("John Frusciante");
        user.setUserPassword("rhcp");
        user.setImage(image);
        user.setIsActive(true);

        userService.addUser(user);

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

        blogPostService.addBlogPost(blogPost);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(blogPost);
        bridge.setCategory(category);

        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromService = blogPostCategoryBridgeService.getBlogPostCategoryBridgeById(bridge.getBlogPostCategoryID());
        //String categoryText = "Music";
        assertEquals(fromService.getCategory().getCategoryID(), category.getCategoryID());
        Category category2 = new Category();
        category2.setCategoryName("Music");
        categoryService.addCategory(category2);

        bridge.setCategory(category2);
        blogPostCategoryBridgeService.updateBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromService2 = blogPostCategoryBridgeService.getBlogPostCategoryBridgeById(bridge.getBlogPostCategoryID());

        assertEquals(category2.getCategoryID(), fromService2.getCategory().getCategoryID());
    }

    @Test
    public void testGetAllBlogPostCategoryBridge() {
        Category category = new Category();
        category.setCategoryName("Music");

        categoryService.addCategory(category);

        Image image = new Image();
        image.setImageName("Guitars");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);

        imageService.addImage(image);

        User user = new User();
        user.setUserName("John Frusciante");
        user.setUserPassword("rhcp");
        user.setImage(image);
        user.setIsActive(true);

        userService.addUser(user);

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

        blogPostService.addBlogPost(blogPost);

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(blogPost);
        bridge.setCategory(category);

        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        BlogPostCategoryBridge fromService
                = blogPostCategoryBridgeService
                        .getBlogPostCategoryBridgeById(bridge
                                .getBlogPostCategoryID());
        assertEquals(fromService.getBlogPostCategoryID(), bridge.getBlogPostCategoryID());

        Category category2 = new Category();
        category2.setCategoryName("Food");

        categoryService.addCategory(category2);

        Image image2 = new Image();
        image2.setImageName("Chicken Marsala");
        byte[] bytes2 = {(byte) 204, 29, (byte) 207, (byte) 217};
        image2.setImage(bytes2);

        imageService.addImage(image2);

        User user2 = new User();
        user2.setUserName("Gordan Ramsay");
        user2.setUserPassword("filetOfBeef");
        user2.setImage(image2);
        user2.setIsActive(true);

        userService.addUser(user2);

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

        blogPostService.addBlogPost(blogPost2);

        BlogPostCategoryBridge bridge2 = new BlogPostCategoryBridge();
        bridge2.setBlogPost(blogPost2);
        bridge2.setCategory(category2);

        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge2);

        BlogPostCategoryBridge fromService2
                = blogPostCategoryBridgeService
                        .getBlogPostCategoryBridgeById(bridge2
                                .getBlogPostCategoryID());
        assertEquals(fromService2.getBlogPostCategoryID(), bridge2.getBlogPostCategoryID());

        List<BlogPostCategoryBridge> bpcbList
                = blogPostCategoryBridgeService.getAllBlogPostCategoryBridges();
        assertEquals(bpcbList.size(), 2);
    }

    /**
     * Test of getAllBlogPostCategoryBridgesByBlogPostAndCategoryID method, of
     * class BlogPostCategoryBridgeService.
     */
    @Test
    public void testGetAllBlogPostCategoryBridgesByBlogPostAndCategoryID() {

        Category category = new Category();
        category.setCategoryName("Music");

        categoryService.addCategory(category);

        Category category2 = new Category();
        category2.setCategoryName("Food");

        categoryService.addCategory(category2);

        Image image2 = new Image();
        image2.setImageName("Chicken Marsala");
        byte[] bytes2 = {(byte) 204, 29, (byte) 207, (byte) 217};
        image2.setImage(bytes2);

        imageService.addImage(image2);

        User user2 = new User();
        user2.setUserName("Gordan Ramsay");
        user2.setUserPassword("filetOfBeef");
        user2.setImage(image2);
        user2.setIsActive(true);

        userService.addUser(user2);

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

        blogPostService.addBlogPost(blogPost2);

        BlogPostCategoryBridge bridge2 = new BlogPostCategoryBridge();
        bridge2.setBlogPost(blogPost2);
        bridge2.setCategory(category2);

        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge2);

//        BlogPostCategoryBridge fromService2
//                = blogPostCategoryBridgeService
//                        .getBlogPostCategoryBridgeById(bridge2
//                                .getBlogPostCategoryID());
        List<BlogPostCategoryBridge> bpcbList
                = blogPostCategoryBridgeService.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPost2.getBlogPostID(), category2.getCategoryID());
        assertEquals(bpcbList.size(), 1);
        List<BlogPostCategoryBridge> emptyList = blogPostCategoryBridgeService.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPost2.getBlogPostID(), category.getCategoryID());
        assertEquals(emptyList.size(), 0);
    }

}
