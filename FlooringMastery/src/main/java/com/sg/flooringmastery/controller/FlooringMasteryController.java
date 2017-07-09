/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Material;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.service.FlooringMasteryMaterialService;
import com.sg.flooringmastery.service.FlooringMasteryOrderFileService;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.util.ArrayList;
import com.sg.flooringmastery.service.FlooringMasteryOrderCalcService;
import com.sg.flooringmastery.service.FlooringMasteryStateService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author chandler
 */
public class FlooringMasteryController {

    FlooringMasteryView view;
    FlooringMasteryService service;
    FlooringMasteryOrderFileService orderFileService;
    FlooringMasteryOrderCalcService orderCalcService;
    FlooringMasteryStateService stateService;
    FlooringMasteryMaterialService matService;

    public FlooringMasteryController(FlooringMasteryView view,
            FlooringMasteryService service, FlooringMasteryOrderFileService orderFileService,
            FlooringMasteryOrderCalcService orderCalcService, FlooringMasteryStateService stateService,
            FlooringMasteryMaterialService matService) {
        this.view = view;
        this.service = service;
        this.orderFileService = orderFileService;
        this.orderCalcService = orderCalcService;
        this.stateService = stateService;
        this.matService = matService;
    }

    public void run() {

        boolean keepGoing = true;
        boolean mode = false;
        try {
            while (keepGoing) {

                int menuSelection = view.printMenuGetSelection();

                switch (menuSelection) {

                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder(mode);
                        break;
                    case 5:
                        save(mode);
                        break;
                    case 6:
                        saveCheck(mode);
                        keepGoing = false;
                        break;
                    case 7:
                        mode = switchModes();
                        view.displayMode(mode);
                        break;
                }
            }

        } catch (FlooringMasteryPersistenceException e) {

        }

    }

    private String displayOrders() throws FlooringMasteryPersistenceException {
        String orderDate = view.getOrderDate();
        List<Order> combinedList = service.combineLists(orderDate);
        view.displayOrders(combinedList);
        return orderDate;
    }

    private void addOrder() throws FlooringMasteryPersistenceException {
        List<State> stateList = stateService.loadStates();
        List<Material> matList = matService.loadMaterials();

        Order order = new Order();

        view.getOrderData(order);
        Integer matNum = view.getMat(matList);
        Integer stateNum = view.getState(stateList);

        order = orderCalcService.calcState(order, stateNum, stateList);
        orderCalcService.calcMats(order, matNum, matList);

        orderCalcService.calcCosts(order);
        view.displayOrderInfo(order);
        boolean correct = view.checkOrderCorrect();
        if (correct) {
            orderCalcService.calcOrderNumber(order);
            view.displayOrderSuccess(order);
            service.addOrderToList(order);
        }
    }

    private void editOrder() throws FlooringMasteryPersistenceException {
        String orderDate = displayOrders();
        Integer orderNumber = view.getOrderNumber(); //will null exception if wrong number chosen //also fix temp stuff/vestigial stuff
        List<Order> combinedList = service.combineLists(orderDate);
        List<Order> TEMPlist = service.getOrderList();
        Order TEMPorder = new Order();
        try {
            TEMPorder = service.getOrder(orderNumber, TEMPlist);
        } catch (Exception e) {
            TEMPlist = orderFileService.loadOrderFile(orderDate);
            TEMPorder = service.getOrder(orderNumber, TEMPlist);
        }
        List<Order> list = service.getOneOrderListFromBothLists(TEMPorder, orderDate);
        Order order = service.getOrder(orderNumber, list);
        List<State> stateList = stateService.loadStates();
        List<Material> matList = matService.loadMaterials();
        view.editOrder(order, stateList, matList);
        view.editState(order, stateList);
        view.editMat(order, matList);
        orderCalcService.calcCosts(order);
        service.editOrder(orderDate, list, order);

    }

    private void removeOrder(boolean mode) throws FlooringMasteryPersistenceException {

        String orderDate = displayOrders();
        Integer orderNumber = view.getOrderNumber();
        boolean userSure = view.removeCheck();

        service.removeOrder(orderDate, orderNumber, userSure, mode);

        if (mode) {
            view.displayTrainingRemove();
        } else {
            view.displayRemove();
        }
    }

    private void save(boolean mode) throws FlooringMasteryPersistenceException {
        List<Order> list = service.getOrderList();

        if (mode) {
            view.displayTrainingSave();
        } else {
            try {
                while (!list.isEmpty()) {
                    List<Order> tempList = orderFileService.splitList(list);
                    orderFileService.writeOrderFile(tempList);

                }
                view.displaySaveSuccess();
            } catch (NullPointerException e) {

            }
        }
    }

    private void saveCheck(boolean mode) throws FlooringMasteryPersistenceException {
        boolean toSave = view.saveCheck();
        if (toSave) {
            save(mode);
        }
    }

    private boolean switchModes() {
        boolean switchMode = view.getSwitchAnswer();
        orderFileService.switchMode(switchMode);
        return switchMode;
    }
}
