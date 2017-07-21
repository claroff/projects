/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.Sighting;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface SightingDao {

    public void addSighting(Sighting sighting);

    public void deleteSighting(String locationID);

    public void updateSighting(Sighting sighting);

    public Sighting getSightingById(String id);

    public List<Sighting> getAllSightings();

    public List<Sighting> getAllSightingsByDate(Date date);

    public List<Sighting> getRecentSightings();

}
