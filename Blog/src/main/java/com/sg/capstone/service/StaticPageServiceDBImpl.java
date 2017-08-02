/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.StaticPageDao;
import com.sg.capstone.model.StaticPage;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class StaticPageServiceDBImpl implements StaticPageService {

    private StaticPageDao staticPageDao;

    @Inject
    public StaticPageServiceDBImpl(StaticPageDao staticPageDao) {
        this.staticPageDao = staticPageDao;
    }

    @Override
    public void addStaticPage(StaticPage staticPage) {
        staticPageDao.addStaticPage(staticPage);
    }

    @Override
    public void updateStaticPage(StaticPage staticPage) {
        staticPageDao.updateStaticPage(staticPage);
    }

    @Override
    public void deleteStaticPage(long staticPageId) {
        staticPageDao.deleteStaticPage(staticPageId);
    }

    @Override
    public List<StaticPage> getAllStaticPages() {
        return staticPageDao.getAllStaticPages();
    }

    @Override
    public StaticPage getStaticPageByID(long staticPageId) {
        return staticPageDao.getStaticPageByID(staticPageId);
    }

}
