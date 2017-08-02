/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Comment;
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
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class CommentController {

    private final BlogPostCategoryBridgeService blogPostCategoryBridgeService;
    private final BlogPostService blogPostService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final PostTagBridgeService postTagBridgeService;
    private final StaticPageService staticPageService;
    private final TagService tagService;
    private final UserService userService;

    public CommentController(BlogPostCategoryBridgeService blogPostCategoryBridgeService, BlogPostService blogPostService, CategoryService categoryService, CommentService commentService, ImageService imageService, PostTagBridgeService postTagBridgeService, StaticPageService staticPageService, TagService tagService, UserService userService) {
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

    @RequestMapping(value = "/postComment", method = RequestMethod.POST)
    public String postComment(HttpServletRequest request, Model model) {
        User user = new User();
        user = userService.getUserByName(request.getParameter("userName"));
        Long blogPostID = Long.parseLong(request.getParameter("blogPostID"));
        BlogPost blogPost = blogPostService.getBlogPostById(blogPostID);
        Comment comment = new Comment();

        comment.setCommentDate(LocalDate.now());
        comment.setUser(user);
        comment.setContent(request.getParameter("Comment"));
        comment.setBlogPost(blogPost);

        commentService.addComment(comment);
        return "redirect:blogPageDisplayByID?blogPostID="+ blogPostID;
    }
}
