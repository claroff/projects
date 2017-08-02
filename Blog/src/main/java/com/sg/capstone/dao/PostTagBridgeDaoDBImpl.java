/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.BlogPost;
import com.sg.capstone.model.PostTagBridge;
import com.sg.capstone.model.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class PostTagBridgeDaoDBImpl implements PostTagBridgeDao {

    private JdbcTemplate jdbcTemplate;

    public PostTagBridgeDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_POSTTAGBRIDGE
            = "insert into PostTagBridge (TagID, BlogPostID) "
            + "values (?, ?)";

    private static final String SQL_DELETE_POSTTAGBRIDGE
            = "delete from PostTagBridge where PostTagBridgeID = ?";

    private static final String SQL_UPDATE_POSTTAGBRIDGE
            = "update PostTagBridge set TagID = ?, BlogPostID = ? "
            + "where PostTagBridgeID = ?";

    private static final String SQL_SELECT_POSTTAGBRIDGE
            = "select * from PostTagBridge where PostTagBridgeID = ?";

    private static final String SQL_SELECT_ALL_POSTTAGBRIDGES
            = "select * from PostTagBridge";

    private static final String SQL_SELECT_ALL_BRIDGES_BY_POSTID
            = "select * from PostTagBridge "
            + "where BlogPostID = ? ";

    private static final String SQL_SELECT_ALL_BRIDGES_BY_TAGID
            = "select * from PostTagBridge "
            + "where TagID = ? ";

    private static final String SQL_SELECT_BRIDGE_BY_TAG_AND_POSTIDS
            = "select * from PostTagBridge "
            + "where TagID = ? and BlogPostID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)

    public void addPostTagBridge(PostTagBridge postTagBridge) {
        jdbcTemplate.update(SQL_INSERT_POSTTAGBRIDGE,
                postTagBridge.getTag().getTagID(),
                postTagBridge.getBlogPost().getBlogPostID());

        long postTagBridgeID
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        postTagBridge.setPostTagID(postTagBridgeID);
    }

    @Override
    public void deletePostTagBridge(Long postTagBridgeID) {
        jdbcTemplate.update(SQL_DELETE_POSTTAGBRIDGE, postTagBridgeID);
    }

    @Override
    public void editPostTagBridge(PostTagBridge postTagBridge) {
        jdbcTemplate.update(SQL_UPDATE_POSTTAGBRIDGE,
                postTagBridge.getTag().getTagID(),
                postTagBridge.getBlogPost().getBlogPostID(),
                postTagBridge.getPostTagID());
    }

    @Override
    public PostTagBridge getPostTagBridgeByID(Long postTagBridgeID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_POSTTAGBRIDGE, new PostTagBridgeMapper(), postTagBridgeID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public PostTagBridge getPostTagBridgeByTagAndPostIDs(Long tagID, Long postID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BRIDGE_BY_TAG_AND_POSTIDS, new PostTagBridgeMapper(), tagID, postID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<PostTagBridge> getAllPostTagBridgesByPostID(Long postID) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BRIDGES_BY_POSTID, new PostTagBridgeMapper(), postID);
    }

    @Override
    public List<PostTagBridge> getAllPostTagBridgesByTagID(Long tagID) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BRIDGES_BY_TAGID, new PostTagBridgeMapper(), tagID);
    }

    @Override
    public List<PostTagBridge> getAllPostTagBridges() {
        return jdbcTemplate.query(SQL_SELECT_ALL_POSTTAGBRIDGES, new PostTagBridgeMapper());
    }

    private static final class PostTagBridgeMapper implements RowMapper<PostTagBridge> {

        @Override
        public PostTagBridge mapRow(ResultSet resultSet, int i) throws SQLException {
            BlogPost blogPost = new BlogPost();
            blogPost.setBlogPostID(resultSet.getLong("BlogPostID"));

            Tag tag = new Tag();
            tag.setTagID(resultSet.getLong("TagID"));

            PostTagBridge postTagBridge = new PostTagBridge();
            postTagBridge.setBlogPost(blogPost);
            postTagBridge.setTag(tag);

            postTagBridge.setPostTagID(resultSet.getLong("PostTagBridgeID"));
            return postTagBridge;
        }
    }

}
