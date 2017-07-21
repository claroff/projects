/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
public class SightingDaoDbImpl implements SightingDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (name, date, locationID)"
            + "values (?, ?, ?)";

    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingID = ?";

    private static final String SQL_UPDATE_SIGHTING
            = "update Sighting set name = ?, date = ?, locationID = ? where SightingID = ?";

    private static final String SQL_SELECT_SIGHTING
            = "select * from Sighting where SightingID = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS_BY_DATE
            = "select Sighting.LocationID, MetaHuman.MetaHumanID from Sighting " //MetaHumanName for readability?
            + "left join MetaHuman_Sighting_Bridge on Sighting.SightingID = MetaHuman_Sighting_Bridge.SightingID "
            + "left join MetaHuman on MetaHuman_Sighting_Bridge.MetaHumanID = MetaHuman.MetaHumanID "
            + "where Sighting.Date = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "select * from Sighting";

    private static final String SQL_SELECT_RECENT_SIGHTINGS
            = "select * from Sighting "
            + "order by Sighting.Date "
            + "limit 10";

//    private static final String SQL_SELECT_SIGHTING_BY_METAHUMANID //CHECK THIS, MIGHT BE WRONG
//            = "select Sighting.SightingID, Sighting.Name, Sighting.Date, Sighting.LocationID"
//            + "join MetaHuman_Sighting_Bridge on MetaHumanID where MetaHuman.MetaHumanID = Sighting.MetaHumanID "
//            + "and MetaHuman.MetaHumanID = ?;";
    private static final String SQL_DELETE_METAHUMAN_SIGHTING
            = "delete from MetaHuman_Sighting_Bridge where SightingID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting) {
        LocalDate localDate = sighting.getDate();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getName(),
                date,
                sighting.getLocation().getLocationID());

        String sightingID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        sighting.setSightingID(sightingID);
    }

    @Override
    public void deleteSighting(String sightingID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_SIGHTING, sightingID);
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingID);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sighting.getName(),
                sighting.getDate().toString(),
                sighting.getLocation().getLocationID(),
                sighting.getSightingID());
    }

    @Override
    public Sighting getSightingById(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING, new SightingMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightingsByDate(Date date) {
        return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_BY_DATE, new SightingMapperSmall(), date);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public List<Sighting> getRecentSightings() {
        return jdbcTemplate.query(SQL_SELECT_RECENT_SIGHTINGS, new SightingMapper());
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getString("locationID"));

            Sighting sighting = new Sighting();
            sighting.setName(rs.getString("name"));
            sighting.setDate(rs.getTimestamp("date").toLocalDateTime().toLocalDate());
            sighting.setLocation(location);
            sighting.setSightingID(rs.getString("sightingID"));
            return sighting;
        }
    }

    private static final class SightingMapperSmall implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getString("locationID"));

            Sighting sighting = new Sighting();

            sighting.setLocation(location);

            return sighting;
        }
    }
//
//    private static final class LocationMapper implements RowMapper<Location> {
//
//        @Override
//        public Location mapRow(ResultSet rs, int i) throws SQLException {
//            Location loc = new Location();
//            loc.setName(rs.getString("name"));
//            loc.setDescription(rs.getString("description"));
//            loc.setAddress(rs.getString("address"));
//            loc.setLatitude(rs.getString("latitude"));
//            loc.setLongitude(rs.getString("longitude"));
//            loc.setLocationID(rs.getString("locationID"));
//            return loc;
//        }
//    }

}
