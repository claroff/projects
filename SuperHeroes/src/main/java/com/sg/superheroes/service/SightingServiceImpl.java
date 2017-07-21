/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.dao.SightingDao;
import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.Sighting;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class SightingServiceImpl implements SightingService {

    private SightingDao dao;
    private LocationDao locDao;

    @Inject
    public SightingServiceImpl(SightingDao dao, LocationDao locDao) {
        this.dao = dao;
        this.locDao = locDao;
    }

    @Override
    public void addSighting(Sighting sighting) {
        dao.addSighting(sighting);
    }

    @Override
    public void deleteSighting(String locationID) {
        dao.deleteSighting(locationID);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        dao.updateSighting(sighting);
    }

    @Override
    public Sighting getSightingById(String id) { //gives sighting location object
        Sighting sighting = dao.getSightingById(id);
        if (sighting != null) {
            String locId = sighting.getLocation().getLocationID();
            Location location = locDao.getLocationById(locId);
            sighting.setLocation(location);
        }
        return sighting;
    }

    @Override
    public List<Sighting> getAllSightings() { //loops through sighting list and adds actual location object so that location data may be used
        List<Sighting> sList = new ArrayList<>();
        for (Sighting currentSighting : dao.getAllSightings()) {
            String id = currentSighting.getLocation().getLocationID();
            Location location = locDao.getLocationById(id);
            currentSighting.setLocation(location);
            sList.add(currentSighting);
        }

        return sList;
    }

    @Override
    public List<Sighting> getAllSightingsByDate(Date date) {
        return dao.getAllSightingsByDate(date);
    }

    @Override
    public List<Sighting> getRecentSightings() { //loops through sighting list and adds actual location object so that location data may be used
        List<Sighting> sList = new ArrayList<>();
        for (Sighting currentSighting : dao.getRecentSightings()) {
            String id = currentSighting.getLocation().getLocationID();
            Location location = locDao.getLocationById(id);
            currentSighting.setLocation(location);
            sList.add(currentSighting);
        }

        return sList;
    }

}
