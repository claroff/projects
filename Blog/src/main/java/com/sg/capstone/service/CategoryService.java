/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Tag;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface CategoryService {

    public void addCategory(Category category);

    public void deleteCategory(long categoryID);

    public void editCategory(Category category);

    public Category getCategoryByID(long categoryID);

    public Category getCategoryByBlogPostCategoryBridgeID(Long blogPostCategoryBridgeID);

    public List<Category> getAllCategories();

    public List<Category> getAllCategoriesByBlogPostID(long blogPostID);

    public List<Category> getAllCategoriesByTagID(long tagID);
}
