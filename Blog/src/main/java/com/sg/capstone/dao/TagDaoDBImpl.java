/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

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
 * @author admin
 */
public class TagDaoDBImpl implements TagDao {

    JdbcTemplate jdbcTemplate;

    @Inject
    public TagDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_TAG
            = "insert into Tag (TagName) "
            + "values (?)";

    private static final String SQL_DELETE_TAG
            = "delete from Tag where TagID = ?";

    private static final String SQL_DELETE_POSTTAGBRIDGE
            = "delete from PostTagBridge where TagID = ?";

    private static final String SQL_UPDATE_TAG
            = "update Tag set TagName = ?"
            + "where TagID = ?";

    private static final String SQL_SELECT_TAG
            = "select * from Tag where TagID = ?";

    private static final String SQL_SELECT_ALL_TAGS
            = "select * from Tag";
    
    private static final String SQL_SELECT_TAG_NAME_BY_BLOGPOST_ID
            = "select t.* from Tag t "
            + "inner join PostTagBridge bridge on t.TagID = bridge.TagID "
            + "inner join BlogPost bp on bridge.BlogPostID = bp.BlogPostID "
            + "where bp.BlogPostID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addTag(Tag tag) {
        jdbcTemplate.update(SQL_INSERT_TAG,
                tag.getTagName());

        long tagID
                = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", long.class);
        tag.setTagID(tagID);
    }

    @Override
    public void deleteTag(Long tagID) {
        jdbcTemplate.update(SQL_DELETE_POSTTAGBRIDGE, tagID);
        jdbcTemplate.update(SQL_DELETE_TAG, tagID);
    }

    @Override
    public void updateTag(Tag tag) {
        jdbcTemplate.update(SQL_UPDATE_TAG,
                tag.getTagName(),
                tag.getTagID());
    }

    @Override
    public Tag getTagByID(Long tagID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_TAG,
                    new TagMapper(), tagID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
            //BLANK ID RETURN NULL!
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query(SQL_SELECT_ALL_TAGS, new TagMapper());
    }

    @Override
    public Tag getTagByBlogPostID(Long blogPostID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_TAG_NAME_BY_BLOGPOST_ID,
                    new TagMapper(), blogPostID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setTagID(rs.getLong("TagID"));
            tag.setTagName(rs.getString("TagName"));
            return tag;
        }

    }

}
