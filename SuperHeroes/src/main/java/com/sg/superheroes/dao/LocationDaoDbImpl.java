/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Location;
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
public class LocationDaoDbImpl implements LocationDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_LOCATION
            = "insert into Location (name, description, address, latitude, longitude)"
            + "values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "delete from Location where locationID = ?";

    private static final String SQL_UPDATE_LOCATION
            = "update Location set name = ?, description = ?, address = ?, latitude = ?, longitude = ? where locationID = ?";

    private static final String SQL_SELECT_LOCATION
            = "select * from Location where locationID = ?";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select * from Location "
            + "order by name";

    private static final String SQL_SELECT_ALL_LOCATIONS_BY_METAHUMANID
            = "select Location.LocationID, Location.Name, Location.Description from Location "
            + "join Sighting on Location.LocationID = Sighting.LocationID "
            + "join MetaHuman_Sighting_Bridge on MetaHuman_Sighting_Bridge.SightingID = Sighting.SightingID "
            + "join MetaHuman on MetaHuman.MetaHumanID = MetaHuman_Sighting_Bridge.MetaHumanID "
            + "where MetaHuman.MetaHumanID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());

        String locationID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        location.setLocationID(locationID);
    }

    @Override
    public void deleteLocation(String locationID) {
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationID);
    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationID());
    }

    @Override
    public Location getLocationById(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION, new LocationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public List<Location> getAllLocationsByMetaHumanID(String id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS_BY_METAHUMANID, new LocationMapperSmall(), id);
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setName(rs.getString("name"));
            loc.setDescription(rs.getString("description"));
            loc.setAddress(rs.getString("address"));
            loc.setLatitude(rs.getString("latitude"));
            loc.setLongitude(rs.getString("longitude"));
            loc.setLocationID(rs.getString("locationID"));
            return loc;
        }
    }

    private static final class LocationMapperSmall implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setName(rs.getString("name"));
            loc.setDescription(rs.getString("description"));
            loc.setLocationID(rs.getString("locationID"));
            return loc;
        }
    }

}
