/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryStateDao;
import com.sg.flooringmastery.dto.State;
import java.util.List;

/**
 *
 * @author chandler
 */
public class FlooringMasteryStateServiceFileImpl implements FlooringMasteryStateService {

    FlooringMasteryStateDao stateDao;

    public FlooringMasteryStateServiceFileImpl(FlooringMasteryStateDao stateDao) {
        this.stateDao = stateDao;
    }

    @Override
    public void writeStates(List<State> stateList) throws FlooringMasteryPersistenceException {
        stateDao.writeStates(stateList);
    }

    @Override
    public List<State> loadStates() throws FlooringMasteryPersistenceException {
        return stateDao.loadStates();
    }

}
