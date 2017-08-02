/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.BlogPostCategoryBridgeDao;
import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author admin
 */
public class BlogPostCategoryBridgeServiceDBImpl
        implements BlogPostCategoryBridgeService {

    BlogPostCategoryBridgeDao blogPostCategoryBridgeDao;

    @Inject
    public BlogPostCategoryBridgeServiceDBImpl(
            BlogPostCategoryBridgeDao blogPostCategoryBridgeDao) {
        this.blogPostCategoryBridgeDao = blogPostCategoryBridgeDao;
    }

    @Override
    public void addBlogPostCategoryBridge(BlogPostCategoryBridge blogPostCategoryBridge) {
        blogPostCategoryBridgeDao.addBlogPostCategoryBridge(blogPostCategoryBridge);
    }

    @Override
    public void deleteBlogPostCategoryBridge(Long blogPostCategoryID) {
        blogPostCategoryBridgeDao.deleteBlogPostCategoryBridge(blogPostCategoryID);
    }

    @Override
    public void updateBlogPostCategoryBridge(BlogPostCategoryBridge blogPostCategoryBridge) {
        blogPostCategoryBridgeDao.updateBlogPostCategoryBridge(blogPostCategoryBridge);
    }

    @Override
    public BlogPostCategoryBridge getBlogPostCategoryBridgeById(Long blogPostCategoryID) {
        return blogPostCategoryBridgeDao.getBlogPostCategoryBridgeById(blogPostCategoryID);
    }

    @Override
    public List<BlogPostCategoryBridge> getAllBlogPostCategoryBridges() {
        return blogPostCategoryBridgeDao.getAllBlogPostCategoryBridges();
    }

    @Override
    public List<BlogPostCategoryBridge> getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(Long blogPostID, Long categoryID) {
        return blogPostCategoryBridgeDao.getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(blogPostID, categoryID);
    }

    @Override
    public List<BlogPostCategoryBridge> getAllActiveBlogPostsByCategoryID(long categoryID) {
        return blogPostCategoryBridgeDao.getAllActiveBlogPostsByCategoryID(categoryID);
    }

}
