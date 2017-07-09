/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Chandler
 */
public class FlooringMasteryOrderNumDaoStub implements FlooringMasteryOrderNumDao {

    public static final String ORDER_NUM = "orderNumTEST.txt";

    @Override
    public Integer loadOrderNumberFile() throws FlooringMasteryPersistenceException {

        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ORDER_NUM)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load inventory into memory.", e);
        }

        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public void writeOrderNumberFile(Order order) throws FlooringMasteryPersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_NUM));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save inventory data.", e);
        }

        out.println(order.getOrderNumber() + 1);

        out.flush();

        out.close();
    }

}
