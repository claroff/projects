/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author chandler
 */
public class Sighting {

    private String sightingID;
    private String name;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;
    private Location location;

    public String getSightingID() {
        return sightingID;
    }

    public void setSightingID(String sightingID) {
        this.sightingID = sightingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
