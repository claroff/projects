/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.Authority;
import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.PostTagBridge;
import com.sg.capstone.model.StaticPage;
import com.sg.capstone.model.Tag;
import com.sg.capstone.model.User;
import com.sg.capstone.service.AuthorityService;
import com.sg.capstone.service.BlogPostCategoryBridgeService;
import com.sg.capstone.service.BlogPostService;
import com.sg.capstone.service.CategoryService;
import com.sg.capstone.service.CommentService;
import com.sg.capstone.service.ImageService;
import com.sg.capstone.service.PostTagBridgeService;
import com.sg.capstone.service.StaticPageService;
import com.sg.capstone.service.TagService;
import com.sg.capstone.service.UserService;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@Controller
public class AdminController {

    private final BlogPostCategoryBridgeService blogPostCategoryBridgeService;
    private final BlogPostService blogPostService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final PostTagBridgeService postTagBridgeService;
    private final StaticPageService staticPageService;
    private final TagService tagService;
    private final UserService userService;
    private final AuthorityService authorityService;
    private PasswordEncoder encoder;

    @Inject
    public AdminController(BlogPostCategoryBridgeService blogPostCategoryBridgeService, BlogPostService blogPostService,
            CategoryService categoryService, CommentService commentService, ImageService imageService, PostTagBridgeService postTagBridgeService,
            StaticPageService staticPageService, TagService tagService, UserService userService, AuthorityService authorityService, PasswordEncoder encoder) {
        this.blogPostCategoryBridgeService = blogPostCategoryBridgeService;
        this.blogPostService = blogPostService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.imageService = imageService;
        this.postTagBridgeService = postTagBridgeService;
        this.staticPageService = staticPageService;
        this.tagService = tagService;
        this.userService = userService;
        this.authorityService = authorityService;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/displayAdminPage", method = RequestMethod.GET)
    public String displayAdminPage(HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        model.addAttribute("pageName", "admin");

        return "adminPage";
    }

    @RequestMapping(value = "/adminCreateNewBlogPost", method = RequestMethod.GET)
    public String displayCreateBlogPostForm(HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        return "adminCreateNewBlogPost";
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    //BURKE ADDED THIS FOR TINYMCE
    public Object uploadImage(@RequestParam("file") MultipartFile graphicFile)
            throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("status", "sucess");
        Long imageID = imageService.insertImage(graphicFile.getBytes(), graphicFile.getOriginalFilename());
        String imageString = "/image/" + imageID.toString();
        hashMap.put("location", imageString);

        return hashMap;
    }

    @RequestMapping(value = "/createNewBlogPost", method = RequestMethod.POST)
    public String createNewBlogPost(HttpServletRequest request) {

        /*NEED TO FIGURE OUT HOW WE ARE HANDLING THE USER OBJECT IN BLOG POST*/
        String categoryID = request.getParameter("category");
        Category category = categoryService.getCategoryByID(Long.parseLong(categoryID));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
//        String isActive = request.getParameter("isActive");

//        Image image = new Image();
//        image.setImageName("blogImage");
//        byte[] bytes = request.getParameter("image").getBytes();
//        image.setImage(bytes);
//        imageService.addImage(image);
        //hard coded users to 0
        User user = userService.getUserByName(request.getParameter("userName"));

//        user.setIsSuperUser(true);
//        user.setIsAdmin(true);
//        user.setUserPassword("grandma");
//        user.setImage(image);
        BlogPost blogPost = new BlogPost();
        blogPost.setStartDate(LocalDate.parse(startDate, formatter));

        if (endDate == null) {
            return "createNewBlogPost";
        }

        blogPost.setTitle(request.getParameter("blogTitle"));
        blogPost.setBlogContent(request.getParameter("blogContent"));
//        blogPost.setIsActive(Util.parseBool(isActive));
//        blogPost.setBlogImage(image);
        blogPost.setUser(user);
        blogPostService.addBlogPost(blogPost);

        BlogPostCategoryBridge catBridge = new BlogPostCategoryBridge();
        catBridge.setBlogPost(blogPost);
        catBridge.setCategory(category);
        blogPostCategoryBridgeService.addBlogPostCategoryBridge(catBridge);

        Tag tag = new Tag();
        tag.setTagName(request.getParameter("blogTag"));

        tagService.addTag(tag);

//        Adding bridges. This may not be right but giving it a try
        PostTagBridge bridge = new PostTagBridge();
        bridge.setBlogPost(blogPost);
        bridge.setTag(tag);
        postTagBridgeService.addPostTagBridge(bridge);

        return "adminPage";
    }

    @RequestMapping(value = "/blogPageDisplayAll", method = RequestMethod.GET)
    public String displayAllBlogPosts(Model model) {
        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        List<BlogPost> blogPostsList = blogPostService.getAllBlogPostByActive(true);

        for (BlogPost blogPost : blogPostsList) {
            blogPost.setUser(userService.getUserByID(blogPost.getUser().getUserID()));
        }

        model.addAttribute("blogPostsList", blogPostsList);

        return "blogPageDisplayAll";
    }

    @RequestMapping(value = "/blogPageDisplayAllInactive", method = RequestMethod.GET)
    public String displayAllInactiveBlogPosts(Model model) {
        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        List<BlogPost> blogPostsList = blogPostService.getAllBlogPostByActive(false);

        for (BlogPost blogPost : blogPostsList) {
            blogPost.setUser(userService.getUserByID(blogPost.getUser().getUserID()));
        }

        model.addAttribute("blogPostsList", blogPostsList);

        return "blogPageDisplayAllInactive";
    }

    @RequestMapping(value = "/displayAdminUpdateBlogPostPage", method = RequestMethod.GET)
    public String displayAdminUpdateBlogPost(HttpServletRequest request, Model model) {
        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        String blogID = request.getParameter("blogPostID");
        BlogPost blogPost = blogPostService.getBlogPostById(Long.parseLong(blogID));

        model.addAttribute("blogPost", blogPost);

        LocalDate startDate = blogPost.getStartDate();
        model.addAttribute("startDate", startDate);

        User user = blogPost.getUser();
        model.addAttribute("user", user);

        Image image = imageService.getImageByBlogPostID(Long.parseLong(blogID));
        model.addAttribute("image", image);

        Tag tagID = tagService.getTagByBlogPostID(Long.parseLong(blogID));
        model.addAttribute("tagID", tagID);

        return "adminUpdateBlogPost";
    }

    @RequestMapping(value = "/adminEditBlogPost", method = RequestMethod.POST)
    public String editBlogPost(@Valid @ModelAttribute("blogPost") BlogPost blogPost, BindingResult result, Model model,
            HttpServletRequest request) {

        if (result.hasErrors()) {

            return "adminUpdateBlogPost";
        }
//        String blogID = request.getParameter("blogPostID");
//        BlogPost blogPostByID = blogPostService.getBlogPostById(Long.parseLong(blogID));
//
//        model.addAttribute("blogPostByID", blogPostByID);
//
//        Image image = imageService.getImageByBlogPostID(Long.parseLong(blogID));
//        model.addAttribute("image", image);
//
//        Tag tagID = tagService.getTagByBlogPostID(Long.parseLong(blogID));
//        model.addAttribute("tagID", tagID);

        Tag tag = tagService.getTagByBlogPostID(blogPost.getBlogPostID());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String startDate = request.getParameter("startDate");
//        String endDate = request.getParameter("endDate");
//
//        BlogPost blogPost = new BlogPost();
//        blogPost.setStartDate(LocalDate.parse(startDate, formatter));
//        blogPost.setEndDate(LocalDate.parse(endDate, formatter));

        blogPostService.updateBlogPost(blogPost);
        tagService.updateTag(tag);
//        imageService.updateImage(image);

        return "adminPage";

    }

    @RequestMapping(value = "/adminEditUser", method = RequestMethod.GET)
    public String editUser(HttpServletRequest request, Model model) {

        String userIDParameter = request.getParameter("userID");
        Long userID = Long.parseLong(userIDParameter);

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        List<Image> imageList = imageService.getAllImages();
        model.addAttribute("imageList", imageList);

        User user = userService.getUserByID(userID);

        List<Authority> authorityList = authorityService.getAllAuthorities(user.getUserID());
        List<String> authorityFromDBList = new ArrayList();

        for (Authority auth : authorityList) {
            authorityFromDBList.add(auth.getAuthorityName());
        }

        boolean isAdmin = false;
        for (String string : authorityFromDBList) {
            if (string.equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        boolean isSuperUser = false;
        for (String string : authorityFromDBList) {
            if (string.equals("ROLE_SUPERUSER")) {
                isSuperUser = true;
            }
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isSuperUser", isSuperUser);
        model.addAttribute("user", user);
        return "adminEditUser";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "adminEditUser";
        }

        user.addAuthority("ROLE_USER");
        Boolean adminBool = Boolean.valueOf(request.getParameter("AdminRadio"));
        if (adminBool == true) {
            user.addAuthority("ROLE_ADMIN");
        } else {
            user.removeAuthority("ROLE_ADMIN");
        }
        Boolean superBool = Boolean.valueOf(request.getParameter("SuperUserRadio"));
        if (superBool == true) {
            user.addAuthority("ROLE_SUPERUSER");
        } else {
            user.removeAuthority("ROLE_SUPERUSER");
        }

        List<Authority> authorityList = authorityService.getAllAuthorities(user.getUserID());

        userService.editUser(user);
        userService.editUserAuthorities(user, authorityList);

        return "redirect:displayManageUser";

    }

    @RequestMapping(value = "/displayAdminCreateUser", method = RequestMethod.GET)
    public String displayCreateNewUserPage(HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        List<Image> imageList = imageService.getAllImages();
        model.addAttribute("imageList", imageList);
        model.addAttribute("staticPageList", staticPageList);

        Image defaultImage = imageService.getImageByID(Long.parseLong("1"));
        model.addAttribute("defaultImage", defaultImage);

        return "adminCreateUser";
    }

    @RequestMapping(value = "/adminDeleteBlogPost", method = RequestMethod.GET)
    public String setBlogPostInactive(HttpServletRequest request, BlogPost blogPost) {

        String blogID = request.getParameter("blogPostID");
        blogPostService.setIsActiveToInActive(Long.parseLong(blogID));

        return "adminPage";
    }

    @RequestMapping(value = "/adminSetBlogPostActive", method = RequestMethod.GET)
    public String setBlogPostActive(HttpServletRequest request, BlogPost blogPost) {

        String blogID = request.getParameter("blogPostID");
        blogPostService.setIsActiveToActive(Long.parseLong(blogID));

        return "adminPage";
    }

    /*User Stuff Below, ADMIN is Above*/
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request) {
        User user = new User();

        user.setUserName(request.getParameter("UserName"));

        String clearPw = request.getParameter("Password");
        String hashPw = encoder.encode(clearPw);
        user.setUserPassword(hashPw);

        user.addAuthority("ROLE_USER");
        Boolean adminBool = Boolean.valueOf(request.getParameter("AdminRadio"));
        if (adminBool == true) {
            user.addAuthority("ROLE_ADMIN");
        }
        Boolean superBool = Boolean.valueOf(request.getParameter("SuperUserRadio"));
        if (superBool == true) {
            user.addAuthority("ROLE_SUPERUSER");
        }

        String imageID = request.getParameter("Image");
        Image image = imageService.getImageByID(Long.parseLong(imageID));

        user.setIsActive(true);

        user.setImage(image);
        userService.addUser(user);

        return "adminPage";
    }

    @RequestMapping(value = "/displayManageUser", method = RequestMethod.GET)
    public String displayManageUserPage(HttpServletRequest request, Model model) {
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            user.setImage(imageService.getImageByID(user.getImage().getImageID()));
        }

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        model.addAttribute("userList", userList);
        return "manageUser";
    }

    @RequestMapping(value = "/setUserInactive", method = RequestMethod.GET)
    public String setUserInactive(HttpServletRequest request, Model model) {
        String UserIDParameter = request.getParameter("userID");
        Long userID = Long.parseLong(UserIDParameter);

        userService.setUserInactive(userID);
        return "redirect:displayManageUser";
    }

    @RequestMapping(value = "/adminCreateNewCategory", method = RequestMethod.GET)
    public String displayCreateCategoryForm(HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        return "adminCreateNewCategory";
    }

    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    public String createCategory(HttpServletRequest request) {
        Category category = new Category();

        category.setCategoryName(request.getParameter("CategoryName"));
        categoryService.addCategory(category);
        return "adminPage";
    }

    @RequestMapping(value = "/displayManageCategory", method = RequestMethod.GET)
    public String displayManageCategory(HttpServletRequest request, Model model) {
        List<Category> categoryList = categoryService.getAllCategories();

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        model.addAttribute("staticPageList", staticPageList);

        model.addAttribute("categoryList", categoryList);
        return "manageCategory";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
    public String deleteCategory(HttpServletRequest request, Category category) {
        Long categoryIDParameter = Long.parseLong(request.getParameter("categoryID"));
        categoryService.deleteCategory(categoryIDParameter);
        return "redirect:displayManageCategory";
    }

    @RequestMapping(value = "/adminEditCategory", method = RequestMethod.GET)
    public String editCategory(HttpServletRequest request, Model model) {
        Long categoryIDParameter = Long.parseLong(request.getParameter("categoryID"));
        Category category = categoryService.getCategoryByID(categoryIDParameter);
        model.addAttribute(category);
        return "adminEditCategory";
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    public String editUser(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "adminEditCategory";
        }
        categoryService.editCategory(category);
        return "redirect:displayManageCategory";
    }
}
