/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.dao.AuthorityDao;
import com.sg.capstone.dao.UserDao;
import com.sg.capstone.model.Authority;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class AuthorityServiceDBImpl implements AuthorityService {
    
    private AuthorityDao authorityDao;
    
    @Inject
    public AuthorityServiceDBImpl(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }
    
    @Override
    public List<Authority> getAllAuthorities(Long userID) {
        return authorityDao.getAllAuthorities(userID);
    }
    
    @Override
    public List<Authority> getAllAuthoritiesForReal() {
        return authorityDao.getAllAuthoritiesForReal();
    }
    
    @Override
    public void deleteAuthority(Long authorityID) {
        authorityDao.deleteAuthority(authorityID);
    }
    
    @Override
    public void addAuthority(Authority authority) {
        authorityDao.addAuthority(authority);
    }

}
