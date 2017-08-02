/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.Comment;
import com.sg.capstone.model.User;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
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
public class CommentDaoDBImpl implements CommentDao {

    JdbcTemplate jdbcTemplate;

    @Inject
    public CommentDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_COMMENT
            = "insert into `Comment` (CommentContent, CommentDate, "
            + "UserID, BlogPostID) values (?, ?, ?, ?)";

    private static final String SQL_DELETE_COMMENT
            = "delete from `Comment` where CommentID = ?";

    private static final String SQL_UPDATE_COMMENT
            = "update `Comment` set CommentContent = ?, "
            + "CommentDate = ?, UserID = ?, BlogPostID = ? "
            + "where CommentID = ?";

    private static final String SQL_SELECT_COMMENT_BY_ID
            = "select * from `Comment` where CommentID = ?";

    private static final String SQL_SELECT_ALL_COMMENTS
            = "select * from `Comment`";

    private static final String SQL_SELECT_COMMENT_BY_BLOGPOST
            = "select c.* from `Comment` c "
            + "join BlogPost bp on c.BlogPostID = bp.BlogPostID "
            + "join `User` u on c.UserID = u.UserID "
            + "where bp.BlogPostID = ?";

    private static final String SQL_SELECT_COMMENT_BY_USERID
            = "select c.* from `Comment` c "
            + "join BlogPost bp on c.BlogPostID = bp.BlogPostID "
            + "join `User` u on c.UserID = u.UserID "
            + "where u.UserID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addComment(Comment comment) {
        Date commentDate = null;
        LocalDate commentLocalDate = comment.getCommentDate();

        if (commentLocalDate != null) {
            commentDate = Date.from(commentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        jdbcTemplate.update(SQL_INSERT_COMMENT,
                comment.getContent(),
                commentDate,
                comment.getUser().getUserID(),
                comment.getBlogPost().getBlogPostID());

        long commentID
                = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()",
                        long.class);
        comment.setCommentID(commentID);
    }

    @Override
    public void deleteComment(Long commentID) {
        jdbcTemplate.update(SQL_DELETE_COMMENT, commentID);
    }

    @Override
    public void updateComment(Comment comment) {
        Date commentDate = null;
        LocalDate commentLocalDate = comment.getCommentDate();

        if (commentLocalDate != null) {
            commentDate = Date.from(commentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        jdbcTemplate.update(SQL_UPDATE_COMMENT,
                comment.getContent(),
                commentDate,
                comment.getUser().getUserID(),
                comment.getBlogPost().getBlogPostID(),
                comment.getCommentID());
    }

    @Override
    public Comment getCommentById(Long CommentID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_COMMENT_BY_ID,
                    new CommentMapper(), CommentID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Comment> getAllComments() {
        return jdbcTemplate.query(SQL_SELECT_ALL_COMMENTS,
                new CommentMapper());
    }

    @Override
    public List<Comment> getAllCommentsByBlogPost(Long blogPostID) {
        return jdbcTemplate.query(SQL_SELECT_COMMENT_BY_BLOGPOST,
                new CommentMapper(), blogPostID);
    }

    @Override
    public List<Comment> getAllCommentsByUserID(Long userID) {
        return jdbcTemplate.query(SQL_SELECT_COMMENT_BY_USERID,
                new CommentMapper(), userID);
    }

    private static final class CommentMapper implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs, int i) throws SQLException {

            User user = new User();
            user.setUserName(rs.getString("UserID"));

            BlogPost blogPost = new BlogPost();
            blogPost.setBlogPostID(rs.getLong("BlogPostID"));

            Comment comment = new Comment();
            comment.setCommentID(rs.getLong("CommentID"));
            comment.setContent(rs.getString("CommentContent"));

            Timestamp commentDate = rs.getTimestamp("CommentDate");
            if (commentDate != null) {
                comment.setCommentDate(commentDate.toLocalDateTime().toLocalDate());
            }
            comment.setCommentDate(rs.getDate("CommentDate").toLocalDate());
            comment.setUser(user);
            comment.setBlogPost(blogPost);

            return comment;
        }

    }
}
