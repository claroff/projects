/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
public class BlogPostDaoDBImpl implements BlogPostDao {

    private static final String SQL_INSERT_BLOGPOST
            = "insert into BlogPost (BlogTitle, StartDate, "
            + "EndDate, BlogContent, IsActive, "
            + "UserID) values (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_BLOGPOST
            = "delete from BlogPost where BlogPostID = ?";

    private static final String SQL_DELETE_BLOG_POST_CATEGORY_BRIDGE
            = "delete from BlogPostCategoryBridge where BlogPostID = ?";

    private static final String SQL_DELETE_POSTTAGBRIDGE
            = "delete from PostTagBridge where BlogPostID = ?";

    private static final String SQL_UPDATE_BLOGPOST
            = "update BlogPost set BlogTitle = ?, "
            + "StartDate = ?, EndDate = ?, BlogContent = ?, "
            + "IsActive = ?, "
            //+ "ImageID = ?, "
            + "UserID = ? "
            + "where BlogPostID = ?";

    private static final String SQL_SELECT_BLOGPOST
            = "select * from BlogPost where BlogPostID = ?";

    private static final String SQL_SELECT_ALL_BLOGPOSTS
            = "select * from BlogPost";

    private static final String SQL_SELECT_ALL_ACTIVE_BLOGPOSTS
            = "select * from BlogPost where IsActive = 1";

    private static final String SQL_SELECT_ALL_ACTIVE_UNEXPIRED_BLOGPOSTS
            = "select * from BlogPost "
            + "where (current_date() between StartDate and EndDate or EndDate is null) "
            + "and IsActive = 1 "
            + "order by BlogPost.StartDate desc";

    private static final String SQL_SELECT_BLOGPOST_BY_CATEGORYID
            = "select * from BlogPost bp "
            + "join BlogPostCategoryBridge pc on bp.BlogPostID = pc.BlogPostID "
            + "join Category c on pc.CategoryID = c.CategoryID "
            + "where c.CategoryID = ?";

    private static final String SQL_SELECT_BLOGPOST_BY_USERID
            = "select * from BlogPost bp "
            + "join `User` u on bp.UserID = u.UserID "
            + "where u.UserID = ?";

    private static final String SQL_SELECT_BLOGPOST_BY_TAGID
            = "select * from BlogPost bp "
            + "join PostTagBridge pt on bp.BlogPostID = pt.BlogPostID "
            + "join Tag t on pt.TagID = t.TagID "
            + "where t.TagID = ?";

    private static final String SQL_SELECT_BLOGPOST_BY_BRIDGEID
            = "select * from BlogPost "
            + "join BlogPostCategoryBridge on BlogPostCategoryBridge.BlogPostID = BlogPost.BlogPostID "
            + "where BlogPostCategoryBridgeID = ?";

    private static final String SQL_SELECT_BLOGPOST_BY_DATE
            = "select * from BlogPost bp "
            + "where bp.StartDate = ? "
            + "order by bp.StartDate";

    private static final String SQL_GET_ALL_BLOG_POST_BY_ACTIVE
            = "select * from BlogPost bp "
            + "where bp.IsActive = ?";

    private static final String SQL_SET_BLOG_POST_TO_ACTIVE
            = "update BlogPost set IsActive = 1 "
            + "where BlogPostID = ?";

    private static final String SQL_SET_BLOG_POST_TO_INACTIVE
            = "update BlogPost set IsActive = 0 "
            + "where BlogPostID = ?";

    private JdbcTemplate jdbcTemplate;

    @Inject
    public BlogPostDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addBlogPost(BlogPost blogPost) {
        Date start = null;
        Date end = null;
        LocalDate startDate = blogPost.getStartDate();
        LocalDate endDate = blogPost.getEndDate();

        if (startDate != null) {
            start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (endDate != null) {
            end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        jdbcTemplate.update(SQL_INSERT_BLOGPOST,
                blogPost.getTitle(),
                start,
                end,
                blogPost.getBlogContent(),
                blogPost.isIsActive(),
                //                blogPost.getBlogImage().getImageID(),
                blogPost.getUser().getUserID());

        long blogPostID
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        long.class);
        blogPost.setBlogPostID(blogPostID);
    }

    @Override
    public void deleteBlogPost(Long blogPostID) {
        jdbcTemplate.update(SQL_DELETE_POSTTAGBRIDGE, blogPostID);
        jdbcTemplate.update(SQL_DELETE_BLOG_POST_CATEGORY_BRIDGE, blogPostID);
        jdbcTemplate.update(SQL_DELETE_BLOGPOST, blogPostID);
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {
        Date start = null;
        Date end = null;
        LocalDate startDate = blogPost.getStartDate();
        LocalDate endDate = blogPost.getEndDate();

        if (startDate != null) {
            start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (endDate != null) {
            end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        jdbcTemplate.update(SQL_UPDATE_BLOGPOST,
                blogPost.getTitle(),
                start,
                end,
                blogPost.getBlogContent(),
                blogPost.isIsActive(),
                //                blogPost.getBlogImage().getImageID(),
                blogPost.getUser().getUserID(),
                blogPost.getBlogPostID());
    }

    @Override
    public BlogPost getBlogPostById(Long blogPostID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BLOGPOST,
                    new BlogPostMapper(),
                    blogPostID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
            //BLANK ID, RETURN NULL
        }
    }

    @Override
    public BlogPost getBlogPostByBlogPostCategoryBridgeID(Long blogPostCategoryBridgeID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BLOGPOST_BY_BRIDGEID,
                    new BlogPostMapper(),
                    blogPostCategoryBridgeID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
            //BLANK ID, RETURN NULL
        }
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return jdbcTemplate.query(SQL_SELECT_ALL_BLOGPOSTS,
                new BlogPostMapper());
    }

    @Override
    public List<BlogPost> getAllActiveBlogPosts() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ACTIVE_BLOGPOSTS,
                new BlogPostMapper());
    }

    @Override
    public List<BlogPost> getAllActiveUnexpiredBlogPosts() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ACTIVE_UNEXPIRED_BLOGPOSTS,
                new BlogPostMapper());
    }

    @Override
    public List<BlogPost> getAllBlogPostsByCategoryID(Long categoryID) {
        return jdbcTemplate.query(SQL_SELECT_BLOGPOST_BY_CATEGORYID,
                new BlogPostMapper(), categoryID);
    }

    @Override
    public List<BlogPost> getAllBlogPostsByUserID(Long userID) {
        return jdbcTemplate.query(SQL_SELECT_BLOGPOST_BY_USERID,
                new BlogPostMapper(), userID);
    }

    @Override
    public List<BlogPost> getAllBlogPostsByTagID(Long tagID) {
        return jdbcTemplate.query(SQL_SELECT_BLOGPOST_BY_TAGID,
                new BlogPostMapper(), tagID);
    }

    @Override
    public List<BlogPost> getAllBlogPostsByDate(LocalDate date) {
        return jdbcTemplate.query(SQL_SELECT_BLOGPOST_BY_DATE,
                new BlogPostMapper(), date.toString());
    }

    @Override
    public List<BlogPost> getAllBlogPostByActive(boolean isActive) {
        return jdbcTemplate.query(SQL_GET_ALL_BLOG_POST_BY_ACTIVE,
                new BlogPostMapper(), isActive);
    }

    @Override
    public void setIsActiveToInActive(long blogPostID) {
        jdbcTemplate.update(SQL_SET_BLOG_POST_TO_INACTIVE, blogPostID);
    }

    @Override
    public void setIsActiveToActive(long blogPostID) {
        jdbcTemplate.update(SQL_SET_BLOG_POST_TO_ACTIVE, blogPostID);
    }

    private static final class BlogPostMapper implements RowMapper<BlogPost> {

        //COMMENT SOME STUFF OUT
        @Override
        public BlogPost mapRow(ResultSet rs, int i) throws SQLException {
            BlogPost blogPost = new BlogPost();

            Image image = new Image();
            image.setImageID(rs.getLong("ImageID"));

            User user = new User();
            user.setUserID(rs.getLong("UserID"));
            blogPost.setBlogPostID(rs.getLong("BlogPostID"));
            blogPost.setTitle(rs.getString("BlogTitle"));

            Timestamp startDate = rs.getTimestamp("StartDate");
            if (startDate != null) {
                blogPost.setStartDate(startDate.toLocalDateTime().toLocalDate());
            }

            Timestamp endDate = rs.getTimestamp("EndDate");
            if (endDate != null) {
                blogPost.setEndDate(endDate.toLocalDateTime().toLocalDate());
            }

            blogPost.setBlogContent(rs.getString("BlogContent"));
            blogPost.setIsActive(rs.getBoolean("IsActive"));
            blogPost.setBlogImage(image);
            blogPost.setUser(user);

            return blogPost;
        }
    }
}
