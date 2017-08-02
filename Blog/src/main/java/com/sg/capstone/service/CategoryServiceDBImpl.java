/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.CategoryDao;
import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Tag;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author apprentice
 */
public class CategoryServiceDBImpl implements CategoryService {

    CategoryDao categoryDao;

    @Inject
    public CategoryServiceDBImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    @Override
    public void deleteCategory(long categoryID) {
        categoryDao.deleteCategory(categoryID);
    }

    @Override
    public void editCategory(Category category) {
        categoryDao.editCategory(category);
    }

    @Override
    public Category getCategoryByID(long categoryID) {
        return categoryDao.getCategoryByID(categoryID);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public List<Category> getAllCategoriesByBlogPostID(long blogPostID) {
        return categoryDao.getAllCategoriesByBlogPostID(blogPostID);
    }

    @Override
    public List<Category> getAllCategoriesByTagID(long tagID) {
        return categoryDao.getAllCategoriesByTagID(tagID);
    }

    @Override
    public Category getCategoryByBlogPostCategoryBridgeID(Long blogPostCategoryBridgeID) {
        return categoryDao.getCategoryByBlogPostCategoryBridgeID(blogPostCategoryBridgeID);
    }
}
