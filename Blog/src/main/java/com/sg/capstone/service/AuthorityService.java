/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.capstone.service;

import com.sg.capstone.model.Authority;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface AuthorityService {

    public List<Authority> getAllAuthorities(Long userID);

    public List<Authority> getAllAuthoritiesForReal();

    public void deleteAuthority(Long authorityID);

    public void addAuthority(Authority authority);

}
