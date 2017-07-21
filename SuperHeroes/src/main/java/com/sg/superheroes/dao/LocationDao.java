/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Location;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface LocationDao {

    public void addLocation(Location location);

    public void deleteLocation(String locationID);

    public void updateLocation(Location location);

    public Location getLocationById(String id);

    public List<Location> getAllLocations();

    public List<Location> getAllLocationsByMetaHumanID(String id);

}
