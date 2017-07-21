/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanSightingBridge;
import com.sg.superheroes.model.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chandler
 */
public class MetaHumanSightingBridgeDaoDbImpl implements MetaHumanSightingBridgeDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_METAHUMAN_SIGHTING_BRIDGE
            = "insert into MetaHuman_Sighting_Bridge (MetaHumanID, SightingID) values(?,?)";

    private static final String SQL_DELETE_METAHUMAN_SIGHTING_BRIDGE
            = "delete from MetaHuman_Sighting_Bridge where MetaHuman_Sighting_BridgeID = ?";

    private static final String SQL_SELECT_METAHUMAN_SIGHTING_SIGHTINGID_BY_METAHUMANID
            = "select * from MetaHuman_Sighting_Bridge where MetaHumanID = ?";

    private static final String SQL_SELECT_METAHUMAN_SIGHTING_METAHUMANID_BY_SIGHTINGID
            = "select * from MetaHuman_Sighting_Bridge where SightingID = ?";

    private static final String SQL_SELECT_METAHUMAN_SIGHTING_BRIDGE_BY_METAHUMAN_AND_SIGHTINGID
            = "select * from MetaHuman_Sighting_Bridge "
            + "where MetaHuman_Sighting_Bridge.MetaHumanID = ? "
            + "and MetaHuman_Sighting_Bridge.SightingID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addMetaHumanSightingBridge(MetaHumanSightingBridge metaHumanSightingBridge) {
        jdbcTemplate.update(SQL_INSERT_METAHUMAN_SIGHTING_BRIDGE,
                metaHumanSightingBridge.getMetaHuman().getMetaHumanID(),
                metaHumanSightingBridge.getSighting().getSightingID());

        String metaHumanSightingBridgeID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        metaHumanSightingBridge.setMetaHumanSightingBridgeID(metaHumanSightingBridgeID);
    }

    @Override
    public void deleteMetaHumanSightingBridge(String metaHumanSightingBridgeID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_SIGHTING_BRIDGE, metaHumanSightingBridgeID);
    }

    @Override
    public MetaHumanSightingBridge getMetaHumanSightingBridgeByMetaHumanId(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_SIGHTING_SIGHTINGID_BY_METAHUMANID, new MetaHumanSightingBridgeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public MetaHumanSightingBridge getMetaHumanSightingBridgeBySightingId(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_SIGHTING_METAHUMANID_BY_SIGHTINGID, new MetaHumanSightingBridgeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public MetaHumanSightingBridge getMetaHumanSightingBridgeByMetaHumanAndSightingId(String metaHumanID, String sightingID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_SIGHTING_BRIDGE_BY_METAHUMAN_AND_SIGHTINGID, new MetaHumanSightingBridgeMapper(), metaHumanID, sightingID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    private static final class MetaHumanSightingBridgeMapper implements RowMapper<MetaHumanSightingBridge> {

        @Override
        public MetaHumanSightingBridge mapRow(ResultSet rs, int i) throws SQLException {
            MetaHuman metaHuman = new MetaHuman();
            metaHuman.setMetaHumanID(rs.getString("metaHumanID"));

            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getString("sightingID"));

            MetaHumanSightingBridge bridge = new MetaHumanSightingBridge();
            bridge.setMetaHuman(metaHuman);
            bridge.setSighting(sighting);
            bridge.setMetaHumanSightingBridgeID(rs.getString("MetaHuman_Sighting_BridgeID"));
            return bridge;
        }
    }

}
