/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.BlogPostDao;
import com.sg.capstone.model.BlogPost;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author admin
 */
public class BlogPostServiceDBImpl implements BlogPostService {

    BlogPostDao blogPostDao;

    @Inject
    public BlogPostServiceDBImpl(BlogPostDao blogPostDao) {
        this.blogPostDao = blogPostDao;
    }

    @Override
    public void addBlogPost(BlogPost blogPost) {
        blogPostDao.addBlogPost(blogPost);
    }

    @Override
    public void deleteBlogPost(Long blogPostID) {
        blogPostDao.deleteBlogPost(blogPostID);
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {
        blogPostDao.updateBlogPost(blogPost);
    }

    @Override
    public BlogPost getBlogPostById(Long blogPostID) {
        return blogPostDao.getBlogPostById(blogPostID);
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogPostDao.getAllBlogPosts();
    }

    @Override
    public List<BlogPost> getAllBlogPostsByCategoryID(Long categoryID) {
        return blogPostDao.getAllBlogPostsByCategoryID(categoryID);
    }

    @Override
    public List<BlogPost> getAllBlogPostsByUserID(Long userID) {
        return blogPostDao.getAllBlogPostsByUserID(userID);
    }

    @Override
    public List<BlogPost> getAllBlogPostsByTagID(Long tagID) {
        return blogPostDao.getAllBlogPostsByTagID(tagID);
    }

    @Override
    public List<BlogPost> getAllBlogPostsByDate(LocalDate date) {
        return blogPostDao.getAllBlogPostsByDate(date);
    }

    @Override
    public List<BlogPost> getAllBlogPostByActive(boolean isActive) {
        return blogPostDao.getAllBlogPostByActive(isActive);
    }

    @Override
    public void setIsActiveToInActive(Long blogPostID) {
        blogPostDao.setIsActiveToInActive(blogPostID);
    }

    @Override
    public void setIsActiveToActive(Long blogPostID) {
        blogPostDao.setIsActiveToActive(blogPostID);
    }

    @Override
    public List<BlogPost> getAllActiveBlogPosts() {
        return blogPostDao.getAllActiveBlogPosts();
    }

    @Override
    public List<BlogPost> getAllActiveUnexpiredBlogPosts() {
        return blogPostDao.getAllActiveUnexpiredBlogPosts();
    }

    @Override
    public BlogPost getBlogPostByBlogPostCategoryBridgeID(Long blogPostCategoryBridgeID) {
        return blogPostDao.getBlogPostByBlogPostCategoryBridgeID(blogPostCategoryBridgeID);
    }

}
