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
public class MetaHumanPowerBridge {

    private String metaHumanPowerBridgeID;
    private MetaHuman metaHuman;
    private Power power;

    public String getMetaHumanPowerBridgeID() {
        return metaHumanPowerBridgeID;
    }

    public void setMetaHumanPowerBridgeID(String metaHumanPowerBridgeID) {
        this.metaHumanPowerBridgeID = metaHumanPowerBridgeID;
    }

    public MetaHuman getMetaHuman() {
        return metaHuman;
    }

    public void setMetaHuman(MetaHuman metaHuman) {
        this.metaHuman = metaHuman;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

}
