/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.MetaHumanSightingBridgeDao;
import com.sg.superheroes.model.MetaHumanSightingBridge;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class MetaHumanSightingBridgeServiceImpl implements MetaHumanSightingBridgeService {

    private MetaHumanSightingBridgeDao dao;

    @Inject
    public MetaHumanSightingBridgeServiceImpl(MetaHumanSightingBridgeDao dao) {
        this.dao = dao;
    }

    @Override
    public void addMetaHumanSightingBridge(MetaHumanSightingBridge metaHumanSightingBridge) {
        if (metaHumanSightingBridge.getSighting() != null && metaHumanSightingBridge.getMetaHuman() != null
                && dao.getMetaHumanSightingBridgeByMetaHumanAndSightingId(metaHumanSightingBridge.getMetaHuman().getMetaHumanID(), metaHumanSightingBridge.getSighting().getSightingID()) == null) {
            dao.addMetaHumanSightingBridge(metaHumanSightingBridge);
        }
    }

    @Override
    public void deleteMetaHumanSightingBridge(String metaHumanSightingBridgeID) {
        dao.deleteMetaHumanSightingBridge(metaHumanSightingBridgeID);
    }

    @Override
    public MetaHumanSightingBridge getMetaHumanSightingBridgeByMetaHumanId(String id) {
        return dao.getMetaHumanSightingBridgeByMetaHumanId(id);
    }

    @Override
    public MetaHumanSightingBridge getMetaHumanSightingBridgeBySightingId(String id) {
        return dao.getMetaHumanSightingBridgeBySightingId(id);
    }

    @Override
    public MetaHumanSightingBridge getMetaHumanSightingBridgeByMetaHumanAndSightingId(String metaHumanID, String sightingID) {
        return dao.getMetaHumanSightingBridgeByMetaHumanAndSightingId(metaHumanID, sightingID);
    }

}
