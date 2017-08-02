/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Tag;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface CategoryDao {

    public void addCategory(Category category);

    public void deleteCategory(Long categoryID);

    public void editCategory(Category category);

    public Category getCategoryByID(Long categoryID);

    public Category getCategoryByBlogPostCategoryBridgeID(Long blogPostCategoryBridgeID);

    public List<Category> getAllCategories();

    public List<Category> getAllCategoriesByBlogPostID(Long blogPostID);

    public List<Category> getAllCategoriesByTagID(Long tagID);
}
