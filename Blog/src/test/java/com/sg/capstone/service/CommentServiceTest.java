/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

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
public class CommentServiceTest {
    
    CommentService commentService;
    BlogPostService blogPostService;
    CategoryService categoryService;
    ImageService imageService;
    UserService userService;
    
    public CommentServiceTest() {
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
        
        commentService = ctx.getBean("CommentService", CommentService.class);
        blogPostService = ctx.getBean("BlogPostService", BlogPostService.class);
        categoryService = ctx.getBean("CategoryService", CategoryService.class);
        imageService = ctx.getBean("ImageService", ImageService.class);
        userService = ctx.getBean("UserService", UserService.class);
        
        List<Comment> comments = commentService.getAllComments();
        for (Comment comment : comments) {
            commentService.deleteComment(comment.getCommentID());
        }
        
        List<BlogPost> blogList = blogPostService.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostService.deleteBlogPost(post.getBlogPostID());
        }
        
        List<Category> catList = categoryService.getAllCategories();
        for (Category cat : catList) {
            categoryService.deleteCategory(cat.getCategoryID());
        }
        
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            userService.deleteUser(user.getUserID());
        }
        
        List<Image> imageList = imageService.getAllImages();
        for (Image image : imageList) {
            imageService.deleteImage(image.getImageID());
        }
        
    }
    
    @After
    public void tearDown() {
        List<Comment> comments = commentService.getAllComments();
        for (Comment comment : comments) {
            commentService.deleteComment(comment.getCommentID());
        }
        
        List<BlogPost> blogList = blogPostService.getAllBlogPosts();
        for (BlogPost post : blogList) {
            blogPostService.deleteBlogPost(post.getBlogPostID());
        }
        
        List<Category> catList = categoryService.getAllCategories();
        for (Category cat : catList) {
            categoryService.deleteCategory(cat.getCategoryID());
        }
        
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            userService.deleteUser(user.getUserID());
        }
        
        List<Image> imageList = imageService.getAllImages();
        for (Image image : imageList) {
            imageService.deleteImage(image.getImageID());
        }
        
    }

    /**
     * Test of addComment method, of class CommentService.
     */
    @Test
    public void testAddGetDeleteComment() {
        
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
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);
        
        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentService.addComment(comment);
        
        Comment fromDB = commentService.getCommentById(comment.getCommentID());
        assertEquals(fromDB.getCommentID(), comment.getCommentID());
        
        commentService.deleteComment(comment.getCommentID());
        assertNull(commentService.getCommentById(comment.getCommentID()));
    }

    /**
     * Test of updateComment method, of class CommentService.
     */
    @Test
    public void testUpdateComment() {
        
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
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);
        
        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentService.addComment(comment);
        comment.setContent("Updated Content");
        commentService.updateComment(comment);
        
        Comment fromDB = commentService.getCommentById(comment.getCommentID());
        String updatedComment = comment.getContent();
        String fromComment = fromDB.getContent();
        
        assertEquals(updatedComment, fromComment);
    }

    /**
     * Test of getAllComments method, of class CommentService.
     */
    @Test
    public void testGetAllComments() {
        
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
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);
        
        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentService.addComment(comment);
        
        Comment comment2 = new Comment();
        comment2.setContent("MORE CONTENT");
        comment2.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment2.setBlogPost(blogPost);
        comment2.setUser(user);
        commentService.addComment(comment2);
        
        Comment comment3 = new Comment();
        comment3.setContent("DIFFERENT CONTENT");
        comment3.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment3.setBlogPost(blogPost);
        comment3.setUser(user);
        commentService.addComment(comment3);
        
        assertEquals(3, commentService.getAllComments().size());
    }

    /**
     * Test of getCommentByBlogPost method, of class CommentService.
     */
    @Test
    public void testGetCommentByBlogPost() {
        
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
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);
        
        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentService.addComment(comment);
        
        Comment comment2 = new Comment();
        comment2.setContent("MORE CONTENT");
        comment2.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment2.setBlogPost(blogPost);
        comment2.setUser(user);
        commentService.addComment(comment2);
        
        Comment comment3 = new Comment();
        comment3.setContent("DIFFERENT CONTENT");
        comment3.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        comment3.setBlogPost(blogPost);
        comment3.setUser(user);
        commentService.addComment(comment3);
        
        assertEquals(3, commentService.getAllCommentsByBlogPost(blogPost.getBlogPostID()).size());
    }

    /**
     * Test of getCommentByUserID method, of class CommentService.
     */
    @Test
    public void testGetCommentByUserID() {
        
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
        
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("title");
        blogPost.setBlogContent("content");
        blogPost.setBlogImage(image);
        blogPost.setStartDate(LocalDate.of(2000, Month.MARCH, 1));
        blogPost.setEndDate(LocalDate.of(3000, Month.MARCH, 1));
        blogPost.setIsActive(true);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);
        
        Comment comment = new Comment();
        comment.setContent("CONTENT");
        comment.setCommentDate(LocalDate.of(2000, Month.MARCH, 1));
        
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        commentService.addComment(comment);
        
        assertEquals(1, commentService.getAllCommentsByUserID(user.getUserID()).size());
    }
    
}
