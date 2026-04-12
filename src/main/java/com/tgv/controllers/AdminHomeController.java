package com.tgv.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

public class AdminHomeController {

    @FXML
    void goToReclamations(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tgv/views/admin-reclamations.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void goToReponses(ActionEvent event) {
        System.out.println("Page admin réponses à faire après.");
    }

    @FXML
    void retourAccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tgv/views/client-home.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


