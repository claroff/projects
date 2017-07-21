/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.MetaHumanOrganizationBridgeDao;
import com.sg.superheroes.model.MetaHumanOrganizationBridge;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class MetaHumanOrganizationBridgeServiceImpl implements MetaHumanOrganizationBridgeService {

    private MetaHumanOrganizationBridgeDao dao;

    @Inject
    public MetaHumanOrganizationBridgeServiceImpl(MetaHumanOrganizationBridgeDao dao) {
        this.dao = dao;
    }

    @Override
    public void addMetaHumanOrganizationBridge(MetaHumanOrganizationBridge metaHumanOrganizationBridge) {
        if (metaHumanOrganizationBridge.getOrganization() != null
                //makes sure no duplicate bridges
                && dao.getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(metaHumanOrganizationBridge.getMetaHuman().getMetaHumanID(), metaHumanOrganizationBridge.getOrganization().getOrganizationID()) == null) {
            dao.addMetaHumanOrganizationBridge(metaHumanOrganizationBridge);
        }
    }

    @Override
    public void deleteMetaHumanOrganizationBridge(String metaHumanOrganizationBridgeID) {
        dao.deleteMetaHumanOrganizationBridge(metaHumanOrganizationBridgeID);
    }

    @Override
    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanId(String id) {
        return dao.getMetaHumanOrganizationBridgeByMetaHumanId(id);
    }

    @Override
    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByOrganizationId(String id) {
        return dao.getMetaHumanOrganizationBridgeByOrganizationId(id);
    }

    @Override
    public MetaHumanOrganizationBridge getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(String MHid, String orgID) {
        return dao.getMetaHumanOrganizationBridgeByMetaHumanAndOrganizationId(MHid, orgID);
    }

}
