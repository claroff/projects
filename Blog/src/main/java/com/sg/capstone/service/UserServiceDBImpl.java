/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.UserDao;
import com.sg.capstone.model.Authority;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.User;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author apprentice
 */
public class UserServiceDBImpl implements UserService {

    UserDao userDao;
    ImageService imageService;

    @Inject
    public UserServiceDBImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
//        Image def = imageService.getImageByID(Long.parseLong("2"));
//        if (user.getImage() != null) {
//            def = user.getImage();
//        }
//        user.setImage(def);
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(long userID) {
        userDao.deleteUser(userID);
    }

    @Override
    public void setUserInactive(Long userID) {
        userDao.setUserInactive(userID);
    }

    @Override
    public void editUser(User user) {
        userDao.editUser(user);
    }

    @Override
    public User getUserByID(long userID) {
        return userDao.getUserByID(userID);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByBlogPostID(Long blogPostID) {
        return userDao.getUserByBlogPostID(blogPostID);
    }

    @Override
    public User getUserByTagID(Long tagID) {
        return userDao.getUserByTagID(tagID);
    }

    @Override
    public User getUserByCommentID(Long commentID) {
        return userDao.getUserByCommentID(commentID);
    }

    @Override
    public void editUserAuthorities(User user, List<Authority> authorityList) {
        userDao.editUserAuthorities(user, authorityList);
    }

    @Override
    public User getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }

}
