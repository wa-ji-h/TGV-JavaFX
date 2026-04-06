package com.tgv.controllers;

import com.tgv.models.Produit;
import com.tgv.services.ProduitService;
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
 * Contrôleur du module Gestion des Produits.
 */
public class ProduitController implements Initializable {

    @FXML private TableView<Produit>           tableProduits;
    @FXML private TableColumn<Produit, Integer> colId;
    @FXML private TableColumn<Produit, String>  colNom;
    @FXML private TableColumn<Produit, String>  colCategorie;
    @FXML private TableColumn<Produit, Double>  colPrix;
    @FXML private TableColumn<Produit, Integer> colStock;
    @FXML private TableColumn<Produit, Void>    colActions;
    @FXML private TextField                     searchField;

    private final ObservableList<Produit> produitList = FXCollections.observableArrayList();
    private final ProduitService produitService = new ProduitService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        loadProduits();
    }

    private void setupColumns() {
        colId.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colNom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNom()));
        colCategorie.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategorie()));
        colPrix.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPrix()).asObject());
        colStock.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getStock()).asObject());
    }

    @FXML
    public void loadProduits() {
        produitList.clear();
        produitList.addAll(produitService.findAll());
        tableProduits.setItems(produitList);
    }

    @FXML
    public void handleSearch() {
        // TODO: Filtrer produitList selon searchField.getText()
    }

    @FXML
    public void openAddDialog() {
        System.out.println("TODO: Dialog Ajout Produit");
    }
}
