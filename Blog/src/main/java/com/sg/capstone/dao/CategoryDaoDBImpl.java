/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class CategoryDaoDBImpl implements CategoryDao {

    JdbcTemplate jdbcTemplate;

    @Inject
    public CategoryDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_CATEGORY
            = "insert into Category (CategoryName) "
            + "value (?)";

    private static final String SQL_DELETE_CATEGORY
            = "delete from Category where CategoryID = ?";

    private static final String SQL_DELETE_BLOG_POST_CATEGORY_BRIDGE
            = "delete from BlogPostCategoryBridge where CategoryID = ?";

    private static final String SQL_UPDATE_CATEGORY
            = "update Category set CategoryName = ? where CategoryID = ?";

    private static final String SQL_SELECT_CATEGORY
            = "select * from Category where CategoryID = ?";

    private static final String SQL_SELECT_CATEGORY_BY_BRIDGEID
            = "select * from Category "
            + "join BlogPostCategoryBridge on BlogPostCategoryBridge.CategoryID = Category.CategoryID "
            + "where BlogPostCategoryBridgeID = ?";

    private static final String SQL_SELECT_ALL_CATEGORIES
            = "select * from Category";

    private static final String SQL_SELECT_ALL_CATEGORIES_BY_BLOGPOST
            = "select * from Category c "
            + "join BlogPostCategoryBridge b on b.CategoryID = c.CategoryID "
            + "join BlogPost p on p.BlogPostID = b.BlogPostID "
            + "where p.BlogPostID = ?";

    private static final String SQL_SELECT_ALL_CATEGORIES_BY_TAG
            = "select * from Category "
            + "join BlogPostCategoryBridge on BlogPostCategoryBridge.CategoryID = Category.CategoryID "
            + "join BlogPost on BlogPost.BlogPostID = BlogPostCategoryBridge.BlogPostID "
            + "join PostTagBridge on PostTagBridge.BlogPostID = BlogPost.BlogPostID "
            + "join Tag on Tag.TagID = PostTagBridge.TagID "
            + "where Tag.TagID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCategory(Category category) {
        jdbcTemplate.update(SQL_INSERT_CATEGORY,
                category.getCategoryName());

        long categoryID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);

        category.setCategoryID(categoryID);

    }

    @Override
    public void deleteCategory(Long categoryID) {
        jdbcTemplate.update(SQL_DELETE_BLOG_POST_CATEGORY_BRIDGE, categoryID);
        jdbcTemplate.update(SQL_DELETE_CATEGORY, categoryID);
    }

    @Override
    public void editCategory(Category category) {
        jdbcTemplate.update(SQL_UPDATE_CATEGORY,
                category.getCategoryName(),
                category.getCategoryID());
    }

    @Override
    public Category getCategoryByID(Long categoryID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_CATEGORY, new CategoryMapper(), categoryID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Category getCategoryByBlogPostCategoryBridgeID(Long blogPostCategoryBridgeID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_CATEGORY_BY_BRIDGEID, new CategoryMapper(), blogPostCategoryBridgeID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(SQL_SELECT_ALL_CATEGORIES, new CategoryMapper());
    }

    @Override
    public List<Category> getAllCategoriesByBlogPostID(Long blogPostID) {
        return jdbcTemplate.query(SQL_SELECT_ALL_CATEGORIES_BY_BLOGPOST, new CategoryMapper(), blogPostID);
    }

    @Override
    public List<Category> getAllCategoriesByTagID(Long tagID) {
        return jdbcTemplate.query(SQL_SELECT_ALL_CATEGORIES_BY_TAG, new CategoryMapper(), tagID);
    }

    private static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category category = new Category();
            category.setCategoryName(rs.getString("CategoryName"));
            category.setCategoryID(rs.getLong("CategoryID"));

            return category;
        }

    }

}
