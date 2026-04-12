package com.tgv.controllers;

import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientAjouterReclamationController {

    private FrontController frontController;

    @FXML private TextField sujetField;
    @FXML private TextArea messageArea;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> serviceComboBox;
    @FXML private Label errorLabel;

    private ReclamationService rs = new ReclamationService();

    @FXML
    public void initialize() {
        serviceComboBox.getItems().addAll("Commercial", "Technique");
        errorLabel.setVisible(false);
    }

    public void setFrontController(FrontController frontController) {
        this.frontController = frontController;
    }

    @FXML
    void handleAjouter(ActionEvent event) {

        String subject = sujetField.getText();
        String message = messageArea.getText();
        String phone = phoneField.getText();
        String service = serviceComboBox.getValue();

        // Validations
        if (subject == null || subject.trim().isEmpty() || !subject.matches("^[a-zA-Z\\s]+$")) {
            showError("Erreur de validation", "Le sujet est obligatoire et doit contenir uniquement des lettres.");
            return;
        }

        if (message == null || message.trim().length() < 10) {
            showError("Erreur de validation", "Le message est obligatoire et doit contenir au moins 10 caractères.");
            return;
        }

        if (phone == null || phone.trim().isEmpty() || !phone.matches("^\\d+$")) {
            showError("Erreur de validation", "Le numéro de téléphone est obligatoire et doit contenir uniquement des chiffres.");
            return;
        }

        if (service == null || service.trim().isEmpty()) {
            showError("Erreur de validation", "Le type de service est obligatoire.");
            return;
        }

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Reclamation r = new Reclamation();
        r.setSubject(subject);
        r.setMessage(message);
        r.setPhone(phone);
        r.setTypeService(service);
        r.setStatus("en_attente");
        r.setCreatedAt(now);
        r.setUpdatedAt(now);

        try {
            rs.ajouter(r);
            showSuccess("Succès", "Réclamation ajoutée avec succès");
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur Glogale", "Erreur lors de l'enregistrement dans la base de données.");
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        if (frontController != null) {
            frontController.loadAccueilReclamation();
        }
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #ffffff;");
        
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; -fx-text-fill: #334155; -fx-padding: 15px 10px; -fx-line-spacing: 4px;");
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8px 25px; -fx-background-radius: 4px; -fx-cursor: hand;");
        
        javafx.scene.shape.SVGPath icon = new javafx.scene.shape.SVGPath();
        icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm5 13.59L15.59 17 12 13.41 8.41 17 7 15.59 10.59 12 7 8.41 8.41 7 12 10.59 15.59 7 17 8.41 13.41 12 17 15.59z");
        icon.setFill(javafx.scene.paint.Color.rgb(239, 68, 68));
        icon.setScaleX(2.0);
        icon.setScaleY(2.0);
        
        javafx.scene.layout.StackPane iconPane = new javafx.scene.layout.StackPane(icon);
        iconPane.setPadding(new javafx.geometry.Insets(20, 20, 20, 30));
        dialogPane.setGraphic(iconPane);

        alert.showAndWait();
    }

    private void showSuccess(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #ffffff;");
        
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; -fx-text-fill: #334155; -fx-padding: 15px 10px; -fx-line-spacing: 4px;");
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8px 25px; -fx-background-radius: 4px; -fx-cursor: hand;");
        
        javafx.scene.shape.SVGPath icon = new javafx.scene.shape.SVGPath();
        icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z");
        icon.setFill(javafx.scene.paint.Color.rgb(16, 185, 129));
        icon.setScaleX(2.0);
        icon.setScaleY(2.0);
        
        javafx.scene.layout.StackPane iconPane = new javafx.scene.layout.StackPane(icon);
        iconPane.setPadding(new javafx.geometry.Insets(20, 20, 20, 30));
        dialogPane.setGraphic(iconPane);

        alert.showAndWait();
    }

    private void clearForm() {
        sujetField.clear();
        messageArea.clear();
        phoneField.clear();
        serviceComboBox.getSelectionModel().clearSelection();
    }
}
