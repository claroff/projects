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
public class BlogPostServiceTest {

    BlogPostService blogPostService;
    CategoryService categoryService;
    ImageService imageService;
    UserService userService;
    TagService tagService;
    PostTagBridgeService postTagBridgeService;
    BlogPostCategoryBridgeService blogPostCategoryBridgeService;

    public BlogPostServiceTest() {
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

        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);
        categoryService = ctx.getBean("CategoryService", CategoryService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);
        userService = ctx.getBean("UserService", UserService.class);
        tagService = ctx.getBean("TagService", TagService.class);
        postTagBridgeService = ctx.getBean("PostTagBridgeService", PostTagBridgeService.class);
        blogPostCategoryBridgeService = ctx.getBean("BlogPostCategoryBridgeService", BlogPostCategoryBridgeService.class);

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

        List<Category> catList = categoryService.getAllCategories();
        for (Category cat : catList) {
            categoryService.deleteCategory(cat.getCategoryID());
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

        List<Category> catList = categoryService.getAllCategories();
        for (Category cat : catList) {
            categoryService.deleteCategory(cat.getCategoryID());
        }

        List<Tag> tags = tagService.getAllTags();
        for (Tag tag : tags) {
            tagService.deleteTag(tag.getTagID());
        }

    }

    /**
     * Test of addBlogPost method, of class BlogPostService.
     */
    @Test
    public void testAddGetDeleteBlogPost() {

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);
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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        BlogPost fromDB = blogPostService.getBlogPostById(post.getBlogPostID());
        assertEquals(fromDB.getBlogPostID(), post.getBlogPostID());

        blogPostService.deleteBlogPost(post.getBlogPostID());
        assertNull(blogPostService.getBlogPostById(post.getBlogPostID()));
    }

    /**
     * Test of updateBlogPost method, of class BlogPostService.
     */
    @Test
    public void testUpdateBlogPost() {

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        post.setTitle("NEW TITLE");
        blogPostService.updateBlogPost(post);
        assertEquals(post.getTitle(), "NEW TITLE");
    }

    /**
     * Test of getAllBlogPosts method, of class BlogPostService.
     */
    @Test
    public void testGetAllBlogPosts() {

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

        User user = new User();
        user.setUserName("Name");
        user.setUserPassword("password");
        user.setIsActive(true);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        assertEquals(1, blogPostService.getAllBlogPosts().size());
    }

    /**
     * Test of getAllBlogPostsByCategoryID method, of class BlogPostService.
     */
    @Test
    public void testGetAllBlogPostsByCategoryID() {
        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        assertEquals(1, blogPostService.getAllBlogPostsByCategoryID(category.getCategoryID()).size());
    }

    /**
     * Test of getAllBlogPostsByUserID method, of class BlogPostService.
     */
    @Test
    public void testGetAllBlogPostsByUserID() {
        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        assertEquals(1, blogPostService.getAllBlogPostsByUserID(user.getUserID()).size());

    }

    /**
     * Test of getAllBlogPostsByTagID method, of class BlogPostService.
     */
    @Test
    public void testGetAllBlogPostsByTagID() {
        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

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

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(1, blogPostService.getAllBlogPostsByTagID(tag.getTagID()).size());
    }

    /**
     * Test of getAllBlogPostsByDate method, of class BlogPostService.
     */
    @Test
    public void testGetAllBlogPostsByDate() {
        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

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

        List<BlogPost> dateList = blogPostService.getAllBlogPostsByDate(post.getStartDate());

        assertEquals(dateList.size(), 1);
    }

    /**
     * Test of getAllActiveBlogPosts method, of class BlogPostService.
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

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post2.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post2.setIsActive(false);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostService.addBlogPost(post2);

        assertEquals(blogPostService.getAllActiveBlogPosts().size(), 1);

    }

    /**
     * Test of getAllActiveUnexpiredBlogPosts method, of class BlogPostService.
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

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post2.setEndDate(LocalDate.of(2001, Month.MARCH, 1));
        post2.setIsActive(false);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostService.addBlogPost(post2);

        BlogPost post3 = new BlogPost();
        post3.setBlogContent("CONTENT");
        post3.setBlogImage(image);
        post3.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post3.setIsActive(false);
        post3.setTitle("TITLE");
        post3.setUser(user);
        blogPostService.addBlogPost(post3);

        assertEquals(blogPostService.getAllActiveUnexpiredBlogPosts().size(), 1);
    }

    /**
     * Test of getAllBlogPostByActive method, of class BlogPostService.
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

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        post2.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        post2.setIsActive(false);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostService.addBlogPost(post2);

        assertEquals(1, blogPostService.getAllBlogPostByActive(true).size());
    }

    /**
     * Test of setIsActiveToInActive method, of class BlogPostService.
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

        blogPostService.setIsActiveToInActive(post.getBlogPostID());

        BlogPost fromDB = blogPostService.getBlogPostById(post.getBlogPostID());

        assertFalse(fromDB.isIsActive());
    }

    /**
     * Test of setIsActiveToActive method, of class BlogPostService.
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

        blogPostService.setIsActiveToActive(post.getBlogPostID());

        BlogPost fromDB = blogPostService.getBlogPostById(post.getBlogPostID());

        assertTrue(fromDB.isIsActive());
    }

    /**
     * Test of getBlogPostByBlogPostCategoryBridgeID method, of class
     * BlogPostService.
     */
    @Test
    public void testGetBlogPostByBlogPostCategoryBridgeID() {

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

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

        BlogPostCategoryBridge bridge = new BlogPostCategoryBridge();
        bridge.setBlogPost(post);
        bridge.setCategory(category);

        blogPostCategoryBridgeService.addBlogPostCategoryBridge(bridge);

        assertEquals(post.getBlogPostID(), blogPostService.getBlogPostByBlogPostCategoryBridgeID(bridge.getBlogPostCategoryID()).getBlogPostID());
    }

}
