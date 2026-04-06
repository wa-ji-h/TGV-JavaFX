package com.tgv.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur du Dashboard — affiche les statistiques globales.
 * TODO: Connecter les Labels aux données réelles (CommandeService, ProduitService, LivreurService).
 */
public class DashboardController implements Initializable {

    @FXML private Label lblTotalCommandes;
    @FXML private Label lblTotalProduits;
    @FXML private Label lblTotalLivreurs;
    @FXML private Label lblCommandesLivrees;
    @FXML private TableView<?> tableRecent;
    @FXML private TableColumn<?, ?> colRef;
    @FXML private TableColumn<?, ?> colStatut;
    @FXML private TableColumn<?, ?> colDate;
    @FXML private TableColumn<?, ?> colMontant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Remplacer par les vraies données de la BDD
        lblTotalCommandes.setText("0");
        lblTotalProduits.setText("0");
        lblTotalLivreurs.setText("0");
        lblCommandesLivrees.setText("0");
    }
}
