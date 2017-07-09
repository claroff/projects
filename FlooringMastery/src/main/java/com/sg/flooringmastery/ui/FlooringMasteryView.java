/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author chandler
 */
public class FlooringMasteryView {

    UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<SWC Corp. Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Save Current Work");
        io.print("* 6. Quit");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please enter selection: ", 1, 7); //7 is production/training switch
    }

    public Integer getState(List<State> stateList) {
        int i = 1;
        for (State state : stateList) {
            io.print(i + ". " + state.getState());
            i++;
        }

        int stateSelect = io.readInt("State: ", 1, stateList.size());
        return stateSelect - 1; //accounts for top text
    }

    public Integer getMat(List<Material> matList) {
        int i = 1;
        for (Material mat : matList) {
            io.print(i + ". " + mat.getType());
            i++;
        }

        int matSelect = io.readInt("Material: ", 1, matList.size());
        return matSelect - 1;
    }

    public Order getOrderData(Order order) {
        LocalDate ld = LocalDate.now();
        boolean notValidated = true;
        DateTimeFormatter formatCheck = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String userInput = io.readString("Please enter date (MM/dd/yyyy): ");
        while (notValidated) {
            try {
                ld = LocalDate.parse(userInput, formatCheck);
                notValidated = false;
            } catch (Exception e) {
                userInput = io.readString("enter in proper format:");
            }
        }
        order.setDate(ld);
        notValidated = true;
        String check = "";
        while (notValidated) {
            check = io.readString("Customer name: ");
            order.setCustomerName(check);
            if (!check.equals("")) {
                notValidated = false;
            }
        }
        notValidated = true;
        double bdDouble = 0;
        while (notValidated) {
            try {
                bdDouble = io.readDouble("Area: ", 0, Double.MAX_VALUE);
                notValidated = false;
            } catch (NumberFormatException e) {
                io.print("Please enter a number.");
            }
        }
        order.setArea(new BigDecimal(String.valueOf(bdDouble)));

        return order;
    }

    public void displayOrderInfo(Order order) {
        io.print("Order date: " + order.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.print("Customer name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Flooring type: " + order.getProductType());
        io.print("Area of floor: " + order.getArea().toString());
        io.print("Cost per sq.ft: " + order.getCostSqFt());
        io.print("Labor cost: " + order.getLaborCostSqFt());
        io.print("Total cost: " + order.getTotalCost().toString());
    }

    public boolean checkOrderCorrect() {
        String answer = io.readString("Is this information correct? (Y/N)");

        if (answer.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void displayOrders(List<Order> list) {
        for (Order order : list) {
            io.print("Order #" + order.getOrderNumber() + " | Customer: " + order.getCustomerName()
                    + " | State: " + order.getState() + " | Type: " + order.getProductType());
        }

    }

    public String getOrderDate() {
        LocalDate ld = LocalDate.now();
        boolean notValidated = true;
        DateTimeFormatter formatCheck = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String userInput = io.readString("Please enter date to search (MM/dd/yyyy): ");
        while (notValidated) {
            try {
                ld = LocalDate.parse(userInput, formatCheck);
                notValidated = false;
            } catch (Exception e) {
                userInput = io.readString("enter in proper format:");
            }
        }

        return ld.format(DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    public Integer getOrderNumber() {
        return io.readInt("Please enter order number: ");
    }

    public Order editState(Order order, List<State> stateList) {
        int i = 1;
        for (State state : stateList) {
            io.print(i + ". " + state.getState());
            i++;
        }
        int stateSelect = io.readIntEmptyString("Enter state [" + order.getState() + "]: ", 1, stateList.size());
        if (stateSelect != 0) {
            order.setState(stateList.get(stateSelect - 1).getState());
            order.setTaxRate(stateList.get(stateSelect - 1).getTaxRate());
        }
        return order;
    }

    public Order editMat(Order order, List<Material> matList) {
        int i = 1;
        for (Material mat : matList) {
            io.print(i + ". " + mat.getType());
            i++;
        }
        int matSelect = io.readIntEmptyString("Enter material [" + order.getProductType() + "]: ", 1, matList.size());
        if (matSelect != 0) {
            order.setProductType(matList.get(matSelect - 1).getType());
            order.setCostSqFt(matList.get(matSelect - 1).getCostSqFt());
            order.setLaborCostSqFt(matList.get(matSelect - 1).getLaborCostSqFt());
        }
        return order;
    }

    public Order editOrder(Order order, List<State> stateList, List<Material> matList) {
        String blank = "";
        String userInput = "placeholder";
        boolean notValidated = true;
        LocalDate ld = order.getDate();
        DateTimeFormatter formatCheck = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        userInput = (io.readString("Enter date of order (MM/dd/yyyy) [" + order.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "]:"));
        if (!userInput.equals("")) {
            while (notValidated) {
                try {
                    ld = LocalDate.parse(userInput, formatCheck);
                    notValidated = false;
                } catch (Exception e) {
                    io.print("enter in proper format:");
                    userInput = (io.readString("Enter date of order (MM/dd/yyyy) [" + order.getDate().toString() + "]:"));
                }

            }
        }
        if (!userInput.equals(order.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))) {
            order.setDate(ld);
        }

        userInput = io.readString("Enter customer name [" + order.getCustomerName() + "]: ");
        if (!userInput.equals(blank) && !userInput.equals(order.getCustomerName())) {
            order.setCustomerName(userInput);
        }

        userInput = io.readString("Enter area [" + order.getArea() + "]: ");

        notValidated = true;
        if (!userInput.equals(blank)) {
            while (notValidated) {
                try {
                    if (!userInput.equals(order.getArea().toPlainString())) {
                        BigDecimal bd = new BigDecimal(userInput);
                        order.setArea(bd);
                        break;
                    }
                    notValidated = false;

                } catch (NumberFormatException e) {
                    io.print("Please enter a number");
                }
            }
        }
        return order;
    }

    public boolean saveCheck() {
        return io.readString("Would you like to save before exiting? (Y/N)").equalsIgnoreCase("Y");
    }

    public void displayOrderSuccess(Order order) {
        io.print("Order #" + order.getOrderNumber() + " created.");
    }

    public void displayOrderNotFound() {
        io.print("ORDER NOT FOUND");
    }

    public boolean removeCheck() {
        return io.readString("Are you sure you want to delete? (Y/N)").equalsIgnoreCase("Y");
    }

    public void displaySaveSuccess() {
        io.print("Saved successfully!");
    }

    public void displayTrainingSave() {
        io.print("DID NOT SAVE, TRAINING MODE");
    }

    public boolean getSwitchAnswer() {
        io.print("1. TRAINING");
        io.print("2. PRODUCTION");
        int input = io.readInt("Switch mode to: ", 1, 2);

        if (input == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void displayMode(boolean switchModes) {
        if (switchModes) {
            io.print("TRAINING MODE");
        } else {
            io.print("PRODUCTION MODE");
        }

    }

    public void displayTrainingRemove() {
        System.out.println("Training mode - only files in memory were deleted.");
    }

    public void displayRemove() {
        System.out.println("Order successfully deleted.");
    }

}
