/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.model.MetaHumanPowerBridge;

/**
 *
 * @author chandler
 */
public interface MetaHumanPowerBridgeService {

    public void addMetaHumanPowerBridge(MetaHumanPowerBridge metaHumanPowerBridge);

    public void deleteMetaHumanPowerBridge(String metaHumanPowerBridgeID);

    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanId(String id);

    public MetaHumanPowerBridge getMetaHumanPowerBridgeByPowerId(String id);

    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanAndPowerId(String metaHumanID, String powerID);

}
