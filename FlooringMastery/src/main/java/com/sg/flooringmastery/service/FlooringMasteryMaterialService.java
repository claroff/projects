/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryMaterialService {

    public void writeMaterials(List<Material> matList)
            throws FlooringMasteryPersistenceException;

    public List<Material> loadMaterials()
            throws FlooringMasteryPersistenceException;

}
