/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.dao;

import com.sg.capstone.model.StaticPage;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface StaticPageDao {

    public void addStaticPage(StaticPage staticPage);

    public void updateStaticPage(StaticPage staticPage);

    public void deleteStaticPage(Long staticPageId);

    public List<StaticPage> getAllStaticPages();

    public StaticPage getStaticPageByID(Long staticPageId);

//    public void deactivateStaticPage(Long staticPageId);
}
