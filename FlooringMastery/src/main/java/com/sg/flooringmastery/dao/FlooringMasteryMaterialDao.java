/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.State;
import java.util.List;

/**
 *
 * @author chandler
 */
public interface FlooringMasteryMaterialDao {

    public void writeMaterials(List<Material> matList)
            throws FlooringMasteryPersistenceException;

    public List<Material> loadMaterials()
            throws FlooringMasteryPersistenceException;

}
