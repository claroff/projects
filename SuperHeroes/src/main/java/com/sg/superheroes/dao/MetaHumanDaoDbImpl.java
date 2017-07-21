/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
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
 * @author chandler
 */
public class MetaHumanDaoDbImpl implements MetaHumanDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_METAHUMAN
            = "insert into MetaHuman (name, identity)"
            + "values (?, ?)";

    private static final String SQL_DELETE_METAHUMAN
            = "delete from MetaHuman where MetaHumanID = ?";

    private static final String SQL_UPDATE_METAHUMAN
            = "update MetaHuman set name = ?, identity = ? where MetaHumanID = ?";

    private static final String SQL_SELECT_METAHUMAN
            = "select * from MetaHuman where MetaHumanID = ?";

    private static final String SQL_SELECT_METAHUMAN_BY_NAME
            = "select * from MetaHuman where name = ?";

    private static final String SQL_SELECT_ALL_METAHUMANS
            = "select * from MetaHuman "
            + "order by name";

    private static final String SQL_SELECT_ALL_METAHUMANS_BY_LOCATIONID
            = "select MetaHuman.MetaHumanID, MetaHuman.Name, MetaHuman.Identity from MetaHuman "
            + "join MetaHuman_Sighting_Bridge on MetaHuman_Sighting_Bridge.MetaHumanID = MetaHuman.MetaHumanID "
            + "join Sighting on MetaHuman_Sighting_Bridge.SightingID = Sighting.SightingID "
            + "join Location on Sighting.LocationID = Location.LocationID "
            + "where Location.LocationID = ?";

//    private static final String SQL_SELECT_ALL_METAHUMANS_BY_POWERID
//            = "select MetaHuman.MetaHumanID, MetaHuman.Name, MetaHuman.Identity"
//            + "join MetaHuman_Power_Bridge on PowerID where MetaHuman.MetaHumanID = Power.MetaHumanID "
//            + "and Power.PowerID = ?;";
//
    private static final String SQL_SELECT_ALL_METAHUMANS_BY_ORGANIZATIONID
            = "select MetaHuman.MetaHumanID, MetaHuman.Name, MetaHuman.Identity from MetaHuman "
            + "join MetaHuman_Organization_Bridge on MetaHuman_Organization_Bridge.MetaHumanID = MetaHuman.MetaHumanID "
            + "join Organization on MetaHuman_Organization_Bridge.OrganizationID = Organization.OrganizationID "
            + "where Organization.OrganizationID = ?";

    private static final String SQL_SELECT_ALL_METAHUMANS_BY_SIGHTINGID
            = "select MetaHuman.MetaHumanID, MetaHuman.Name, MetaHuman.Identity from MetaHuman "
            + "join MetaHuman_Sighting_Bridge on MetaHuman_Sighting_Bridge.MetaHumanID = MetaHuman.MetaHumanID "
            + "join Sighting on MetaHuman_Sighting_Bridge.SightingID = Sighting.SightingID "
            + "where Sighting.SightingID = ?";

    private static final String SQL_DELETE_METAHUMAN_POWER_BRIDGE
            = "delete from MetaHuman_Power_Bridge where MetaHumanID = ?";

    private static final String SQL_DELETE_METAHUMAN_SIGHTING_BRIDGE
            = "delete from MetaHuman_Sighting_Bridge where MetaHumanID = ?";

    private static final String SQL_DELETE_METAHUMAN_ORGANIZATION_BRIDGE
            = "delete from MetaHuman_Organization_Bridge where MetaHumanID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addMetaHuman(MetaHuman metaHuman) {
        jdbcTemplate.update(SQL_INSERT_METAHUMAN,
                metaHuman.getName(),
                metaHuman.getIdentity());

        String metaHumanID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        metaHuman.setMetaHumanID(metaHumanID);
    }

    @Override
    public void deleteMetaHuman(String metaHumanID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_ORGANIZATION_BRIDGE, metaHumanID);
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_SIGHTING_BRIDGE, metaHumanID);
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_POWER_BRIDGE, metaHumanID);
        jdbcTemplate.update(SQL_DELETE_METAHUMAN, metaHumanID);
    }

    //IF HAVING PROBLEMS
//    public void deleteMetaHumanOrganizationBridgeWhenDeletingMetaHuman(String metaHumanID) { //combine this and delete into one service method
//        jdbcTemplate.update(SQL_DELETE_METAHUMANORGANIZATIONBRIDGE_WHEN_DELETING_METAHUMAN, metaHumanID);
//    }
    @Override
    public void updateMetaHuman(MetaHuman metaHuman) {
        jdbcTemplate.update(SQL_UPDATE_METAHUMAN,
                metaHuman.getName(),
                metaHuman.getIdentity(),
                metaHuman.getMetaHumanID());
    }

    @Override
    public MetaHuman getMetaHumanById(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN, new MetaHumanMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<MetaHuman> getAllMetaHumans() {
        return jdbcTemplate.query(SQL_SELECT_ALL_METAHUMANS, new MetaHumanMapper());
    }

    @Override
    public List<MetaHuman> getAllMetaHumansByLocationID(String id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_METAHUMANS_BY_LOCATIONID, new MetaHumanMapper(), id);
    }

    @Override
    public List<MetaHuman> getAllMetaHumansByOrganizationID(String id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_METAHUMANS_BY_ORGANIZATIONID, new MetaHumanMapper(), id);
    }

    @Override
    public List<MetaHuman> getAllMetaHumansBySightingID(String id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_METAHUMANS_BY_SIGHTINGID, new MetaHumanMapper(), id);
    }

    private static final class MetaHumanMapper implements RowMapper<MetaHuman> {

        @Override
        public MetaHuman mapRow(ResultSet rs, int i) throws SQLException {
            MetaHuman mh = new MetaHuman();
            mh.setName(rs.getString("Name"));
            mh.setIdentity(rs.getString("Identity"));
            mh.setMetaHumanID(rs.getString("MetaHumanID"));
            return mh;
        }
    }

}
