/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.StaticPage;
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
 * @author chandler
 */
public class StaticPageDaoDBImpl implements StaticPageDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public StaticPageDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_STATIC_PAGE
            = "insert into StaticPage (StaticPageName, StaticPageContent, NavIndex)"
            + "values (?, ?, ?)";

    private static final String SQL_SELECT_STATIC_PAGE
            = "select * from StaticPage where StaticPageID = ?";

    private static final String SQL_SELECT_ALL_STATIC_PAGES
            = "select * from StaticPage "
            + "order by NavIndex";

    private static final String SQL_DELETE_STATIC_PAGE
            = "delete from StaticPage where StaticPageID = ?";

    private static final String SQL_SET_STATIC_PAGE_TO_INACTIVE
            = "update StaticPage set IsActive = 0 "
            + "where StaticPageID = ?";

    private static final String SQL_UPDATE_STATIC_PAGE
            = "update StaticPage set StaticPageName = ?, "
            + "StaticPageContent = ?, NavIndex = ? where StaticPageID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addStaticPage(StaticPage staticPage) {
        jdbcTemplate.update(SQL_INSERT_STATIC_PAGE,
                staticPage.getStaticPageName(),
                staticPage.getStaticPageContent(),
                staticPage.getNavIndex());

        String staticPageID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        Long longID = Long.parseLong(staticPageID);

        staticPage.setStaticPageID(longID);
    }

    @Override
    public void updateStaticPage(StaticPage staticPage) {
        jdbcTemplate.update(SQL_UPDATE_STATIC_PAGE,
                staticPage.getStaticPageName(),
                staticPage.getStaticPageContent(),
                staticPage.getNavIndex(),
                staticPage.getStaticPageID());
    }

    @Override
    public void deleteStaticPage(Long staticPageId) {
        jdbcTemplate.update(SQL_DELETE_STATIC_PAGE, staticPageId);
    }

//    @Override
//    public void deactivateStaticPage(Long staticPageId) {
//        jdbcTemplate.update(SQL_SET_STATIC_PAGE_TO_INACTIVE, staticPageId);
//    }
    @Override
    public List<StaticPage> getAllStaticPages() {
        return jdbcTemplate.query(SQL_SELECT_ALL_STATIC_PAGES, new StaticPageMapper());
    }

    @Override
    public StaticPage getStaticPageByID(Long staticPageId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_STATIC_PAGE, new StaticPageMapper(), staticPageId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final class StaticPageMapper implements RowMapper<StaticPage> {

        @Override
        public StaticPage mapRow(ResultSet rs, int i) throws SQLException {
            StaticPage sp = new StaticPage();

            sp.setStaticPageName(rs.getString("StaticPageName"));
            sp.setStaticPageContent(rs.getString("StaticPageContent"));
            int nav = Integer.parseInt(rs.getString("NavIndex"));
            sp.setNavIndex(nav);
            Long id = Long.parseLong(rs.getString("StaticPageID"));
            sp.setStaticPageID(id);

            return sp;
        }
    }

}
