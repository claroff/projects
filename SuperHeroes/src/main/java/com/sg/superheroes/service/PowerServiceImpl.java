/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.PowerDao;
import com.sg.superheroes.model.Power;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class PowerServiceImpl implements PowerService {

    private PowerDao dao;

    @Inject
    public PowerServiceImpl(PowerDao dao) {
        this.dao = dao;
    }

    @Override
    public void addPower(Power power) {
        dao.addPower(power);
    }

    @Override
    public void deletePower(String locationID) {
        dao.deletePower(locationID);
    }

    @Override
    public void updatePower(Power power) {
        dao.updatePower(power);
    }

    @Override
    public Power getPowerById(String id) {
        return dao.getPowerById(id);
    }

    @Override
    public List<Power> getAllPowers() {
        return dao.getAllPowers();
    }

    @Override
    public List<Power> getAllPowersByMetaHumanId(String id) {
        return dao.getAllPowersByMetaHumanId(id);
    }

}
