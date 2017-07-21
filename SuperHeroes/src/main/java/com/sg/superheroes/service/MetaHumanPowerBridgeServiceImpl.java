/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.MetaHumanPowerBridgeDao;
import com.sg.superheroes.model.MetaHumanPowerBridge;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class MetaHumanPowerBridgeServiceImpl implements MetaHumanPowerBridgeService {

    private MetaHumanPowerBridgeDao dao;

    @Inject
    public MetaHumanPowerBridgeServiceImpl(MetaHumanPowerBridgeDao dao) {
        this.dao = dao;
    }

    @Override
    public void addMetaHumanPowerBridge(MetaHumanPowerBridge metaHumanPowerBridge) {
        if (metaHumanPowerBridge.getPower() != null && metaHumanPowerBridge.getMetaHuman() != null
                && dao.getMetaHumanPowerBridgeByMetaHumanAndPowerId(metaHumanPowerBridge.getMetaHuman().getMetaHumanID(), metaHumanPowerBridge.getPower().getPowerID()) == null) {
            dao.addMetaHumanPowerBridge(metaHumanPowerBridge);
        }
    }

    @Override
    public void deleteMetaHumanPowerBridge(String metaHumanPowerBridgeID) {
        dao.deleteMetaHumanPowerBridge(metaHumanPowerBridgeID);
    }

    @Override
    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanId(String id) {
        return dao.getMetaHumanPowerBridgeByMetaHumanId(id);
    }

    @Override
    public MetaHumanPowerBridge getMetaHumanPowerBridgeByPowerId(String id) {
        return dao.getMetaHumanPowerBridgeByPowerId(id);
    }

    @Override
    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanAndPowerId(String metaHumanID, String powerID) {
        return dao.getMetaHumanPowerBridgeByMetaHumanAndPowerId(metaHumanID, powerID);
    }

}
