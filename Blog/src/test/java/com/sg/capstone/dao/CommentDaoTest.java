/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Comment;
import com.sg.capstone.model.Image;
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
public class CommentDaoTest {

    CommentDao commentDao;
    BlogPostDao blogPostDao;
    CategoryDao categoryDao;
    ImageDao imageDao;
    UserDao userDao;

    public CommentDaoTest() {
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

        commentDao = ctx.getBean("CommentDao", CommentDao.class);
        blogPostDao = ctx.getBean("BlogPostDao", BlogPostDao.class);
        categoryDao = ctx.getBean("CategoryDao", CategoryDao.class);
        imageDao = ctx.getBean("ImageDao", ImageDao.class);
        userDao = ctx.getBean("UserDao", UserDao.class);

        List<Comment> comments = commentDao.getAllComments();
        for (Comment comment : comments) {
            commentDao.deleteComment(comment.getCommentID());
        }

        List<BlogPost> blogList = blogPostDao.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostDao.deleteBlogPost(post.getBlogPostID());
        }

        List<Category> catList = categoryDao.getAllCategories();
        for (Category cat : catList) {
            categoryDao.deleteCategory(cat.getCategoryID());
        }

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            userDao.deleteUser(user.getUserID());
        }

        List<Image> imageList = imageDao.getAllImages();
        for (Image image : imageList) {
            imageDao.deleteImage(image.getImageID());
        }

    }

    @After
    public void tearDown() {
        List<Comment> comments = commentDao.getAllComments();
        for (Comment comment : comments) {
            commentDao.deleteComment(comment.getCommentID());
        }

        List<BlogPost> blogList = blogPostDao.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostDao.deleteBlogPost(post.getBlogPostID());
        }

        List<Category> catList = categoryDao.getAllCategories();
        for (Category cat : catList) {
            categoryDao.deleteCategory(cat.getCategoryID());
        }

        List<User> userList = userDao.getAllUsers();
        for (User user : userList) {
            userDao.deleteUser(user.getUserID());
        }

        List<Image> imageList = imageDao.getAllImages();
        for (Image image : imageList) {
            imageDao.deleteImage(image.getImageID());
        }

    }

    /**
     * Test of addComment method, of class CommentDao.
     */
    @Test
    public void testAddGetDeleteComment() {

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

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));

        comment.setBlogPost(blogPost);
        comment.setUser(user);
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentDao.addComment(comment);

        Comment fromDB = commentDao.getCommentById(comment.getCommentID());
        assertEquals(fromDB.getCommentID(), comment.getCommentID());

        commentDao.deleteComment(comment.getCommentID());
        assertNull(commentDao.getCommentById(comment.getCommentID()));
    }

    /**
     * Test of updateComment method, of class CommentDao.
     */
    @Test
    public void testUpdateComment() {

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

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentDao.addComment(comment);
        comment.setContent("Updated Content");
        commentDao.updateComment(comment);

        Comment fromDB = commentDao.getCommentById(comment.getCommentID());
        String updatedComment = comment.getContent();
        String fromComment = fromDB.getContent();

        assertEquals(updatedComment, fromComment);
    }

    /**
     * Test of getAllComments method, of class CommentDao.
     */
    @Test
    public void testGetAllComments() {

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

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentDao.addComment(comment);

        Comment comment2 = new Comment();
        comment2.setContent("MORE CONTENT");
        comment2.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment2.setBlogPost(blogPost);
        comment2.setUser(user);
        commentDao.addComment(comment2);

        Comment comment3 = new Comment();
        comment3.setContent("DIFFERENT CONTENT");
        comment3.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment3.setBlogPost(blogPost);
        comment3.setUser(user);
        commentDao.addComment(comment3);

        assertEquals(3, commentDao.getAllComments().size());
    }

    /**
     * Test of getCommentByBlogPost method, of class CommentDao.
     */
    @Test
    public void testGetCommentByBlogPost() {

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

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentDao.addComment(comment);

        Comment comment2 = new Comment();
        comment2.setContent("MORE CONTENT");
        comment2.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment2.setBlogPost(blogPost);
        comment2.setUser(user);
        commentDao.addComment(comment2);

        Comment comment3 = new Comment();
        comment3.setContent("DIFFERENT CONTENT");
        comment3.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment3.setBlogPost(blogPost);
        comment3.setUser(user);
        commentDao.addComment(comment3);

        assertEquals(3, commentDao.getAllCommentsByBlogPost(blogPost.getBlogPostID()).size());
    }

    /**
     * Test of getCommentByUserID method, of class CommentDao.
     */
    @Test
    public void testGetCommentByUserID() {

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

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostDao.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));

        comment.setBlogPost(blogPost);
        comment.setUser(user);
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentDao.addComment(comment);

        assertEquals(1, commentDao.getAllCommentsByUserID(user.getUserID()).size());
    }

}
