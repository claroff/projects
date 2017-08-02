/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.Authority;
import com.sg.capstone.model.BlogPost;
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
 * @author apprentice
 */
public class UserDaoTest {
    
    UserDao userDao;
    ImageDao imageDao;
    BlogPostDao blogPostDao;
    TagDao tagDao;
    CategoryDao categoryDao;
    PostTagBridgeDao postTagBridgeDao;
    CommentDao commentDao;
    BlogPostCategoryBridgeDao blogPostCategoryBridgeDao;
    AuthorityDao authorityDao;
    
    public UserDaoTest() {
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
        tagDao = ctx.getBean("TagDao", TagDao.class);
        categoryDao = ctx.getBean("CategoryDao", CategoryDao.class);
        postTagBridgeDao = ctx.getBean("PostTagBridgeDao", PostTagBridgeDao.class);
        commentDao = ctx.getBean("CommentDao", CommentDao.class);
        blogPostCategoryBridgeDao = ctx.getBean("BlogPostCategoryBridgeDao", BlogPostCategoryBridgeDao.class);
        authorityDao = ctx.getBean("AuthorityDao", AuthorityDao.class);
        
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
        
        List<Tag> tagList = tagDao.getAllTags();
        for (Tag tag : tagList) {
            tagDao.deleteTag(tag.getTagID());
        }
        
        List<Category> catList = categoryDao.getAllCategories();
        for (Category cat : catList) {
            categoryDao.deleteCategory(cat.getCategoryID());
        }
        
        List<Comment> commentList = commentDao.getAllComments();
        for (Comment comment : commentList) {
            commentDao.deleteComment(comment.getCommentID());
        }
        
        List<Authority> authorityList = authorityDao.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityDao.deleteAuthority(authority.getAuthorityID());
        }
        
    }
    
    @After
    public void tearDown() {
        
        List<Tag> tagList = tagDao.getAllTags();
        for (Tag tag : tagList) {
            tagDao.deleteTag(tag.getTagID());
        }
        
        List<Category> catList = categoryDao.getAllCategories();
        for (Category cat : catList) {
            categoryDao.deleteCategory(cat.getCategoryID());
        }
        
        List<Comment> commentList = commentDao.getAllComments();
        for (Comment comment : commentList) {
            commentDao.deleteComment(comment.getCommentID());
        }
        
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
        
        List<Authority> authorityList = authorityDao.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityDao.deleteAuthority(authority.getAuthorityID());
        }
    }

    /**
     * Test of addUser method, of class UserDao.
     */
    @Test
    public void testAddGetDeleteUser() {
        
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
        
        User fromDb = userDao.getUserByID(user.getUserID());
        assertEquals(fromDb.getUserID(), user.getUserID());
        
        userDao.deleteUser(userDao.getUserByID(user.getUserID()).getUserID());
        assertNull(userDao.getUserByID(user.getUserID()));
        
    }

    /**
     * Test of editUser method, of class UserDao.
     */
    @Test
    public void testEditUser() {
        
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
        
        Image image2 = new Image();
        image2.setImageName("imageName");
        image2.setImage(bytes);
        imageDao.addImage(image2);
        
        user.setUserName("Updated name");
        user.setUserPassword("qwerty");
        user.setImage(imageDao.getImageByID(image2.getImageID()));
        
        userDao.editUser(user);
        assertEquals(user.getUserName(), "Updated name");
        assertEquals(user.getUserPassword(), "qwerty");
        assertEquals(user.getImage().getImageID(), image2.getImageID());
        
    }

    /**
     * Test of getAllUsers method, of class UserDao.
     */
    @Test
    public void testGetAllUsers() {
        
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
        
        User user2 = new User();
        user2.setUserName("Namee");
        user2.setUserPassword("password");
        user2.setImage(imageDao.getImageByID(image.getImageID()));
        
        userDao.addUser(user2);
        
        assertEquals(2, userDao.getAllUsers().size());
        
    }

    /**
     * Test of getUserByBlogPost method, of class UserDao.
     */
    @Test
    public void testUserGetByBlogPost() {
        
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
        
        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.now());
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 12));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);
        
        assertEquals(user.getUserID(), userDao.getUserByBlogPostID(blogPost.getBlogPostID()).getUserID());
        
    }

    /**
     * Test of getUserByTag method, of class UserDao.
     */
    @Test
    public void testGetUserByTag() {
        
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
        
        Tag tag = new Tag();
        tag.setTagName("TAG");
        tagDao.addTag(tag);
        
        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.now());
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 12));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);
        
        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(blogPost);
        bridge.setTag(tag);
        postTagBridgeDao.addPostTagBridge(bridge);
        
        assertEquals(user.getUserID(), userDao.getUserByTagID(tag.getTagID()).getUserID());
        
    }

    /**
     * Test of getUserByComment method, of class UserDao.
     */
    @Test
    public void testGetUserByComment() {
        
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
        
        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryDao.addCategory(category);
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.now());
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 12));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);
        
        Comment comment = new Comment();
        comment.setContent("COMMENT");
        comment.setBlogPost(blogPost);
        comment.setCommentDate(LocalDate.now());
        comment.setUser(user);
        commentDao.addComment(comment);
        
        assertEquals(userDao.getUserByID(user.getUserID()), userDao.getUserByCommentID(comment.getCommentID()));
        
    }

    /**
     * Test of setUserInactive method, of class UserDao.
     */
    @Test
    public void testSetUserInactive() {
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
        
        userDao.setUserInactive(user.getUserID());
        User fromDB = userDao.getUserByID(user.getUserID());
        
        assertFalse(fromDB.isIsActive());
        
    }

    /**
     * Test of getUserByName method, of class UserDao.
     */
    @Test
    public void testGetUserByName() {
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
        
        assertEquals(user.getUserID(), userDao.getUserByName("Name").getUserID());
        
    }

    /**
     * Test of editUserAuthorities method, of class UserDao.
     */
    @Test
    public void testEditUserAuthorities() {
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
        
        Authority authority = new Authority();
        authority.setAuthorityName("ROLE_USER");
        authority.setUser(user);
        authorityDao.addAuthority(authority);

        user.addAuthority("ROLE_USER");
        
        List<Authority> authorityList = authorityDao.getAllAuthorities(user.getUserID());
        
        user.addAuthority("ROLE_SUPERUSER");
        
        userDao.editUserAuthorities(user, authorityList);
        
        assertEquals(user.getAuthority().size(), 2);
    }
    
}
