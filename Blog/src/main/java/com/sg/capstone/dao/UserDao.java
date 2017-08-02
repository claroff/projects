/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.Authority;
import com.sg.capstone.model.User;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface UserDao {

    public void addUser(User user);

    public void deleteUser(Long userID);

    public void setUserInactive(Long userID);

    public void editUser(User user);

    public User getUserByID(Long userID);

    public List<User> getAllUsers();

    public User getUserByBlogPostID(Long blogPostID);

    public User getUserByTagID(Long tagID);

    public User getUserByCommentID(Long commentID);

    public User getUserByName(String userName);

    public void editUserAuthorities(User user, List<Authority> authorityList);

}
