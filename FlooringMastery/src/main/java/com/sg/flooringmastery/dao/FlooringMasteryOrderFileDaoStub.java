/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import static com.sg.flooringmastery.dao.FlooringMasteryDaoFileImpl.DELIMITER;
import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Chandler
 */
public class FlooringMasteryOrderFileDaoStub implements FlooringMasteryOrderFileDao {

    @Override
    public List<Order> loadOrderFile(String date) throws FlooringMasteryPersistenceException {
        List<Order> list = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader("Orders_" + date + "TEST.txt")));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load inventory into memory.", e);
        }
        scanner.nextLine();

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Order currentOrder = new Order();

            currentOrder.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy")));
            currentOrder.setOrderNumber(Integer.parseInt(currentTokens[0]));
            currentOrder.setCustomerName(currentTokens[1]);
            currentOrder.setState(currentTokens[2]);
            BigDecimal bd = new BigDecimal(currentTokens[3]);
            currentOrder.setTaxRate(bd);
            currentOrder.setProductType(currentTokens[4]);
            bd = new BigDecimal(currentTokens[5]);
            currentOrder.setArea(bd);
            bd = new BigDecimal(currentTokens[6]);
            currentOrder.setCostSqFt(bd);
            bd = new BigDecimal(currentTokens[7]);
            currentOrder.setLaborCostSqFt(bd);
            bd = new BigDecimal(currentTokens[8]);
            currentOrder.setMatCost(bd);
            bd = new BigDecimal(currentTokens[9]);
            currentOrder.setLaborCost(bd);
            bd = new BigDecimal(currentTokens[10]);
            currentOrder.setTax(bd);
            bd = new BigDecimal(currentTokens[11]);
            currentOrder.setTotalCost(bd);

            list.add(currentOrder);
        }
        return list;
    }

    @Override
    public void writeOrderFile(List<Order> orderList) throws FlooringMasteryPersistenceException {
        //If orderList is empty, there is nothing to append
        if (orderList.isEmpty()) {
            return;
        }

        File txtFile = new File("Orders_" + orderList.get(0).getDate().format(DateTimeFormatter.ofPattern("MMddyyyy")) + "TEST.txt");
        boolean exists = txtFile.exists();

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(txtFile.getPath(), exists));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save inventory data.", e);
        }

        if (!exists) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
                    + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        }
        for (Order currentOrder : orderList) {
            out.println(currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getCostSqFt() + DELIMITER
                    + currentOrder.getLaborCostSqFt() + DELIMITER
                    + currentOrder.getMatCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTax() + DELIMITER
                    + currentOrder.getTotalCost());

            out.flush();
        }
        out.close();
    }

    @Override
    public void writeOrderFileFull(List<Order> orderList) throws FlooringMasteryPersistenceException {
        //If orderList is empty, there is nothing to append
        if (orderList.isEmpty()) {
            return;
        }

        File txtFile = new File("Orders_" + orderList.get(0).getDate().format(DateTimeFormatter.ofPattern("MMddyyyy")) + "TEST.txt");

        try {
            PrintWriter out = new PrintWriter(
                    new FileWriter(txtFile.getPath()));

            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
                    + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");

            for (Order currentOrder : orderList) {
                out.println(currentOrder.getOrderNumber() + DELIMITER
                        + currentOrder.getCustomerName() + DELIMITER
                        + currentOrder.getState() + DELIMITER
                        + currentOrder.getTaxRate() + DELIMITER
                        + currentOrder.getProductType() + DELIMITER
                        + currentOrder.getArea() + DELIMITER
                        + currentOrder.getCostSqFt() + DELIMITER
                        + currentOrder.getLaborCostSqFt() + DELIMITER
                        + currentOrder.getMatCost() + DELIMITER
                        + currentOrder.getLaborCost() + DELIMITER
                        + currentOrder.getTax() + DELIMITER
                        + currentOrder.getTotalCost());

                out.flush();
            }
            out.close();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save inventory data.", e);
        }
    }

    @Override
    public void deleteOrderFile(List<Order> orderList) throws FlooringMasteryPersistenceException {

        File txtFile = new File("Orders_" + orderList.get(0).getDate().format(DateTimeFormatter.ofPattern("MMddyyyy")) + "TEST.txt");

        txtFile.delete();

    }
}
