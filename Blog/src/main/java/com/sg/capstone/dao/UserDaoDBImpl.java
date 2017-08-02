/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.Authority;
import com.sg.capstone.model.Image;
import com.sg.capstone.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class UserDaoDBImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public UserDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_USER
            = "insert into `User` (UserName, UserPassword, ImageID, IsActive) "
            + "values (?, ?, ?, ?)";

    private static final String SQL_INSERT_AUTHORITY
            = "insert into Authority (UserID, Authority) values (?, ?)";

    private static final String SQL_UPDATE_AUTHORITY
            = "update Authority set UserID = ?, Authority = ? where UserID = ?";

    private static final String SQL_DELETE_USER
            = "delete from `User` where UserID = ?";

    private static final String SQL_DELETE_AUTHORITIES
            = "delete from Authority where UserID = ?";

    private static final String SQL_DELETE_AUTHORITY_BY_USERID_AND_AUTHORITY
            = "delete from Authority where UserID = ? and Authority = ?";

    private static final String SQL_SET_USER_INACTIVE
            = "update `User` set isActive = 0 where UserID = ?";

    private static final String SQL_UPDATE_USER
            = "update `User` set UserName = ?,  "
            + "UserPassword = ?, IsActive = ? where UserID = ?";

    private static final String SQL_SELECT_USER
            = "select * from `User` where UserID = ?";

    private static final String SQL_SELECT_ALL_USERS
            = "select * from `User`";

    private static final String SQL_GET_USER_BY_BLOG_POST
            = "select * from `User` u "
            + "join BlogPost p on p.UserID = u.UserID "
            + "where p.BlogPostID = ?";

    private static final String SQL_GET_USER_BY_TAG
            = "select * from `User` u "
            + "join BlogPost p on p.UserID = u.UserID "
            + "join PostTagBridge b on b.BlogPostID = p.BlogPostID "
            + "join Tag t on t.TagID = b.TagID "
            + "where t.TagID = ?";

    private static final String SQL_GET_USER_BY_COMMENT
            = "select * from `User` u "
            + "join Comment c on c.UserID = u.UserID "
            + "where c.CommentID = ?";

    private static final String SQL_GET_USER_BY_NAME
            = "select * from `User` "
            + "where UserName = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addUser(User user) {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUserName(),
                user.getUserPassword(),
                user.getImage().getImageID(),
                user.isIsActive());
        long userID
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        long.class);

        user.setUserID(userID);

        List<String> authorities = user.getAuthority();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    user.getUserID(),
                    authority);
        }

    }

    @Override
    public void deleteUser(Long userID) {
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, userID);
        jdbcTemplate.update(SQL_DELETE_USER, userID);
    }

    @Override
    public void setUserInactive(Long userID) {
        jdbcTemplate.update(SQL_SET_USER_INACTIVE, userID);
    }

    @Override
    public void editUserAuthorities(User user, List<Authority> authorityList) {
        List<String> authorityFromDBList = new ArrayList();

        for (Authority auth : authorityList) {
            authorityFromDBList.add(auth.getAuthorityName());
        }

        List<String> authorities = user.getAuthority();

        boolean adminCheck = false;
        boolean superUserCheck = false;

        for (String check : authorityFromDBList) {
            if (check.equals("ROLE_ADMIN")) {
                adminCheck = true;
            }

            if (check.equals("ROLE_SUPERUSER")) {
                superUserCheck = true;
            }
        }
        for (String authority : authorities) {

            if (authority.equals("ROLE_ADMIN") && adminCheck == false) {
                jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                        user.getUserID(),
                        authority);
            }
            if (authority.equals("ROLE_SUPERUSER") && superUserCheck == false) {
                jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                        user.getUserID(),
                        authority);
            }
        }

        if (!user.getAuthority()
                .contains("ROLE_ADMIN")) {
            jdbcTemplate.update(SQL_DELETE_AUTHORITY_BY_USERID_AND_AUTHORITY,
                    user.getUserID(),
                    "ROLE_ADMIN");
        }

        if (!user.getAuthority()
                .contains("ROLE_SUPERUSER")) {
            jdbcTemplate.update(SQL_DELETE_AUTHORITY_BY_USERID_AND_AUTHORITY,
                    user.getUserID(),
                    "ROLE_SUPERUSER");
        }

    }

    @Override
    public void editUser(User user) {
        jdbcTemplate.update(SQL_UPDATE_USER,
                user.getUserName(),
                user.getUserPassword(),
                //  user.getImage().getImageID(),
                user.isIsActive(),
                user.getUserID());

    }

    @Override
    public User getUserByID(Long userID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER, new UserMapper(), userID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, new UserMapper());
    }

    @Override
    public User getUserByBlogPostID(Long blogPostID) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_USER_BY_BLOG_POST, new UserMapper(), blogPostID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User getUserByTagID(Long tagID) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_USER_BY_TAG, new UserMapper(), tagID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User getUserByCommentID(Long commentID) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_USER_BY_COMMENT, new UserMapper(), commentID);
        } catch (EmptyResultDataAccessException ex) {
            return null;

        }
    }

    @Override
    public User getUserByName(String userName) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_USER_BY_NAME, new UserMapper(), userName);
        } catch (EmptyResultDataAccessException ex) {
            return null;

        }
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            Image image = new Image();
            image.setImageID(rs.getLong("ImageID"));

            user.setUserName(rs.getString("UserName"));
            user.setUserPassword(rs.getString("UserPassword"));
            user.setImage(image);
            user.setIsActive(rs.getBoolean("IsActive"));
            user.setUserID(rs.getLong("UserID"));

            return user;
        }

    }
}
