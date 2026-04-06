package com.tgv.controllers;

import com.tgv.models.Livreur;
import com.tgv.services.LivreurService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur du module Gestion des Livreurs.
 */
public class LivreurController implements Initializable {

    @FXML private TableView<Livreur>            tableLivreurs;
    @FXML private TableColumn<Livreur, Integer> colId;
    @FXML private TableColumn<Livreur, String>  colNom;
    @FXML private TableColumn<Livreur, String>  colPrenom;
    @FXML private TableColumn<Livreur, String>  colTelephone;
    @FXML private TableColumn<Livreur, String>  colVehicule;
    @FXML private TableColumn<Livreur, Boolean> colDisponible;
    @FXML private TableColumn<Livreur, Void>    colActions;
    @FXML private TextField                     searchField;

    private final ObservableList<Livreur> livreurList = FXCollections.observableArrayList();
    private final LivreurService livreurService = new LivreurService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadLivreurs();
    }

    private void setupColumns() {
        colId.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colNom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNom()));
        colPrenom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPrenom()));
        colTelephone.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelephone()));
        colVehicule.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getVehicule()));
        colDisponible.setCellValueFactory(d -> new SimpleBooleanProperty(d.getValue().isDisponible()).asObject());
    }

    @FXML
    public void loadLivreurs() {
        livreurList.clear();
        livreurList.addAll(livreurService.findAll());
        tableLivreurs.setItems(livreurList);
    }

    @FXML
    public void handleSearch() {
        // TODO: Filtrer livreurList selon searchField.getText()
    }

    @FXML
    public void openAddDialog() {
        System.out.println("TODO: Dialog Ajout Livreur");
    }
}
