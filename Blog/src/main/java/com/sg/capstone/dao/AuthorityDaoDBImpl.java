/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.Authority;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chandler
 */
public class AuthorityDaoDBImpl implements AuthorityDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public AuthorityDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_AUTHORITY
            = "insert into Authority (Authority, UserID) "
            + "value (?, ?)";

    private static final String SQL_SELECT_ALL_AUTHORITIES
            = "select Authority.Authority from Authority where UserID = ?";

    private static final String SQL_SELECT_ALL_AUTHORITIES_FOR_REAL
            = "select Authority.Authority from Authority";

    private static final String SQL_DELETE_AUTHORITY
            = "delete from Authority where AuthorityID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addAuthority(Authority authority) {
        jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                authority.getAuthorityName(),
                authority.getUser().getUserID());

        long authorityID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);

        authority.setAuthorityID(authorityID);

    }

    @Override
    public List<Authority> getAllAuthorities(Long userID) {
        return jdbcTemplate.query(SQL_SELECT_ALL_AUTHORITIES, new AuthorityMapper(), userID);
    }

    @Override
    public List<Authority> getAllAuthoritiesForReal() {
        return jdbcTemplate.query(SQL_SELECT_ALL_AUTHORITIES_FOR_REAL, new AuthorityMapper());
    }

    @Override
    public void deleteAuthority(Long authorityID) {
        jdbcTemplate.update(SQL_DELETE_AUTHORITY, authorityID);
    }

    private static final class AuthorityMapper implements RowMapper<Authority> {

        @Override
        public Authority mapRow(ResultSet rs, int i) throws SQLException {
//            User user = new User();
//            user.setUserID(rs.getLong("UserID"));
            Authority authority = new Authority();
//            authority.setAuthorityID(rs.getLong("AuthorityID"));
//            authority.setUser(user);
            authority.setAuthorityName(rs.getString("Authority"));

            return authority;
        }
    }

}
