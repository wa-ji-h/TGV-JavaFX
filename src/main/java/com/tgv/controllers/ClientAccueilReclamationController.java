package com.tgv.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;

public class ClientAccueilReclamationController {

    private FrontController frontController;

    public void setFrontController(FrontController frontController) {
        this.frontController = frontController;
        System.out.println("ClientAccueilReclamationController: FrontController set to " + frontController);
    }

    @FXML
    public void goToAdd() {
        System.out.println("goToAdd invoked! frontController = " + frontController);
        if (frontController != null) {
            frontController.loadAjouterReclamation();
        } else {
            System.err.println("CRITICAL: frontController is null! Cannot navigate to Ajouter.");
        }
    }

    @FXML
    public void goToMesReclamations() {
        System.out.println("goToMesReclamations invoked! frontController = " + frontController);
        if (frontController != null) {
            frontController.loadMesReclamations();
        } else {
            System.err.println("CRITICAL: frontController is null! Cannot navigate to MesReclamations.");
        }
    }
}
