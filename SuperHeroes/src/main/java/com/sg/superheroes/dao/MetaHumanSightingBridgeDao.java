/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import com.sg.superheroes.model.MetaHumanSightingBridge;
import com.sg.superheroes.model.Sighting;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface MetaHumanSightingBridgeDao {

    public void addMetaHumanSightingBridge(MetaHumanSightingBridge metaHumanSightingBridge);

    public void deleteMetaHumanSightingBridge(String metaHumanSightingBridgeID);

    public MetaHumanSightingBridge getMetaHumanSightingBridgeByMetaHumanId(String id);

    public MetaHumanSightingBridge getMetaHumanSightingBridgeBySightingId(String id);

    public MetaHumanSightingBridge getMetaHumanSightingBridgeByMetaHumanAndSightingId(String metaHumanID, String sightingID);

    //public List<MetaHumanSightingBridge> getAllMetaHumanSightingBridges();
}
