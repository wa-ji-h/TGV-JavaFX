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
import java.util.List;

public class ClientModifierReclamationController {

    private FrontController frontController;
    private Reclamation currentReclamation;

    @FXML private TextField sujetField;
    @FXML private TextArea messageArea;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> serviceComboBox;
    @FXML private Label errorLabel;
    @FXML private Button modifierButton;

    private ReclamationService rs = new ReclamationService();

    @FXML
    public void initialize() {
        serviceComboBox.getItems().addAll("Commercial", "Technique", "Livraison", "Autre");
        errorLabel.setVisible(false);
    }

    public void initData(int id, FrontController fc) {
        this.frontController = fc;

        try {
            List<Reclamation> list = rs.afficher();
            currentReclamation = list.stream().filter(r -> r.getId() == id).findFirst().orElse(null);

            if (currentReclamation != null) {
                if (!"en_attente".equals(currentReclamation.getStatus())) {
                    showError("Cette réclamation ne peut plus être modifiée car elle n'est plus en attente.");
                    sujetField.setDisable(true);
                    messageArea.setDisable(true);
                    phoneField.setDisable(true);
                    serviceComboBox.setDisable(true);
                    modifierButton.setDisable(true);
                } else {
                    sujetField.setText(currentReclamation.getSubject());
                    messageArea.setText(currentReclamation.getMessage());
                    phoneField.setText(currentReclamation.getPhone());
                    serviceComboBox.setValue(currentReclamation.getTypeService());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        errorLabel.setVisible(false);

        if (currentReclamation == null || !"en_attente".equals(currentReclamation.getStatus())) {
            showError("Modification interdite.");
            return;
        }

        String subject = sujetField.getText();
        String message = messageArea.getText();
        String phone = phoneField.getText();
        String service = serviceComboBox.getValue();

        // Validations
        if (subject == null || subject.trim().isEmpty() || !subject.matches("^[a-zA-Z\\s]+$")) {
            showError("Le sujet est obligatoire et doit contenir uniquement des lettres.");
            return;
        }

        if (message == null || message.trim().length() < 10) {
            showError("Le message est obligatoire (minimum 10 caractères).");
            return;
        }

        if (phone == null || phone.trim().isEmpty() || !phone.matches("^\\d+$")) {
            showError("Le téléphone est obligatoire et doit contenir uniquement des chiffres.");
            return;
        }

        if (service == null || service.trim().isEmpty()) {
            showError("Le type de service est obligatoire.");
            return;
        }

        currentReclamation.setSubject(subject);
        currentReclamation.setMessage(message);
        currentReclamation.setPhone(phone);
        currentReclamation.setTypeService(service);
        currentReclamation.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        try {
            rs.modifier(currentReclamation);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation modifiée avec succès !");
            alert.showAndWait();
            
            if (frontController != null) {
                frontController.loadMesReclamations();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur lors de la modification.");
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        if (frontController != null) {
            frontController.loadMesReclamations();
        }
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(true);
    }
}
