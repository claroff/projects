/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import com.sg.superheroes.model.Organization;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface MetaHumanOrganizationBridgeDao {

    public void addMetaHumanOrganizationBridge(MetaHumanOrganizationBridge metaHumanOrganizationBridge);

    public void deleteMetaHumanOrganizationBridge(String metaHumanOrganizationBridgeID);

    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanId(String id);

    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByOrganizationId(String id);
    //public List<MetaHumanOrganizationBridge> getAllMetaHumanOrganizationBridges();

    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(String MHid, String orgID);
}
