/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Comment;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.PostTagBridge;
import com.sg.capstone.model.StaticPage;
import com.sg.capstone.model.Tag;
import com.sg.capstone.model.User;
import com.sg.capstone.service.BlogPostCategoryBridgeService;
import com.sg.capstone.service.BlogPostService;
import com.sg.capstone.service.CategoryService;
import com.sg.capstone.service.CommentService;
import com.sg.capstone.service.ImageService;
import com.sg.capstone.service.PostTagBridgeService;
import com.sg.capstone.service.StaticPageService;
import com.sg.capstone.service.TagService;
import com.sg.capstone.service.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author admin
 */
@Controller
public class BlogController {

    private final BlogPostCategoryBridgeService blogPostCategoryBridgeService;
    private final BlogPostService blogPostService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final PostTagBridgeService postTagBridgeService;
    private final StaticPageService staticPageService;
    private final TagService tagService;
    private final UserService userService;

    public BlogController(BlogPostCategoryBridgeService blogPostCategoryBridgeService, BlogPostService blogPostService, CategoryService categoryService, CommentService commentService, ImageService imageService, PostTagBridgeService postTagBridgeService, StaticPageService staticPageService, TagService tagService, UserService userService) {
        this.blogPostCategoryBridgeService = blogPostCategoryBridgeService;
        this.blogPostService = blogPostService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.imageService = imageService;
        this.postTagBridgeService = postTagBridgeService;
        this.staticPageService = staticPageService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @RequestMapping(value = "/blogPageCreate", method = RequestMethod.GET)
    public String displayBlogPageCreate(HttpServletRequest request, Model model) {

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        return "blogPageCreate";
    }

    @RequestMapping(value = "/createBlogPost", method = RequestMethod.POST)
    public String createBlogPost(HttpServletRequest request) {
        /*NEED TO FIGURE OUT HOW WE ARE HANDLING THE USER OBJECT IN BLOG POST*/

        Category category = new Category();
        category.setCategoryName(request.getParameter("categoryName"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
//        String isActive = request.getParameter("isActive");

        Image image = new Image();
        image.setImageName("blogImage");
        byte[] bytes = request.getParameter("image").getBytes();
        image.setImage(bytes);

        imageService.addImage(image);

        User user = new User();

        BlogPost blogPost = new BlogPost();
        blogPost.setStartDate(LocalDate.parse(startDate, formatter));
        blogPost.setEndDate(LocalDate.parse(endDate, formatter));
        blogPost.setTitle(request.getParameter("blogTitle"));
        blogPost.setBlogContent(request.getParameter("blogContent"));
//        blogPost.setIsActive(Util.parseBool(isActive));
        blogPost.setBlogImage(image);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);

        Tag tag = new Tag();
        tag.setTagName(request.getParameter("blogTag"));

        tagService.addTag(tag);

//        Adding bridges. This may not be right but giving it a try
        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(blogPost);
        bridge.setTag(tag);
        postTagBridgeService.addPostTagBridge(bridge);
        return "/";
    }

    @RequestMapping(value = "/blogPageDisplayByID", method = RequestMethod.GET)
    public String displayBlogPageByID(HttpServletRequest request, Model model) {
        String blogPostIDParameter = request.getParameter("blogPostId");
        Long blogPost = Long.parseLong(request.getParameter("blogPostID"));
        BlogPost blogPostByID = blogPostService.getBlogPostById(blogPost);
        // this next line just overrides the current blogPost user with all of the user property values
        blogPostByID.setUser(userService.getUserByID(blogPostByID.getUser().getUserID()));

        model.addAttribute("blogPostByID", blogPostByID);

        List<BlogPost> blogPostList = blogPostService.getAllBlogPosts();
        model.addAttribute("blogPostList", blogPostList);

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        List<Comment> commentList = commentService.getAllCommentsByBlogPost(blogPostByID.getBlogPostID());
        for (Comment comment : commentList) {
            comment.setUser(userService.getUserByCommentID(comment.getCommentID()));
        }
        model.addAttribute("commentList", commentList);
        return "blogPageDisplayByID";
    }

    @RequestMapping(value = "/displayAllPostsByCategory", method = RequestMethod.GET)
    public String displayAllPostsByCategory(HttpServletRequest request, Model model) {
        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);

        return "blogPageDisplayActiveByCategory";
    }

    @RequestMapping(value = "/chooseCategory/{categoryID}", method = RequestMethod.GET)
    public String chooseCategory(@PathVariable("categoryID") String categoryID, HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);

        Long newCategoryID = Long.parseLong(categoryID);
        List<BlogPostCategoryBridge> bridgeList
                = blogPostCategoryBridgeService
                        .getAllActiveBlogPostsByCategoryID(newCategoryID);

        for (BlogPostCategoryBridge bridge : bridgeList) {
            BlogPost blogPost = new BlogPost();

            bridge.setBlogPost(blogPostService.getBlogPostByBlogPostCategoryBridgeID(bridge.getBlogPostCategoryID()));
            User user = userService.getUserByBlogPostID(bridge.getBlogPost().getBlogPostID());
            blogPost.setUser(user);
            blogPost.setTitle(bridge.getBlogPost().getTitle());
            blogPost.setStartDate(bridge.getBlogPost().getStartDate());
            blogPost.setBlogPostID(bridge.getBlogPost().getBlogPostID());
            bridge.setBlogPost(blogPost);

            bridge.setCategory(categoryService.getCategoryByBlogPostCategoryBridgeID(bridge.getBlogPostCategoryID()));
        }
        model.addAttribute("bridgeList", bridgeList);
        return "blogPageDisplayActiveByCategory";
    }

}
