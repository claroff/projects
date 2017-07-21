/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.dao;

import com.sg.superheroes.model.MetaHuman;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface MetaHumanDao {

    public void addMetaHuman(MetaHuman metaHuman);

    public void deleteMetaHuman(String metaHumanID);

    public void updateMetaHuman(MetaHuman metaHuman);

    public MetaHuman getMetaHumanById(String id);

    public List<MetaHuman> getAllMetaHumans();

    public List<MetaHuman> getAllMetaHumansByLocationID(String id);

    public List<MetaHuman> getAllMetaHumansByOrganizationID(String id);

    public List<MetaHuman> getAllMetaHumansBySightingID(String id);

//    public void deleteMetaHumanOrganizationBridgeWhenDeletingMetaHuman(String metaHumanID);
}
