package com.tgv.controllers;

import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReclamationsController implements Initializable {

    // Table elements
    @FXML private TableView<Reclamation> tableReclamations;
    @FXML private TableColumn<Reclamation, Integer> colId;
    @FXML private TableColumn<Reclamation, String> colSubject;
    @FXML private TableColumn<Reclamation, String> colMessage;
    @FXML private TableColumn<Reclamation, String> colStatus;
    @FXML private TableColumn<Reclamation, String> colDate;
    @FXML private TableColumn<Reclamation, String> colTypeService;
    @FXML private TableColumn<Reclamation, String> colPhone;

    // Filters & Search
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statutFilter;
    @FXML private ComboBox<String> typeFilter;

    // Form elements
    @FXML private TextField sujetField;
    @FXML private TextArea messageArea;
    @FXML private TextField telField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> statutComboBox;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

    private ReclamationService rs = new ReclamationService();
    private ObservableList<Reclamation> masterList = FXCollections.observableArrayList();
    private FilteredList<Reclamation> filteredList;
    private Reclamation selectedReclamation = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Init ComboBoxes
        statutFilter.getItems().addAll("en_attente", "en_cours", "traitée", "refusée");
        typeFilter.getItems().addAll("commercial", "technique");
        
        statutComboBox.getItems().addAll("en_attente", "en_cours", "traitée", "refusée");
        typeComboBox.getItems().addAll("commercial", "technique");
        statutComboBox.setValue("en_attente"); // Default

        // Init Columns
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colSubject.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubject()));
        colMessage.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMessage()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCreatedAt()));
        colTypeService.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTypeService()));
        colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

        chargerDonnees();
    }

    private void chargerDonnees() {
        try {
            List<Reclamation> lst = rs.afficher();
            masterList.setAll(lst);
            filteredList = new FilteredList<>(masterList, b -> true);
            tableReclamations.setItems(filteredList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur BD", "Impossible de charger les données: " + e.getMessage());
        }
    }

    @FXML
    void handleAjouter(ActionEvent event) {
        if (!validerSaisie()) return;

        Reclamation r = new Reclamation(
                sujetField.getText().trim(),
                messageArea.getText().trim(),
                statutComboBox.getValue() != null ? statutComboBox.getValue() : "en_attente",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                null,
                typeComboBox.getValue()
        );
        r.setPhone(telField.getText().trim());

        try {
            rs.ajouter(r);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès.");
            clearForm();
            chargerDonnees();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur BD", "Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        if (selectedReclamation == null) return;
        if (!validerSaisie()) return;

        selectedReclamation.setSubject(sujetField.getText().trim());
        selectedReclamation.setMessage(messageArea.getText().trim());
        selectedReclamation.setPhone(telField.getText().trim());
        selectedReclamation.setTypeService(typeComboBox.getValue());
        selectedReclamation.setStatus(statutComboBox.getValue() != null ? statutComboBox.getValue() : "en_attente");
        selectedReclamation.setUpdatedAt(LocalDateTime.now().toString());

        try {
            rs.modifier(selectedReclamation);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation modifiée avec succès.");
            clearForm();
            chargerDonnees();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur BD", "Erreur lors de la modification: " + e.getMessage());
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        if (selectedReclamation == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation de suppression");
        confirm.setHeaderText(null);
        confirm.setContentText("Êtes-vous sûr de vouloir supprimer cette réclamation ?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                rs.supprimer(selectedReclamation.getId());
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation supprimée.");
                clearForm();
                chargerDonnees();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur BD", "Erreur de suppression: " + e.getMessage());
            }
        }
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        Reclamation selection = tableReclamations.getSelectionModel().getSelectedItem();
        if (selection != null) {
            selectedReclamation = selection;
            sujetField.setText(selection.getSubject());
            messageArea.setText(selection.getMessage());
            telField.setText(selection.getPhone());
            typeComboBox.setValue(selection.getTypeService());
            statutComboBox.setValue(selection.getStatus());

            btnAjouter.setDisable(true);
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
        }
    }

    @FXML
    void clearForm() {
        selectedReclamation = null;
        sujetField.clear();
        messageArea.clear();
        telField.clear();
        typeComboBox.setValue(null);
        statutComboBox.setValue("en_attente");

        tableReclamations.getSelectionModel().clearSelection();

        btnAjouter.setDisable(false);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    private boolean validerSaisie() {
        String sujet = sujetField.getText().trim();
        String message = messageArea.getText().trim();
        String tel = telField.getText().trim();
        String type = typeComboBox.getValue();

        if (sujet.isEmpty() || !sujet.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur Validation", "Le sujet est obligatoire et doit contenir uniquement des lettres.");
            return false;
        }

        if (message.isEmpty() || message.length() < 10) {
            showAlert(Alert.AlertType.ERROR, "Erreur Validation", "Le message est obligatoire et doit comporter au moins 10 caractères.");
            return false;
        }

        if (tel.isEmpty() || !tel.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur Validation", "Le téléphone est obligatoire et doit être composé de chiffres uniquement.");
            return false;
        }

        if (type == null || type.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur Validation", "Veuillez choisir un type de service.");
            return false;
        }

        return true;
    }

    @FXML
    void handleSearch() {
        appliquerFiltres();
    }

    @FXML
    void handleFilter() {
        appliquerFiltres();
    }

    @FXML
    void handleReset() {
        searchField.clear();
        statutFilter.setValue(null);
        typeFilter.setValue(null);
        filteredList.setPredicate(b -> true);
    }

    private void appliquerFiltres() {
        String keyword = searchField.getText() != null ? searchField.getText().toLowerCase() : "";
        String statut = statutFilter.getValue();
        String type = typeFilter.getValue();

        filteredList.setPredicate(r -> {
            boolean matchRecherche = keyword.isEmpty() || 
                                     (r.getSubject() != null && r.getSubject().toLowerCase().contains(keyword)) ||
                                     (r.getMessage() != null && r.getMessage().toLowerCase().contains(keyword)) ||
                                     (r.getStatus() != null && r.getStatus().toLowerCase().contains(keyword)) ||
                                     (r.getTypeService() != null && r.getTypeService().toLowerCase().contains(keyword)) ||
                                     (r.getPhone() != null && r.getPhone().toLowerCase().contains(keyword));

            boolean matchStatut = (statut == null || statut.isEmpty()) || 
                                  (r.getStatus() != null && r.getStatus().equalsIgnoreCase(statut));

            boolean matchType = (type == null || type.isEmpty()) || 
                                (r.getTypeService() != null && r.getTypeService().equalsIgnoreCase(type));

            return matchRecherche && matchStatut && matchType;
        });
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
