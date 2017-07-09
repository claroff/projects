/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import static com.sg.flooringmastery.dao.FlooringMasteryDaoFileImpl.DELIMITER;
import com.sg.flooringmastery.dto.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Chandler
 */
public class FlooringMasteryStateDaoStub implements FlooringMasteryStateDao {

    public final String STATE_FILE = "taxesTEST.txt";

    @Override
    public void writeStates(List<State> stateList) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(STATE_FILE));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save inventory data.", e);
        }

        out.println("State,TaxRate");

        for (State currentState : stateList) {
            out.println(currentState.getState() + DELIMITER
                    + currentState.getTaxRate());

            out.flush();
        }
        out.close();
    }

    @Override
    public List<State> loadStates() throws FlooringMasteryPersistenceException {
        List<State> list = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(STATE_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load states into memory.", e);
        }

        scanner.nextLine();

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            State currentState = new State();

            currentState.setState(currentTokens[0]);
            BigDecimal bd = new BigDecimal(currentTokens[1]);
            currentState.setTaxRate(bd);

            list.add(currentState);
        }
        return list;
    }
}
