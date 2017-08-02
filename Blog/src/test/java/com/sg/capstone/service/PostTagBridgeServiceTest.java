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
public class PostTagBridgeServiceTest {

    PostTagBridgeService postTagBridgeService;
    TagService tagService;
    BlogPostService blogPostService;
    CategoryService categoryService;
    ImageService imageService;
    UserService userService;
    BlogPostCategoryBridgeService blogPostCategoryBridgeService;

    public PostTagBridgeServiceTest() {
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

        postTagBridgeService
                = ctx.getBean("PostTagBridgeService", PostTagBridgeService.class);
        tagService = ctx.getBean("TagService", TagService.class);
        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);
        categoryService = ctx.getBean("CategoryService", CategoryService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);
        userService = ctx.getBean("UserService", UserService.class);
        blogPostCategoryBridgeService = ctx.getBean("BlogPostCategoryBridgeService", BlogPostCategoryBridgeService.class);

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

        List<Tag> tags = tagService.getAllTags();
        if (tags != null) {
            for (Tag tag : tags) {
                tagService.deleteTag(tag.getTagID());
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

        List<Tag> tags = tagService.getAllTags();
        if (tags != null) {
            for (Tag tag : tags) {
                tagService.deleteTag(tag.getTagID());
            }
        }
    }

    /**
     * Test of addPostTagBridge method, of class PostTagBridgeService.
     */
    @Test
    public void testAddPostTagBridge() {

        Image image = new Image();
        image.setImageName("imageName");
        byte[] bytes = {(byte) 204, 29, (byte) 207, (byte) 217};
        image.setImage(bytes);
        imageService.addImage(image);

        User user = new User();
        user.setUserName("Name");
        
        
        user.setUserPassword("password");
        user.setImage(image);
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

        BlogPost fromDB = blogPostService.getBlogPostById(post.getBlogPostID());
        assertEquals(fromDB.getBlogPostID(), post.getBlogPostID());

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

        PostTagBridge ptb = new PostTagBridge();
        ptb.setBlogPost(post);
        ptb.setTag(tag);

        postTagBridgeService.addPostTagBridge(ptb);

        PostTagBridge res = postTagBridgeService.getPostTagBridgeByID(ptb.getPostTagID());
        assertEquals(ptb.getPostTagID(), res.getPostTagID());

    }

    /**
     * Test of addPostTagBridge method, of class PostTagBridgeService.
     */
    @Test
    public void testAddGetDeletePostTagBridge() {
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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        PostTagBridge fromDB = postTagBridgeService.getPostTagBridgeByID(bridge.getPostTagID());
        assertEquals(fromDB.getPostTagID(), bridge.getPostTagID());

        postTagBridgeService.deletePostTagBridge(bridge.getPostTagID());
        assertNull(postTagBridgeService.getPostTagBridgeByID(bridge.getPostTagID()));
    }

    /**
     * Test of editPostTagBridge method, of class PostTagBridgeService.
     */
    @Test
    public void testEditPostTagBridge() {

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        BlogPost post2 = new BlogPost();
        post2.setBlogContent("CONTENT");
        post2.setBlogImage(image);
        post2.setStartDate(LocalDate.now());
        post2.setEndDate(null);
        post2.setIsActive(true);
        post2.setTitle("TITLE");
        post2.setUser(user);
        blogPostService.addBlogPost(post2);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        bridge.setBlogPost(post2);
        postTagBridgeService.editPostTagBridge(bridge);
        assertEquals(bridge.getBlogPost().getBlogPostID(), post2.getBlogPostID());
    }

    /**
     * Test of getAllPostTagBridges method, of class PostTagBridgeService.
     */
    @Test
    public void testGetAllPostTagBridges() {

        List<PostTagBridge> bridgeList = postTagBridgeService.getAllPostTagBridges();

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        PostTagBridge bridge2 = new PostTagBridge();
        bridge2.setBlogPost(post);
        bridge2.setTag(tag);

        PostTagBridge bridge3 = new PostTagBridge();
        bridge3.setBlogPost(post);
        bridge3.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);
        postTagBridgeService.addPostTagBridge(bridge2);
        postTagBridgeService.addPostTagBridge(bridge3);

        assertEquals(3, postTagBridgeService.getAllPostTagBridges().size());
    }

    /**
     * Test of getPostTagBridgeByTagAndPostIDs method, of class
     * PostTagBridgeService.
     */
    @Test
    public void testGetPostTagBridgeByTagAndPostIDs() {
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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(bridge.getPostTagID(), postTagBridgeService.getPostTagBridgeByTagAndPostIDs(tag.getTagID(), post.getBlogPostID()).getPostTagID());
    }

    /**
     * Test of getAllPostTagBridgesByPostID method, of class
     * PostTagBridgeService.
     */
    @Test
    public void testGetAllPostTagBridgesByPostID() {
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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(1, postTagBridgeService.getAllPostTagBridgesByPostID(post.getBlogPostID()).size());
    }

    /**
     * Test of getAllPostTagBridgesByTagID method, of class
     * PostTagBridgeService.
     */
    @Test
    public void testGetAllPostTagBridgesByTagID() {

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
        post.setStartDate(LocalDate.now());
        post.setEndDate(null);
        post.setIsActive(true);
        post.setTitle("TITLE");
        post.setUser(user);
        blogPostService.addBlogPost(post);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);

        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(1, postTagBridgeService.getAllPostTagBridgesByTagID(tag.getTagID()).size());
    }

}
