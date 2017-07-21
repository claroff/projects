/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.MetaHumanDao;
import com.sg.superheroes.dao.OrganizationDao;
import com.sg.superheroes.model.MetaHuman;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class MetaHumanServiceImpl implements MetaHumanService {

    private MetaHumanDao dao;
    private OrganizationDao orgDao;

    @Inject
    public MetaHumanServiceImpl(MetaHumanDao dao, OrganizationDao orgDao) {
        this.dao = dao;
        this.orgDao = orgDao;
    }

    @Override
    public void addMetaHuman(MetaHuman metaHuman) {
        dao.addMetaHuman(metaHuman);
    }

    @Override
    public void deleteMetaHuman(String metaHumanID) {
        dao.deleteMetaHuman(metaHumanID);
    }

    @Override
    public void updateMetaHuman(MetaHuman metaHuman) {
        dao.updateMetaHuman(metaHuman);
    }

    @Override
    public MetaHuman getMetaHumanById(String id) {
        return dao.getMetaHumanById(id);
    }

    @Override
    public List<MetaHuman> getAllMetaHumans() {
        return dao.getAllMetaHumans();
    }

    @Override
    public List<MetaHuman> getAllMetaHumansByLocationID(String id) {
        return dao.getAllMetaHumansByLocationID(id);
    }

    @Override
    public List<MetaHuman> getAllMetaHumansByOrganizationID(String id) {
        return dao.getAllMetaHumansByOrganizationID(id);
    }

    @Override
    public List<MetaHuman> getAllMetaHumansBySightingID(String id) {
        return dao.getAllMetaHumansBySightingID(id);
    }

}
