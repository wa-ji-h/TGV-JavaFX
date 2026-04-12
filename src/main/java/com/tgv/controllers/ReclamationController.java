package com.tgv.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReclamationController {

    @FXML
    private TextField sujetField;

    @FXML
    private TextArea messageArea;

    @FXML
    private TextField telField;

    @FXML
    private ComboBox<String> serviceComboBox;

    ReclamationService sr = new ReclamationService();

    @FXML
    public void initialize() {
        serviceComboBox.getItems().addAll("commercial", "technique");
    }

    @FXML
    void ajouterReclamation(ActionEvent event) {

        String sujet = sujetField.getText().trim();
        String message = messageArea.getText().trim();
        String tel = telField.getText().trim();
        String service = serviceComboBox.getValue();

        if (sujet.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le sujet est obligatoire.");
            return;
        }

        if (!sujet.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le sujet doit contenir uniquement des lettres.");
            return;
        }

        if (message.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le message est obligatoire.");
            return;
        }

        if (message.length() < 10) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le message doit contenir au moins 10 caractères.");
            return;
        }

        if (tel.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de téléphone est obligatoire.");
            return;
        }

        if (!tel.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de téléphone doit contenir uniquement des chiffres.");
            return;
        }

        if (service == null || service.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir un type de service.");
            return;
        }

        String now = LocalDateTime.now().toString();

        Integer userIdTemporaire = null;

        Reclamation r = new Reclamation(
                sujet,
                message,
                "en_attente",
                now,
                now,
                userIdTemporaire,
                service
        );
        r.setPhone(tel);

        try {
            sr.ajouter(r);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès.");

            sujetField.clear();
            messageArea.clear();
            telField.clear();
            serviceComboBox.setValue(null);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur BD : " + e.getMessage());
        }
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/tgv/views/client-home.fxml")
            );
            sujetField.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

