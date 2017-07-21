/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroes.service;

import com.sg.superheroes.dao.LocationDao;
import com.sg.superheroes.dao.OrganizationDao;
import com.sg.superheroes.model.Location;
import com.sg.superheroes.model.Organization;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author chandler
 */
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationDao dao;
    private LocationDao locDao;

    @Inject
    public OrganizationServiceImpl(OrganizationDao dao, LocationDao locDao) {
        this.dao = dao;
        this.locDao = locDao;
    }

    @Override
    public void addOrganization(Organization organization) {
        dao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(String locationID) {
        dao.deleteOrganization(locationID);
    }

    @Override
    public void updateOrganization(Organization organization) {
        dao.updateOrganization(organization);
    }

    @Override
    public Organization getOrganizationById(String id) {
        Organization organization = dao.getOrganizationById(id);
        if (organization != null) {
            String locId = organization.getLocation().getLocationID();
            Location location = locDao.getLocationById(locId);
            organization.setLocation(location);
        }
        return organization;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return dao.getAllOrganizations();
    }

    @Override
    public List<Organization> getAllOrganizationsByMetaHumanID(String id) {
        return dao.getAllOrganizationsByMetaHumanID(id);
    }

}
