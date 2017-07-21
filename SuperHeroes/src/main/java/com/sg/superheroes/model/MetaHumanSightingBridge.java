/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.model;

/**
 *
 * @author chandler
 */
public class MetaHumanSightingBridge {

    private String metaHumanSightingBridgeID;
    private MetaHuman metaHuman;
    private Sighting sighting;

    public String getMetaHumanSightingBridgeID() {
        return metaHumanSightingBridgeID;
    }

    public void setMetaHumanSightingBridgeID(String metaHumanSightingBridgeID) {
        this.metaHumanSightingBridgeID = metaHumanSightingBridgeID;
    }

    public MetaHuman getMetaHuman() {
        return metaHuman;
    }

    public void setMetaHuman(MetaHuman metaHuman) {
        this.metaHuman = metaHuman;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }

}
