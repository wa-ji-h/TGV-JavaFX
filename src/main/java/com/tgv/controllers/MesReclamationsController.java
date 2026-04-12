package com.tgv.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MesReclamationsController {

    @FXML
    private TableView<Reclamation> tableReclamation;

    @FXML
    private TableColumn<Reclamation, Integer> colId;

    @FXML
    private TableColumn<Reclamation, String> colSubject;

    @FXML
    private TableColumn<Reclamation, String> colMessage;

    @FXML
    private TableColumn<Reclamation, String> colStatus;

    @FXML
    private TableColumn<Reclamation, String> colDate;

    @FXML
    private TableColumn<Reclamation, String> colTypeService;

    @FXML
    private TableColumn<Reclamation, String> colPhone;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> statutFilter;

    @FXML
    private ComboBox<String> typeFilter;

    private final ReclamationService sr = new ReclamationService();

    private ObservableList<Reclamation> masterList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colTypeService.setCellValueFactory(new PropertyValueFactory<>("typeService"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        statutFilter.getItems().addAll("en_attente", "traitée", "refusée");
        typeFilter.getItems().addAll("commercial", "technique");

        chargerTable();
        activerRecherche();
    }

    private void chargerTable() {
        try {
            List<Reclamation> list = sr.afficher();
            masterList = FXCollections.observableArrayList(list);
            tableReclamation.setItems(masterList);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void activerRecherche() {
        FilteredList<Reclamation> filteredList = new FilteredList<>(masterList, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(reclamation -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keyword = newValue.toLowerCase();

                if (reclamation.getSubject() != null && reclamation.getSubject().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (reclamation.getMessage() != null && reclamation.getMessage().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (reclamation.getStatus() != null && reclamation.getStatus().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (reclamation.getTypeService() != null && reclamation.getTypeService().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (reclamation.getPhone() != null && reclamation.getPhone().toLowerCase().contains(keyword)) {
                    return true;
                }

                return false;
            });
        });

        tableReclamation.setItems(filteredList);
    }

    @FXML
    void filtrer() {
        String statut = statutFilter.getValue();
        String type = typeFilter.getValue();

        FilteredList<Reclamation> filtered = new FilteredList<>(masterList, b -> true);

        filtered.setPredicate(reclamation -> {
            boolean matchStatut = true;
            boolean matchType = true;

            if (statut != null && !statut.isEmpty()) {
                matchStatut = reclamation.getStatus() != null &&
                        reclamation.getStatus().equalsIgnoreCase(statut);
            }

            if (type != null && !type.isEmpty()) {
                matchType = reclamation.getTypeService() != null &&
                        reclamation.getTypeService().equalsIgnoreCase(type);
            }

            return matchStatut && matchType;
        });

        tableReclamation.setItems(filtered);
    }

    @FXML
    void resetFilter() {
        statutFilter.setValue(null);
        typeFilter.setValue(null);
        searchField.clear();
        tableReclamation.setItems(masterList);
    }

    @FXML
    void modifierReclamation(ActionEvent event) {
        Reclamation selected = tableReclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une réclamation.");
            return;
        }

        if (!"en_attente".equalsIgnoreCase(selected.getStatus())) {
            showAlert("Refus", "Cette réclamation ne peut plus être modifiée.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/tgv/views/modifier-reclamation.fxml")
            );

            Parent root = loader.load();

            ModifierReclamationController controller = loader.getController();
            controller.setReclamation(selected);

            tableReclamation.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void supprimerReclamation(ActionEvent event) {
        Reclamation selected = tableReclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une réclamation.");
            return;
        }

        if (!"en_attente".equalsIgnoreCase(selected.getStatus())) {
            showAlert("Refus", "Cette réclamation ne peut plus être supprimée.");
            return;
        }

        try {
            sr.supprimer(selected.getId());
            chargerTable();
            activerRecherche();
            showAlert("Succès", "Réclamation supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void retourAccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tgv/views/client-home.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

