package com.tgv.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import com.tgv.models.Reclamation;
import com.tgv.models.Reponse;
import com.tgv.services.ReclamationService;
import com.tgv.services.ReponseService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdminReponseController {

    @FXML
    private Label labelIdReclamation;

    @FXML
    private Label labelSujet;

    @FXML
    private Label labelMessage;

    @FXML
    private TextArea contentField;

    private Reclamation reclamationSelectionnee;

    private final ReponseService serviceReponse = new ReponseService();
    private final ReclamationService serviceReclamation = new ReclamationService();

    public void setReclamation(Reclamation reclamation) {
        this.reclamationSelectionnee = reclamation;

        labelIdReclamation.setText("Réclamation ID : " + reclamation.getId());
        labelSujet.setText("Sujet : " + reclamation.getSubject());
        labelMessage.setText("Message : " + reclamation.getMessage());
    }

    @FXML
    void ajouterReponse(ActionEvent event) {
        if (reclamationSelectionnee == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune réclamation sélectionnée.");
            return;
        }

        String contenu = contentField.getText();

        if (contenu == null || contenu.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Le contenu de la réponse est obligatoire.");
            return;
        }

        String now = LocalDateTime.now().toString();

        Reponse r = new Reponse();
        r.setContent(contenu.trim());
        r.setAuthor("Admin");
        r.setCreatedAt(now);
        r.setUpdatedAt(now);
        r.setReclamationId(reclamationSelectionnee.getId());

        try {
            serviceReponse.ajouter(r);

            reclamationSelectionnee.setStatus("traitée");
            reclamationSelectionnee.setUpdatedAt(now);
            serviceReclamation.modifier(reclamationSelectionnee);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réponse ajoutée avec succès.");

            Parent root = FXMLLoader.load(getClass().getResource("/com/tgv/views/admin-reclamations.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter la réponse.");
        }
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tgv/views/admin-reclamations.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


