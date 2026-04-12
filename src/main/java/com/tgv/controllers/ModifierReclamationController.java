package com.tgv.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ModifierReclamationController {

    @FXML
    private TextField sujetField;

    @FXML
    private TextArea messageArea;

    @FXML
    private ComboBox<String> serviceComboBox;

    private Reclamation reclamation;

    private final ReclamationService sr = new ReclamationService();

    @FXML
    public void initialize() {
        serviceComboBox.getItems().addAll("commercial", "technique");
    }

    public void setReclamation(Reclamation r) {
        this.reclamation = r;
        sujetField.setText(r.getSubject());
        messageArea.setText(r.getMessage());
        serviceComboBox.setValue(r.getTypeService());
    }

    @FXML
    void modifier(ActionEvent event) {

        reclamation.setSubject(sujetField.getText());
        reclamation.setMessage(messageArea.getText());
        reclamation.setTypeService(serviceComboBox.getValue());
        reclamation.setUpdatedAt(LocalDateTime.now().toString());

        try {
            sr.modifier(reclamation);

            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/tgv/views/mes-reclamations.fxml")
            );
            sujetField.getScene().setRoot(root);

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/tgv/views/mes-reclamations.fxml")
            );
            sujetField.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

