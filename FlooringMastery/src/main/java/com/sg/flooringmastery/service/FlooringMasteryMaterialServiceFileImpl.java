/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryMaterialDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author chandler
 */
public class FlooringMasteryMaterialServiceFileImpl implements FlooringMasteryMaterialService {

    FlooringMasteryMaterialDao materialDao;

    public FlooringMasteryMaterialServiceFileImpl(FlooringMasteryMaterialDao materialDao) {
        this.materialDao = materialDao;
    }

    @Override
    public void writeMaterials(List<Material> matList) throws FlooringMasteryPersistenceException {
        materialDao.writeMaterials(matList);
    }

    @Override
    public List<Material> loadMaterials() throws FlooringMasteryPersistenceException {
        return materialDao.loadMaterials();
    }

}
