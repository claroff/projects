/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

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
public class UserServiceTest {

    UserService userService;
    ImageService imageService;
    BlogPostService blogPostService;
    TagService tagService;
    CategoryService categoryService;
    PostTagBridgeService postTagBridgeService;
    CommentService commentService;
    BlogPostCategoryBridgeService blogPostCategoryBridgeService;
    AuthorityService authorityService;

    public UserServiceTest() {
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
        tagService = ctx.getBean("TagService", TagService.class);
        categoryService = ctx.getBean("CategoryService", CategoryService.class);
        postTagBridgeService = ctx.getBean("PostTagBridgeService", PostTagBridgeService.class);
        commentService = ctx.getBean("CommentService", CommentService.class);
        blogPostCategoryBridgeService = ctx.getBean("BlogPostCategoryBridgeService", BlogPostCategoryBridgeService.class);
        authorityService = ctx.getBean("AuthorityService", AuthorityService.class);

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

        List<Tag> tagList = tagService.getAllTags();
        for (Tag tag : tagList) {
            tagService.deleteTag(tag.getTagID());
        }

        List<Category> catList = categoryService.getAllCategories();
        for (Category cat : catList) {
            categoryService.deleteCategory(cat.getCategoryID());
        }

        List<Comment> commentList = commentService.getAllComments();
        for (Comment comment : commentList) {
            commentService.deleteComment(comment.getCommentID());
        }

        List<Authority> authorityList = authorityService.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityService.deleteAuthority(authority.getAuthorityID());
        }

    }

    @After
    public void tearDown() {

        List<Tag> tagList = tagService.getAllTags();
        for (Tag tag : tagList) {
            tagService.deleteTag(tag.getTagID());
        }

        List<Category> catList = categoryService.getAllCategories();
        for (Category cat : catList) {
            categoryService.deleteCategory(cat.getCategoryID());
        }

        List<Comment> commentList = commentService.getAllComments();
        for (Comment comment : commentList) {
            commentService.deleteComment(comment.getCommentID());
        }

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

        List<Authority> authorityList = authorityService.getAllAuthoritiesForReal();
        for (Authority authority : authorityList) {
            authorityService.deleteAuthority(authority.getAuthorityID());
        }
    }

    /**
     * Test of addUser method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        User fromDb = userService.getUserByID(user.getUserID());
        assertEquals(fromDb.getUserID(), user.getUserID());

        userService.deleteUser(userService.getUserByID(user.getUserID()).getUserID());
        assertNull(userService.getUserByID(user.getUserID()));

    }

    /**
     * Test of editUser method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Image image2 = new Image();
        image2.setImageName("imageName");
        image2.setImage(bytes);
        imageService.addImage(image2);

        user.setUserName("Updated name");
        user.setUserPassword("qwerty");
        user.setImage(imageService.getImageByID(image2.getImageID()));

        userService.editUser(user);
        assertEquals(user.getUserName(), "Updated name");
        assertEquals(user.getUserPassword(), "qwerty");
        assertEquals(user.getImage().getImageID(), image2.getImageID());

    }

    /**
     * Test of getAllUsers method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        User user2 = new User();
        user2.setUserName("Namee");
        user2.setUserPassword("password");
        user2.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user2);

        assertEquals(2, userService.getAllUsers().size());

    }

    /**
     * Test of getUserByBlogPost method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.now());
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 12));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);

        assertEquals(user.getUserID(), userService.getUserByBlogPostID(blogPost.getBlogPostID()).getUserID());

    }

    /**
     * Test of getUserByTag method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Tag tag = new Tag();
        tag.setTagName("TAG");
        tagService.addTag(tag);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.now());
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 12));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);

        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(blogPost);
        bridge.setTag(tag);
        postTagBridgeService.addPostTagBridge(bridge);

        assertEquals(user.getUserID(), userService.getUserByTagID(tag.getTagID()).getUserID());

    }

    /**
     * Test of getUserByComment method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Category category = new Category();
        category.setCategoryName("pineapple");
        categoryService.addCategory(category);

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.now());
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 12));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setContent("COMMENT");
        comment.setBlogPost(blogPost);
        comment.setCommentDate(LocalDate.now());
        comment.setUser(user);
        commentService.addComment(comment);

        assertEquals(userService.getUserByID(user.getUserID()), userService.getUserByCommentID(comment.getCommentID()));

    }

    /**
     * Test of setUserInactive method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        userService.setUserInactive(user.getUserID());
        User fromDB = userService.getUserByID(user.getUserID());

        assertFalse(fromDB.isIsActive());

    }

    /**
     * Test of getUserByName method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        assertEquals(user.getUserID(), userService.getUserByName("Name").getUserID());

    }

    /**
     * Test of editUserAuthorities method, of class UserService.
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
        imageService.addImage(image);
        user.setImage(imageService.getImageByID(image.getImageID()));

        userService.addUser(user);

        Authority authority = new Authority();
        authority.setAuthorityName("ROLE_USER");
        authority.setUser(user);
        authorityService.addAuthority(authority);

        user.addAuthority("ROLE_USER");

        List<Authority> authorityList = authorityService.getAllAuthorities(user.getUserID());

        user.addAuthority("ROLE_SUPERUSER");

        userService.editUserAuthorities(user, authorityList);

        assertEquals(user.getAuthority().size(), 2);
    }

}
