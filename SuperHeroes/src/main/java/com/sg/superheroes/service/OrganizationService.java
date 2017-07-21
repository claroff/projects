/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.model.Organization;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface OrganizationService {

    public void addOrganization(Organization organization);

    public void deleteOrganization(String locationID);

    public void updateOrganization(Organization organization);

    public Organization getOrganizationById(String id);

    public List<Organization> getAllOrganizations();

    public List<Organization> getAllOrganizationsByMetaHumanID(String id);

}
