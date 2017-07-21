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
public class MetaHumanOrganizationBridge {

    private String metaHumanOrganizationBridgeID;
    private MetaHuman metaHuman;
    private Organization organization;

    public String getMetaHumanOrganizationBridgeID() {
        return metaHumanOrganizationBridgeID;
    }

    public void setMetaHumanOrganizationBridgeID(String metaHumanOrganizationBridgeID) {
        this.metaHumanOrganizationBridgeID = metaHumanOrganizationBridgeID;
    }

    public MetaHuman getMetaHuman() {
        return metaHuman;
    }

    public void setMetaHuman(MetaHuman metaHuman) {
        this.metaHuman = metaHuman;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
