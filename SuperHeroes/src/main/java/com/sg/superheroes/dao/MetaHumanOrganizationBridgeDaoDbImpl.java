/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import com.sg.superheroes.model.Organization;
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
public class MetaHumanOrganizationBridgeDaoDbImpl implements MetaHumanOrganizationBridgeDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_METAHUMAN_ORGANIZATION_BRIDGE
            = "insert into MetaHuman_Organization_Bridge (MetaHumanID, OrganizationID) values(?,?)";

    private static final String SQL_DELETE_METAHUMAN_ORGANIZATION_BRIDGE ///////////////////////////
            = "delete from MetaHuman_Organization_Bridge where MetaHumanOrganizationBridgeID = ?";

    private static final String SQL_SELECT_METAHUMAN_ORGANIZATION_BRIDGEID_BY_METAHUMANID
            = "select * from MetaHuman_Organization_Bridge "
            + "where MetaHuman_Organization_Bridge.MetaHumanID = ?";

    private static final String SQL_SELECT_METAHUMAN_ORGANIZATION_BRIDGEID_BY_ORGANIZATIONID
            = "select * from MetaHuman_Organization_Bridge "
            + "where MetaHuman_Organization_Bridge.OrganizationID = ?";

    private static final String SQL_SELECT_METAHUMAN_ORGANIZATION_BRIDGE_BY_METAHUMAN_AND_ORGANIZATIONID
            = "select * from MetaHuman_Organization_Bridge "
            + "where MetaHuman_Organization_Bridge.MetaHumanID = ? "
            + "and MetaHuman_Organization_Bridge.OrganizationID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addMetaHumanOrganizationBridge(MetaHumanOrganizationBridge metaHumanOrganizationBridge) {
        jdbcTemplate.update(SQL_INSERT_METAHUMAN_ORGANIZATION_BRIDGE,
                metaHumanOrganizationBridge.getMetaHuman().getMetaHumanID(),
                metaHumanOrganizationBridge.getOrganization().getOrganizationID());

        String metaHumanOrganizationBridgeID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        metaHumanOrganizationBridge.setMetaHumanOrganizationBridgeID(metaHumanOrganizationBridgeID);
    }

    @Override
    public void deleteMetaHumanOrganizationBridge(String metaHumanOrganizationBridgeID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_ORGANIZATION_BRIDGE, metaHumanOrganizationBridgeID);
    }

    @Override
    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanId(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_ORGANIZATION_BRIDGEID_BY_METAHUMANID, new MetaHumanOrganizationBridgeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByOrganizationId(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_ORGANIZATION_BRIDGEID_BY_ORGANIZATIONID, new MetaHumanOrganizationBridgeMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(String MHid, String orgID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_METAHUMAN_ORGANIZATION_BRIDGE_BY_METAHUMAN_AND_ORGANIZATIONID, new MetaHumanOrganizationBridgeMapper(), MHid, orgID);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final class MetaHumanOrganizationBridgeMapper implements RowMapper<MetaHumanOrganizationBridge> {

        @Override
        public MetaHumanOrganizationBridge mapRow(ResultSet rs, int i) throws SQLException {
            MetaHuman metaHuman = new MetaHuman();
            metaHuman.setMetaHumanID(rs.getString("metaHumanID"));

            Organization organization = new Organization();
            organization.setOrganizationID(rs.getString("organizationID"));

            MetaHumanOrganizationBridge bridge = new MetaHumanOrganizationBridge();
            bridge.setMetaHuman(metaHuman);
            bridge.setOrganization(organization);
            bridge.setMetaHumanOrganizationBridgeID(rs.getString("metaHumanOrganizationBridgeID"));
            return bridge;
        }
    }

}
