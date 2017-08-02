/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.Authority;
import com.sg.capstone.model.User;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface UserService {

    public void addUser(User user);

    public void deleteUser(long userID);

    public void setUserInactive(Long userID);

    public void editUser(User userID);

    public User getUserByID(long userID);

    public List<User> getAllUsers();

    public User getUserByBlogPostID(Long blogPostID);

    public User getUserByTagID(Long tagID);

    public User getUserByCommentID(Long commentID);

    public User getUserByName(String userName);

    public void editUserAuthorities(User user, List<Authority> authorityList);

}
