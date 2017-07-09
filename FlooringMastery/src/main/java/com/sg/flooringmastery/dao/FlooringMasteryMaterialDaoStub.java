/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import static com.sg.flooringmastery.dao.FlooringMasteryDaoFileImpl.DELIMITER;
import com.sg.flooringmastery.dto.Material;
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
public class FlooringMasteryMaterialDaoStub implements FlooringMasteryMaterialDao {

    public final String MAT_FILE = "productsTEST.txt";

    @Override
    public void writeMaterials(List<Material> matList) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(MAT_FILE));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save inventory data.", e);
        }

        out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");

        for (Material currentMat : matList) {
            out.println(currentMat.getType() + DELIMITER
                    + currentMat.getCostSqFt() + DELIMITER
                    + currentMat.getLaborCostSqFt());

            out.flush();
        }
        out.close();
    }

    @Override
    public List<Material> loadMaterials() throws FlooringMasteryPersistenceException {
        List<Material> list = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(MAT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load materials into memory.", e);
        }

        scanner.nextLine();

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Material currentMat = new Material();

            currentMat.setType(currentTokens[0]);
            BigDecimal bd = new BigDecimal(currentTokens[1]);
            currentMat.setCostSqFt(bd);
            bd = new BigDecimal(currentTokens[2]);
            currentMat.setLaborCostSqFt(bd);

            list.add(currentMat);
        }
        return list;
    }

}
