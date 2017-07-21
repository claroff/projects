/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanPowerBridge;
import com.sg.superheroes.model.Power;
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
public class MetaHumanPowerBridgeDaoDbImpl implements MetaHumanPowerBridgeDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_METAHUMAN_POWER_BRIDGE
            = "insert into MetaHuman_Power_Bridge (MetaHumanID, PowerID) values(?,?)";

    private static final String SQL_DELETE_METAHUMAN_POWER_BRIDGE
            = "delete from MetaHuman_Power_Bridge where MetaHumanPowerBridgeID = ?";

    private static final String SQL_SELECT_METAHUMAN_POWER_BRIDGEID_BY_METAHUMANID
            = "select * from MetaHuman_Power_Bridge where MetaHumanID = ?";

    private static final String SQL_SELECT_METAHUMAN_POWER_BRIDGEID_BY_POWERID
            = "select * from MetaHuman_Power_Bridge where PowerID = ?";

    private static final String SQL_SELECT_METAHUMAN_POWER_BRIDGE_BY_METAHUMAN_AND_POWERID
            = "select * from MetaHuman_Power_Bridge "
            + "where MetaHuman_Power_Bridge.MetaHumanID = ? "
            + "and MetaHuman_Power_Bridge.PowerID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addMetaHumanPowerBridge(MetaHumanPowerBridge metaHumanPowerBridge) {
        jdbcTemplate.update(SQL_INSERT_METAHUMAN_POWER_BRIDGE,
                metaHumanPowerBridge.getMetaHuman().getMetaHumanID(),
                metaHumanPowerBridge.getPower().getPowerID());
        String metaHumanPowerBridgeID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        metaHumanPowerBridge.setMetaHumanPowerBridgeID(metaHumanPowerBridgeID);
    }

    @Override
    public void deleteMetaHumanPowerBridge(String metaHumanPowerBridgeID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_POWER_BRIDGE, metaHumanPowerBridgeID);
    }

    @Override
    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanId(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_POWER_BRIDGEID_BY_METAHUMANID, new MetaHumanPowerBridgeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public MetaHumanPowerBridge getMetaHumanPowerBridgeByPowerId(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_POWER_BRIDGEID_BY_POWERID, new MetaHumanPowerBridgeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanAndPowerId(String metaHumanID, String powerID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_POWER_BRIDGE_BY_METAHUMAN_AND_POWERID, new MetaHumanPowerBridgeMapper(), metaHumanID, powerID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final class MetaHumanPowerBridgeMapper implements RowMapper<MetaHumanPowerBridge> {

        @Override
        public MetaHumanPowerBridge mapRow(ResultSet rs, int i) throws SQLException {
            MetaHuman metaHuman = new MetaHuman();
            metaHuman.setMetaHumanID(rs.getString("metaHumanID"));

            Power power = new Power();
            power.setPowerID(rs.getString("powerID"));

            MetaHumanPowerBridge bridge = new MetaHumanPowerBridge();
            bridge.setMetaHuman(metaHuman);
            bridge.setPower(power);
            bridge.setMetaHumanPowerBridgeID(rs.getString("metaHumanPowerBridgeID"));
            return bridge;
        }
    }

}
