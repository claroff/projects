/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
import java.util.List;

/**
 *
 * @author admin
 */
public interface BlogPostCategoryBridgeDao {

    public void addBlogPostCategoryBridge(BlogPostCategoryBridge blogPostCategoryBridge);

    public void deleteBlogPostCategoryBridge(Long blogPostCategoryID);

    public void updateBlogPostCategoryBridge(BlogPostCategoryBridge blogPostCategoryBridge);

    public BlogPostCategoryBridge getBlogPostCategoryBridgeById(Long blogPostCategoryID);

    public List<BlogPostCategoryBridge> getAllBlogPostCategoryBridges();

    public List<BlogPostCategoryBridge> getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(Long blogPostID, Long categoryID);

    public List<BlogPostCategoryBridge> getAllActiveBlogPostsByCategoryID(long categoryID);

}
