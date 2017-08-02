/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.StaticPage;
import com.sg.capstone.service.BlogPostService;
import com.sg.capstone.service.ImageService;
import com.sg.capstone.service.StaticPageService;
import com.sg.capstone.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author admin
 */
@Controller
public class HomeController {

    private BlogPostService blogPostService;
    private ImageService imageService;
    private StaticPageService staticPageService;
    private final UserService userService;

    public HomeController(BlogPostService blogPostService, ImageService imageService, StaticPageService staticPageService, UserService userService) {
        this.blogPostService = blogPostService;
        this.imageService = imageService;
        this.staticPageService = staticPageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayHomePage(HttpServletRequest request, Model model) {

        List<BlogPost> blogPostList = blogPostService.getAllActiveUnexpiredBlogPosts();
        for (BlogPost blogPost : blogPostList) {
            blogPost.setUser(userService.getUserByBlogPostID(blogPost.getBlogPostID()));
        }
        model.addAttribute("blogPostList", blogPostList);

        List<Image> pictureList = imageService.getAllImages();
        model.addAttribute("pictureList", pictureList);

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        model.addAttribute("pageName", "index");

        return "home";
    }

    @RequestMapping(value = "/staticPage", method = RequestMethod.GET)
    public String viewStaticPage(Model model) {

        return "staticPage";
    }

    @RequestMapping(value = "/blogPage", method = RequestMethod.GET)
    public String viewBlogPage(Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        return "blogPage";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String signInPage(Model model) {

        return "signIn";
    }

}
