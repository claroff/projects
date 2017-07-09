/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author chandler
 */
public class App {

    public static void main(String[] args) {
//        UserIO myIo = new UserIOConsoleImpl();
//        FlooringMasteryView myView = new FlooringMasteryView(myIo);
//        FlooringMasteryDao myDao = new FlooringMasteryDaoFileImpl();
//        FlooringMasteryService myService = new FlooringMasteryServiceFileImpl(myDao);
//
//        FlooringMasteryStateDao myStateDao = new FlooringMasteryStateDaoFileImpl();
//        FlooringMasteryStateService myStateService = new FlooringMasteryStateServiceFileImpl(myStateDao);
//        FlooringMasteryMaterialDao myMaterialDao = new FlooringMasteryMaterialDaoFileImpl();
//        FlooringMasteryMaterialService myMaterialService = new FlooringMasteryMaterialServiceFileImpl(myMaterialDao);
//
//        FlooringMasteryOrderFileDao myOrderFileDao = new FlooringMasteryOrderFileDaoFileImpl();
//        FlooringMasteryOrderCalcService myOrderCalcService = new FlooringMasteryOrderCalcServiceFileImpl();
//        FlooringMasteryOrderFileService myOrderFileService = new FlooringMasteryOrderFileServiceFileImpl(myOrderFileDao, myOrderCalcService, myDao);
//
//        FlooringMasteryController controller = new FlooringMasteryController(myView, myService, myOrderFileService, myOrderCalcService, myStateService, myMaterialService);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);

        controller.run();
    }

}
