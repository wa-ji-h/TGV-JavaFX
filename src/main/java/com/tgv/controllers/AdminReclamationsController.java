package com.tgv.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.tgv.models.Reclamation;
import com.tgv.services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AdminReclamationsController {

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
    private TableColumn<Reclamation, Integer> colUserId;

    @FXML
    private TableColumn<Reclamation, String> colDejaRepondu;

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
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colDejaRepondu.setCellValueFactory(new PropertyValueFactory<>("dejaRepondu"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        statutFilter.getItems().addAll("en_attente", "en_cours", "traitée", "refusée");
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

                if (reclamation.getSubject() != null && reclamation.getSubject().toLowerCase().contains(keyword)) return true;
                if (reclamation.getMessage() != null && reclamation.getMessage().toLowerCase().contains(keyword)) return true;
                if (reclamation.getStatus() != null && reclamation.getStatus().toLowerCase().contains(keyword)) return true;
                if (reclamation.getTypeService() != null && reclamation.getTypeService().toLowerCase().contains(keyword)) return true;
                if (reclamation.getUserId() != null && reclamation.getUserId().toString().contains(keyword)) return true;

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
    void changerStatut(ActionEvent event) {
        Reclamation selected = tableReclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une réclamation.");
            return;
        }

        List<String> choix = Arrays.asList("en_attente", "en_cours", "traitée", "refusée");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(selected.getStatus(), choix);
        dialog.setTitle("Changer statut");
        dialog.setHeaderText(null);
        dialog.setContentText("Nouveau statut :");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            selected.setStatus(result.get());
            try {
                sr.modifier(selected);
                chargerTable();
                activerRecherche();
                showAlert("Succès", "Statut modifié avec succès.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void repondreReclamation(ActionEvent event) {
        Reclamation selected = tableReclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une réclamation.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tgv/views/admin-ajouter-reponse.fxml"));
            Parent root = loader.load();

            AdminReponseController controller = loader.getController();
            controller.setReclamation(selected);

            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert("Erreur", "Impossible d'ouvrir la page de réponse.");
        }
    }

    @FXML
    void supprimerReclamation(ActionEvent event) {
        Reclamation selected = tableReclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner une réclamation.");
            return;
        }

        try {
            sr.supprimer(selected.getId());
            chargerTable();
            activerRecherche();
            showAlert("Succès", "Réclamation supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void retourAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tgv/views/admin-home.fxml"));
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
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


