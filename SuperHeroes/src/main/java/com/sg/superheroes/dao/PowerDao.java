/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Power;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface PowerDao {

    public void addPower(Power power);

    public void deletePower(String locationID);

    public void updatePower(Power power);

    public Power getPowerById(String id);

    public List<Power> getAllPowers();

    public List<Power> getAllPowersByMetaHumanId(String id);

}
