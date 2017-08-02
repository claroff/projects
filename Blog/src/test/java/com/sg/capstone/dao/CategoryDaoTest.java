/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

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
public class CategoryDaoTest {

    CategoryDao categoryDao;
    BlogPostCategoryBridgeDao blogPostCategoryBridgeDao;
    CommentDao commentDao;
    PostTagBridgeDao postTagBridgeDao;
    UserDao userDao;
    ImageDao imageDao;
    BlogPostDao blogPostDao;
    TagDao tagDao;

    public CategoryDaoTest() {
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
        categoryDao = ctx.getBean("CategoryDao", CategoryDao.class);
        postTagBridgeDao = ctx.getBean("PostTagBridgeDao", PostTagBridgeDao.class);
        commentDao = ctx.getBean("CommentDao", CommentDao.class);
        blogPostCategoryBridgeDao = ctx.getBean("BlogPostCategoryBridgeDao", BlogPostCategoryBridgeDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        tagDao = ctx.getBean("TagDao", TagDao.class);
        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);

        List<Category> catList = categoryDao.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryDao.deleteCategory(cat.getCategoryID());
            }
        }

        List<PostTagBridge> bridgeList = postTagBridgeDao.getAllPostTagBridges();
        if (bridgeList != null) {
            for (PostTagBridge bridge : bridgeList) {
                postTagBridgeDao.deletePostTagBridge(bridge.getPostTagID());
            }
        }

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

        List<Tag> tagList = tagDao.getAllTags();
        if (tagList != null) {
            for (Tag tag : tagList) {
                tagDao.deleteTag(tag.getTagID());
            }
        }

        List<Comment> commentList = commentDao.getAllComments();
        if (commentList != null) {
            for (Comment comment : commentList) {
                commentDao.deleteComment(comment.getCommentID());
            }
        }
    }

    @After
    public void tearDown() {

        List<Category> catList = categoryDao.getAllCategories();
        if (catList != null) {
            for (Category cat : catList) {
                categoryDao.deleteCategory(cat.getCategoryID());
            }
        }

        List<PostTagBridge> bridgeList = postTagBridgeDao.getAllPostTagBridges();
        if (bridgeList != null) {
            for (PostTagBridge bridge : bridgeList) {
                postTagBridgeDao.deletePostTagBridge(bridge.getPostTagID());
            }
        }

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

        List<Tag> tagList = tagDao.getAllTags();
        if (tagList != null) {
            for (Tag tag : tagList) {
                tagDao.deleteTag(tag.getTagID());
            }
        }

        List<Comment> commentList = commentDao.getAllComments();
        if (commentList != null) {
            for (Comment comment : commentList) {
                commentDao.deleteComment(comment.getCommentID());
            }
        }
    }

    @Test
    public void testAddGetDeleteCategory() {
        Category category = new Category();
        category.setCategoryName("Food");
        categoryDao.addCategory(category);

        Category fromDao = categoryDao.getCategoryByID(category.getCategoryID());
        assertEquals(fromDao, category);

        categoryDao.deleteCategory(category.getCategoryID());

        assertNull(categoryDao.getCategoryByID(category.getCategoryID()));

    }

    @Test
    public void testEditCategory() {
        Category category = new Category();
        category.setCategoryName("Food");
        categoryDao.addCategory(category);

        categoryDao.getCategoryByID(category.getCategoryID());
        category.setCategoryName("Games");
        categoryDao.editCategory(category);

        assertEquals("Games", category.getCategoryName());
    }

    @Test
    public void testGetAllCategories() {
        Category category = new Category();
        category.setCategoryName("Food");
        categoryDao.addCategory(category);

        Category fromDao = categoryDao.getCategoryByID(category.getCategoryID());
        assertEquals(fromDao, category);

        Category category2 = new Category();
        category2.setCategoryName("Music");
        categoryDao.addCategory(category2);

        Category fromDao2 = categoryDao.getCategoryByID(category2.getCategoryID());
        assertEquals(fromDao2, category2);

        Category category3 = new Category();
        category3.setCategoryName("Games");
        categoryDao.addCategory(category3);

        Category fromDao3 = categoryDao.getCategoryByID(category3.getCategoryID());
        assertEquals(fromDao3, category3);

        List<Category> categoryList = categoryDao.getAllCategories();
        assertEquals(categoryList.size(), 3);
    }

    @Test
    public void testGetAllCategoriesByBlogPost() {

        Category category = new Category();
        category.setCategoryName("Food");
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

        assertEquals(1, categoryDao.getAllCategoriesByBlogPostID(post.getBlogPostID()).size());

    }

    @Test
    public void testGetAllCategoriesByTag() {

        Category category = new Category();
        category.setCategoryName("Food");
        categoryDao.addCategory(category);

        Tag tag = new Tag();
        tag.setTagName("NAME");
        tagDao.addTag(tag);

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

        BlogPostCategoryBridge catBridge = new BlogPostCategoryBridge();
        catBridge.setBlogPost(post);
        catBridge.setCategory(category);
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(catBridge);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(post);
        bridge.setTag(tag);
        postTagBridgeDao.addPostTagBridge(bridge);

        assertEquals(1, categoryDao.getAllCategoriesByTagID(tag.getTagID()).size());
    }

    /**
     * Test of getCategoryByBlogPostCategoryBridgeID method, of class
     * CategoryDao.
     */
    @Test
    public void testGetCategoryByBlogPostCategoryBridgeID() {

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

        assertEquals(category.getCategoryID(), categoryDao.getCategoryByBlogPostCategoryBridgeID(bridge.getBlogPostCategoryID()).getCategoryID());
    }

}
