package com.tgv.controllers;

import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientMesReclamationsController {

    private FrontController frontController;

    @FXML private TableView<Reclamation> reclamationsTable;
    @FXML private TableColumn<Reclamation, Integer> colId;
    @FXML private TableColumn<Reclamation, String> colSujet;
    @FXML private TableColumn<Reclamation, String> colMessage;
    @FXML private TableColumn<Reclamation, String> colStatut;
    @FXML private TableColumn<Reclamation, String> colDate;
    @FXML private TableColumn<Reclamation, String> colService;
    @FXML private TableColumn<Reclamation, String> colTelephone;
    @FXML private TableColumn<Reclamation, String> colRepondu;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterStatut;
    @FXML private ComboBox<String> filterService;

    private ReclamationService rs = new ReclamationService();
    private ObservableList<Reclamation> allReclamations = FXCollections.observableArrayList();

    public void setFrontController(FrontController frontController) {
        this.frontController = frontController;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSujet.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colService.setCellValueFactory(new PropertyValueFactory<>("typeService"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colRepondu.setCellValueFactory(new PropertyValueFactory<>("dejaRepondu"));

        filterStatut.getItems().addAll("Toutes", "en_attente", "traitée");
        filterStatut.setValue("Toutes");

        filterService.getItems().addAll("Tous", "Commercial", "Technique", "Livraison", "Autre");
        filterService.setValue("Tous");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData());
        filterStatut.valueProperty().addListener((observable, oldValue, newValue) -> filterData());
        filterService.valueProperty().addListener((observable, oldValue, newValue) -> filterData());

        loadData();
    }

    private void loadData() {
        try {
            List<Reclamation> list = rs.afficher();
            allReclamations.setAll(list);
            filterData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterData() {
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String selectedStatut = filterStatut.getValue();
        String selectedService = filterService.getValue();

        List<Reclamation> filtered = allReclamations.stream()
            .filter(r -> {
                boolean matchesSearch = r.getSubject() != null && r.getSubject().toLowerCase().contains(searchText) ||
                                        r.getMessage() != null && r.getMessage().toLowerCase().contains(searchText) ||
                                        r.getStatus() != null && r.getStatus().toLowerCase().contains(searchText) ||
                                        (r.getTypeService() != null && r.getTypeService().toLowerCase().contains(searchText));
                
                boolean matchesStatut = selectedStatut.equals("Toutes") || (r.getStatus() != null && r.getStatus().equals(selectedStatut));
                boolean matchesService = selectedService.equals("Tous") || (r.getTypeService() != null && r.getTypeService().equals(selectedService));
                
                return matchesSearch && matchesStatut && matchesService;
            })
            .collect(Collectors.toList());

        reclamationsTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    void resetFiltre(ActionEvent event) {
        searchField.clear();
        filterStatut.setValue("Toutes");
        filterService.setValue("Tous");
    }

    @FXML
    void handleModifier(ActionEvent event) {
        Reclamation selected = reclamationsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Veuillez sélectionner une réclamation à modifier.");
            return;
        }

        if (frontController != null) {
            frontController.loadModifierReclamation(selected.getId());
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        Reclamation selected = reclamationsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Veuillez sélectionner une réclamation à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réclamation ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                rs.supprimer(selected.getId());
                loadData();
                showSuccess("Réclamation supprimée.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        if (frontController != null) {
            frontController.loadAccueilReclamation();
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
