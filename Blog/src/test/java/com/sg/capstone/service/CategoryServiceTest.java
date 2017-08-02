/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Comment;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.PostTagBridge;
import com.sg.capstone.model.Tag;
import com.sg.capstone.model.User;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class CategoryServiceTest {

    CategoryService categoryService;
    BlogPostCategoryBridgeService blogPostCategoryBridgeService;
    CommentService commentService;
    PostTagBridgeService postTagBridgeService;
    UserService userService;
    ImageService imageService;
    BlogPostService blogPostService;
    TagService tagService;

    public CategoryServiceTest() {
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
        categoryService = ctx.getBean("CategoryService", CategoryService.class);
        postTagBridgeService = ctx.getBean("PostTagBridgeService", PostTagBridgeService.class);
        commentService = ctx.getBean("CommentService", CommentService.class);
        blogPostCategoryBridgeService = ctx.getBean("BlogPostCategoryBridgeService", BlogPostCategoryBridgeService.class);
        userService = ctx.getBean("UserService", UserService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);
        tagService = ctx.getBean("TagService", TagService.class);
        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);

        List<Category> catList = categoryService.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryService.deleteCategory(cat.getCategoryID());
            }
        }

        List<PostTagBridge> bridgeList = postTagBridgeService.getAllPostTagBridges();
        if (bridgeList != null) {
            for (PostTagBridge bridge : bridgeList) {
                postTagBridgeService.deletePostTagBridge(bridge.getPostTagID());
            }
        }

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

        List<Tag> tagList = tagService.getAllTags();
        if (tagList != null) {
            for (Tag tag : tagList) {
                tagService.deleteTag(tag.getTagID());
            }
        }

        List<Comment> commentList = commentService.getAllComments();
        if (commentList != null) {
            for (Comment comment : commentList) {
                commentService.deleteComment(comment.getCommentID());
            }
        }
    }

    @After
    public void tearDown() {

        List<Category> catList = categoryService.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryService.deleteCategory(cat.getCategoryID());
            }
        }

        List<PostTagBridge> bridgeList = postTagBridgeService.getAllPostTagBridges();
        if (bridgeList != null) {
            for (PostTagBridge bridge : bridgeList) {
                postTagBridgeService.deletePostTagBridge(bridge.getPostTagID());
            }
        }

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

        List<Tag> tagList = tagService.getAllTags();
        if (tagList != null) {
            for (Tag tag : tagList) {
                tagService.deleteTag(tag.getTagID());
            }
        }

        List<Comment> commentList = commentService.getAllComments();
        if (commentList != null) {
            for (Comment comment : commentList) {
                commentService.deleteComment(comment.getCommentID());
            }
        }
    }

    @Test
    public void testAddGetDeleteCategory() {
        Category category = new Category();
        category.setCategoryName("Food");
        categoryService.addCategory(category);

        Category fromService = categoryService.getCategoryByID(category.getCategoryID());
        assertEquals(fromService, category);

        categoryService.deleteCategory(category.getCategoryID());

        assertNull(categoryService.getCategoryByID(category.getCategoryID()));

    }

    @Test
    public void testEditCategory() {
        Category category = new Category();
        category.setCategoryName("Food");
        categoryService.addCategory(category);

        categoryService.getCategoryByID(category.getCategoryID());
        category.setCategoryName("Games");
        categoryService.editCategory(category);

        assertEquals("Games", category.getCategoryName());
    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setCategoryName("Food");
        categoryService.addCategory(category);

        Category fromService = categoryService.getCategoryByID(category.getCategoryID());
        assertEquals(fromService, category);

        Category category2 = new Category();
        category2.setCategoryName("Music");
        categoryService.addCategory(category2);

        Category fromService2 = categoryService.getCategoryByID(category2.getCategoryID());
        assertEquals(fromService2, category2);

        Category category3 = new Category();
        category3.setCategoryName("Games");
        categoryService.addCategory(category3);

        Category fromService3 = categoryService.getCategoryByID(category3.getCategoryID());
        assertEquals(fromService3, category3);

        List<Category> categoryList = categoryService.getAllCategories();
        assertEquals(categoryList.size(), 3);
    }

    @Test
    public void testGetAllCategoriesByBlogPost() {

        Category category = new Category();
        category.setCategoryName("Food");
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

        assertEquals(1, categoryService.getAllCategoriesByBlogPostID(post.getBlogPostID()).size());

    }

    @Test
    public void testGetAllCategoriesByTag() {

        Category category = new Category();
        category.setCategoryName("Food");
        categoryService.addCategory(category);

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagService.addTag(tag);

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

        BlogPostCategoryBridge catBridge = new BlogPostCategoryBridge();
        catBridge.setBlogPost(post);
        catBridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(catBridge);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);
        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(1, categoryService.getAllCategoriesByTagID(tag.getTagID()).size());
    }

    /**
     * Test of getCategoryByBlogPostCategoryBridgeID method, of class
     * CategoryService.
     */
    @Test
    public void testGetCategoryByBlogPostCategoryBridgeID() {

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

        assertEquals(category.getCategoryID(), categoryService.getCategoryByBlogPostCategoryBridgeID(bridge.getBlogPostCategoryID()).getCategoryID());
    }

}
