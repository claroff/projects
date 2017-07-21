/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Power;
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
public class PowerDaoDbImpl implements PowerDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_POWER
            = "insert into Power (description)"
            + "values (?)";

    private static final String SQL_DELETE_POWER
            = "delete from Power where PowerID = ?";

    private static final String SQL_UPDATE_POWER
            = "update Power set description = ? where PowerID = ?";

    private static final String SQL_SELECT_POWER
            = "select * from Power where PowerID = ?";

    private static final String SQL_SELECT_ALL_POWERS
            = "select * from Power "
            + "order by description";

    private static final String SQL_SELECT_ALL_POWERS_BY_METAHUMANID
            = "select Power.PowerID, Power.Description from Power "
            + "join MetaHuman_Power_Bridge on MetaHuman_Power_Bridge.PowerID = Power.PowerID "
            + "join MetaHuman on MetaHuman_Power_Bridge.MetaHumanID = MetaHuman.MetaHumanID "
            + "where MetaHuman.MetaHumanID = ?";

    private static final String SQL_DELETE_METAHUMAN_POWER_BRIDGE
            = "delete from MetaHuman_Power_Bridge where PowerID = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPower(Power power) {
        jdbcTemplate.update(SQL_INSERT_POWER,
                power.getDescription());

        String powerID = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", String.class);

        power.setPowerID(powerID);
    }

    @Override
    public void deletePower(String powerID) {
        jdbcTemplate.update(SQL_DELETE_METAHUMAN_POWER_BRIDGE, powerID);
        jdbcTemplate.update(SQL_DELETE_POWER, powerID);
    }

    @Override
    public void updatePower(Power power) {
        jdbcTemplate.update(SQL_UPDATE_POWER,
                power.getDescription(),
                power.getPowerID());
    }

    @Override
    public Power getPowerById(String id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_POWER, new PowerMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowersByMetaHumanId(String id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_POWERS_BY_METAHUMANID, new PowerMapper(), id);
    }

    @Override
    public List<Power> getAllPowers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_POWERS, new PowerMapper());
    }

    private static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            Power pow = new Power();
            pow.setDescription(rs.getString("description"));
            pow.setPowerID(rs.getString("powerID"));
            return pow;
        }
    }

}
