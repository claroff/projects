/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.BlogPostCategoryBridge;
import com.sg.capstone.model.Category;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.User;
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
 * @author admin
 */
public class BlogPostCategoryBridgeDaoDBImpl implements BlogPostCategoryBridgeDao {

    JdbcTemplate jdbcTemplate;

    @Inject
    public BlogPostCategoryBridgeDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_BLOG_POST_CATEGORY_BRIDGE
            = "insert into BlogPostCategoryBridge (BlogPostID, CategoryID) "
            + "value (?, ?)";

    private static final String SQL_DELETE_BLOG_POST_CATEGORY_BRIDGE
            = "delete from BlogPostCategoryBridge where BlogPostCategoryBridgeID = ?";

    private static final String SQL_UPDATE_BLOG_POST_CATEGORY_BRIDGE
            = "update BlogPostCategoryBridge set BlogPostID = ?, CategoryID = ? "
            + "where BlogPostCategoryBridgeID = ?";

    private static final String SQL_SELECT_BLOG_POST_CATEGORY_BRIDGEID
            = "select * from BlogPostCategoryBridge where BlogPostCategoryBridgeID = ?";

    private static final String SQL_SELECT_ALL_BLOG_POST_CATEGORY_BRIDGES
            = "select * from BlogPostCategoryBridge";

    private static final String SQL_GET_BY_BLOG_POST_AND_CATEGORYIDS
            = "select * from BlogPostCategoryBridge bpc "
            + "join BlogPost bp on bpc.BlogPostID = bp.BlogPostID "
            + "join Category c on bpc.CategoryID = c.CategoryID "
            + "where bp.BlogPostID = ? and c.CategoryID = ?";

    private static final String SQL_SELECT_ALL_ACTIVE_BLOG_POSTS_BY_CATEGORY_ID
            = "select * from BlogPost bp "
            + "inner join BlogPostCategoryBridge bridge on bp.BlogPostID = bridge.BlogPostID "
            + "inner join Category c on bridge.CategoryID = c.CategoryID "
            + "where c.CategoryID = ? and bp.IsActive = 1";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addBlogPostCategoryBridge(BlogPostCategoryBridge blogPostCategoryBridge) {
        jdbcTemplate.update(SQL_INSERT_BLOG_POST_CATEGORY_BRIDGE,
                blogPostCategoryBridge.getBlogPost().getBlogPostID(),
                blogPostCategoryBridge.getCategory().getCategoryID());

        long blogPostCategoryBridgeID
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        long.class);
        blogPostCategoryBridge.setBlogPostCategoryID(blogPostCategoryBridgeID);
    }

    @Override
    public void deleteBlogPostCategoryBridge(Long blogPostCategoryID) {
        jdbcTemplate.update(SQL_DELETE_BLOG_POST_CATEGORY_BRIDGE, blogPostCategoryID);
    }

    @Override
    public void updateBlogPostCategoryBridge(BlogPostCategoryBridge blogPostCategoryBridge) {
        jdbcTemplate.update(SQL_UPDATE_BLOG_POST_CATEGORY_BRIDGE,
                blogPostCategoryBridge.getBlogPost().getBlogPostID(),
                blogPostCategoryBridge.getCategory().getCategoryID(),
                blogPostCategoryBridge.getBlogPostCategoryID());
    }

    @Override
    public BlogPostCategoryBridge getBlogPostCategoryBridgeById(Long blogPostCategoryID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BLOG_POST_CATEGORY_BRIDGEID,
                    new BlogPostCategoryBridgeMapper(), blogPostCategoryID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<BlogPostCategoryBridge> getAllBlogPostCategoryBridges() {
        return jdbcTemplate.query(SQL_SELECT_ALL_BLOG_POST_CATEGORY_BRIDGES,
                new BlogPostCategoryBridgeMapper());
    }

    @Override
    public List<BlogPostCategoryBridge> getAllBlogPostCategoryBridgesByBlogPostAndCategoryID(Long blogPostID, Long categoryID) {
        return jdbcTemplate.query(SQL_GET_BY_BLOG_POST_AND_CATEGORYIDS,
                new BlogPostCategoryBridgeMapper(), blogPostID, categoryID);
    }

    @Override
    public List<BlogPostCategoryBridge> getAllActiveBlogPostsByCategoryID(long categoryID) {
        return jdbcTemplate.query(SQL_SELECT_ALL_ACTIVE_BLOG_POSTS_BY_CATEGORY_ID,
                new BlogPostCategoryBridgeMapper(), categoryID);
    }

    private static final class BlogPostCategoryBridgeMapper implements RowMapper<BlogPostCategoryBridge> {

        @Override
        public BlogPostCategoryBridge mapRow(ResultSet rs, int i) throws SQLException {

            BlogPost blogPost = new BlogPost();
            blogPost.setBlogPostID(rs.getLong("BlogPostID"));

            Category category = new Category();
            category.setCategoryID(rs.getLong("CategoryID"));

            BlogPostCategoryBridge bpcb = new BlogPostCategoryBridge();
            bpcb.setBlogPostCategoryID(rs.getLong("BlogPostCategoryBridgeID"));
            bpcb.setBlogPost(blogPost);
            bpcb.setCategory(category);

            return bpcb;

        }

    }

}
