/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.Organization;
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
public class OrganizationDaoDbImpl implements OrganizationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (name, description, locationID)"
            + "values (?, ?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationID = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set name = ?, description = ?, locationID = ? where OrganizationID = ?";

    private static final String SQL_SELECT_ORGANIZATION
            = "select * from Organization where OrganizationID = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from Organization "
            + "order by name";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS_BY_METAHUMANID
            = "select Organization.OrganizationID, Organization.Name, Organization.Description from Organization "
            + "join MetaHuman_Organization_Bridge on MetaHuman_Organization_Bridge.OrganizationID = Organization.OrganizationID "
            + "join MetaHuman on MetaHuman_Organization_Bridge.MetaHumanID = MetaHuman.MetaHumanID "
            + "where MetaHuman.MetaHumanID = ?";

    private static final String SQL_DELETE_METAHUMAN_ORGANIZATION_BRIDGE
            = "delete from MetaHuman_Organization_Bridge where OrganizationID = ?";

//    private static final String SQL_SELECT_LOCATION_BY_ORGANIZATION_ID //CHECK
//            = "select Location.LocationID, Location.Name, Location.Description, Location.Address, "
//            + "Location.Latitude, Location.Longitude from Location "
//            + "join Organization on Location.LocationID = Organization.LocationID where "
//            + "Organization.OrganizationID = ?";
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getLocation().getLocationID());

        String organizationID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        organization.setOrganizationID(organizationID);
    }

    @Override
    public void deleteOrganization(String organizationID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_ORGANIZATION_BRIDGE, organizationID);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationID);
    }

    @Override
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getLocation().getLocationID(),
                organization.getOrganizationID());
    }

    @Override
    public Organization getOrganizationById(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION, new OrganizationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public List<Organization> getAllOrganizationsByMetaHumanID(String id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS_BY_METAHUMANID, new OrganizationMapperSmall(), id);
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getString("locationID"));

            Organization org = new Organization();
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setLocation(location);
            org.setOrganizationID(rs.getString("organizationID"));
            return org;
        }
    }

    private static final class OrganizationMapperSmall implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
//            Location location = new Location();
//            location.setLocationID(rs.getString("locationID"));

            Organization org = new Organization();
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            //org.setLocation(location);
            org.setOrganizationID(rs.getString("organizationID"));
            return org;
        }
    }
}
