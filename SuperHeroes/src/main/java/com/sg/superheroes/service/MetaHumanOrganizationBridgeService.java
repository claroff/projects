/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.model.MetaHumanOrganizationBridge;

/**
 *
 * @author chandler
 */
public interface MetaHumanOrganizationBridgeService {

    public void addMetaHumanOrganizationBridge(MetaHumanOrganizationBridge metaHumanOrganizationBridge);

    public void deleteMetaHumanOrganizationBridge(String metaHumanOrganizationBridgeID);

    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanId(String id);

    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByOrganizationId(String id);

    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(String MHid, String orgID);

}
