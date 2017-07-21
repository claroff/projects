/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.model.Location;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class LocationServiceImpl implements LocationService {

    private LocationDao dao;

    @Inject
    public LocationServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    @Override
    public void addLocation(Location location) {
        dao.addLocation(location);
    }

    @Override
    public void deleteLocation(String locationID) {
        dao.deleteLocation(locationID);
    }

    @Override
    public void updateLocation(Location location) {
        dao.updateLocation(location);
    }

    @Override
    public Location getLocationById(String id) {
        return dao.getLocationById(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.getAllLocations();
    }

    @Override
    public List<Location> getAllLocationsByMetaHumanID(String id) {
        return dao.getAllLocationsByMetaHumanID(id);
    }

}
