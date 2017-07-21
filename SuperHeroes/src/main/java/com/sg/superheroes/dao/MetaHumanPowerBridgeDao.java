/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanPowerBridge;
import com.sg.superheroes.model.Power;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface MetaHumanPowerBridgeDao {

    public void addMetaHumanPowerBridge(MetaHumanPowerBridge metaHumanPowerBridge);

    public void deleteMetaHumanPowerBridge(String metaHumanPowerBridgeID);

    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanId(String id);

    public MetaHumanPowerBridge getMetaHumanPowerBridgeByPowerId(String id);

    public MetaHumanPowerBridge getMetaHumanPowerBridgeByMetaHumanAndPowerId(String metaHumanID, String powerID);

    //public List<MetaHumanPowerBridge> getAllMetaHumanPowerBridges();
}
