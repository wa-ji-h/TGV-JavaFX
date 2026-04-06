package com.tgv.controllers;

import com.tgv.models.Commande;
import com.tgv.services.CommandeService;
import javafx.beans.property.SimpleDoubleProperty;
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
 * Contrôleur du module Gestion des Commandes.
 * Gère l'affichage, la recherche et les opérations CRUD sur les commandes.
 */
public class CommandeController implements Initializable {

    @FXML private TableView<Commande>          tableCommandes;
    @FXML private TableColumn<Commande, Integer> colId;
    @FXML private TableColumn<Commande, String>  colReference;
    @FXML private TableColumn<Commande, String>  colStatut;
    @FXML private TableColumn<Commande, String>  colDate;
    @FXML private TableColumn<Commande, Double>  colMontant;
    @FXML private TableColumn<Commande, Void>    colActions;
    @FXML private TextField                      searchField;
    @FXML private ComboBox<String>              filterStatut;

    private final ObservableList<Commande> commandeList = FXCollections.observableArrayList();
    private final CommandeService commandeService = new CommandeService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        setupFilters();
        loadCommandes();
    }

    private void setupColumns() {
        colId.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colReference.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getReference()));
        colStatut.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatut()));
        colDate.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getDateCommande() != null ? d.getValue().getDateCommande().toString() : "-"));
        colMontant.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getMontantTotal()).asObject());
    }

    private void setupFilters() {
        filterStatut.setItems(FXCollections.observableArrayList(
                "Tous", "EN_ATTENTE", "EN_COURS", "LIVREE", "ANNULEE"));
        filterStatut.setValue("Tous");
    }

    @FXML
    public void loadCommandes() {
        commandeList.clear();
        commandeList.addAll(commandeService.findAll());
        tableCommandes.setItems(commandeList);
    }

    @FXML
    public void handleSearch() {
        // TODO: Filtrer commandeList selon searchField.getText()
    }

    @FXML
    public void openAddDialog() {
        // TODO: Ouvrir une boîte de dialogue pour ajouter une commande
        System.out.println("TODO: Dialog Ajout Commande");
    }
}
