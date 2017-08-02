/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.controller;

import com.sg.capstone.model.Image;
import com.sg.capstone.model.StaticPage;
import com.sg.capstone.model.User;
import com.sg.capstone.service.ImageService;
import com.sg.capstone.service.StaticPageService;
import com.sg.capstone.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author chandler
 */
@Controller
public class LoginController {

    private StaticPageService staticPageService;
    private ImageService imageService;
    private UserService userService;
    private PasswordEncoder encoder;

    public LoginController(StaticPageService staticPageService, ImageService imageService,
            UserService userService, PasswordEncoder encoder) {
        this.staticPageService = staticPageService;
        this.imageService = imageService;
        this.userService = userService;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
    public String createAccountForm(HttpServletRequest request, Model model) {

        List<StaticPage> staticPageList = staticPageService.getAllStaticPages();
        List<Image> imageList = imageService.getAllImages();
        model.addAttribute("imageList", imageList);
        model.addAttribute("staticPageList", staticPageList);

        return "createAccount";
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public String createAccount(HttpServletRequest request, Model model) {

        User user = new User();

        user.setUserName(request.getParameter("UserName"));

        String clearPw = request.getParameter("Password");
        String hashPw = encoder.encode(clearPw);
        user.setUserPassword(hashPw);

        user.addAuthority("ROLE_USER");

        String imageID = request.getParameter("Image");
        Image image = imageService.getImageByID(Long.parseLong(request.getParameter("Image")));

        user.setIsActive(true);

        user.setImage(image);
        userService.addUser(user);

        return "redirect:/";
    }
}
