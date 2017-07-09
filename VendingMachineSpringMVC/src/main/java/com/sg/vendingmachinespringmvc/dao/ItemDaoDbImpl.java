/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
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
public class ItemDaoDbImpl implements ItemDao {

    private static final String SQL_INSERT_ITEM
            = "insert into Item "
            + "(name, stock, price) "
            + "values (?, ?, ?)";
    private static final String SQL_DELETE_ITEM
            = "delete from Item where itemID = ?";
    private static final String SQL_SELECT_ITEM
            = "select * from Item where itemID = ?";
    private static final String SQL_UPDATE_ITEM
            = "update Item set "
            + "name = ?, stock = ?, price = ? "
            + "where itemID = ?";
    private static final String SQL_SELECT_ALL_ITEMS
            = "select * from Item";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item addItem(Item item) {
        jdbcTemplate.update(SQL_INSERT_ITEM,
                item.getName(),
                item.getStock(),
                item.getPrice());

        //query database for id just assigned to new row in database
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        //set new id value to contact object and return it
        item.setId(newId);
        return item;
    }

    @Override
    public void removeItem(int itemId) {
        jdbcTemplate.update(SQL_DELETE_ITEM, itemId);
    }

    @Override
    public void updateItem(Item item) {
        jdbcTemplate.update(SQL_UPDATE_ITEM,
                item.getName(),
                item.getStock(),
                item.getPrice(),
                item.getId());
    }

    @Override
    public Item getItemById(int itemId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ITEM, new ItemMapper(), itemId);
        } catch (EmptyResultDataAccessException ex) {
            //no results for given id
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ITEMS, new ItemMapper());
    }

    @Override
    public void purchaseItem(Item item) { //POSSIBLY TOTALLY WRONG
        jdbcTemplate.update(SQL_UPDATE_ITEM,
                item.getName(),
                item.getStock() - 1,
                item.getPrice(),
                item.getId());
    }

    private static final class ItemMapper implements RowMapper<Item> {

        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();
            item.setName(rs.getString("name"));
            item.setStock(rs.getInt("stock"));
            item.setPrice(rs.getBigDecimal("price"));
            item.setId(rs.getInt("itemID"));
            return item;
        }
    }

}
