package com.tgv.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ContentDisplay;

/**
 * MainController — gère la navigation globale de l'application.
 */
public class MainController implements Initializable {

    // ─── Content + top bar ─────────────────────────────────────────
    @FXML private StackPane contentPane;
    @FXML private Label     topBarTitle;
    @FXML private VBox      sidebar;
    @FXML private ImageView logoImage;
    @FXML private javafx.scene.layout.HBox searchBox;

    // ─── Boutons principaux (Navigation plate) ─────────────────────
    @FXML private Button btnCommandes;
    @FXML private Button btnProduits;
    @FXML private Button btnLivreurs;
    @FXML private Button btnServices;
    @FXML private Button btnReclamations;
    @FXML private Button btnPartenaires;
    @FXML private Button btnUtilisateurs;
    @FXML private Button btnStatistiques;
    @FXML private Button btnConfiguration;

    /** Liste de tous les boutons nav (pour réinitialiser le style actif) */
    private List<Button> allNavButtons;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allNavButtons = List.of(
                btnCommandes, btnProduits, btnLivreurs,
                btnServices, btnReclamations, btnPartenaires,
                btnUtilisateurs, btnStatistiques, btnConfiguration
        );
        // Collapse by default
        setSidebarExpanded(false);
        
        if (topBarTitle != null) {
            topBarTitle.setText("Accueil");
        }
    }

    // ══════════════════════════════════════════════════════════════
    // NAVIGATION PRINCIPALE
    // ══════════════════════════════════════════════════════════════

    @FXML public void showCommandes() {
        loadView("commande.fxml", "Gestion des Commandes");
        setActive(btnCommandes);
    }

    @FXML public void showProduits() {
        loadView("produit.fxml", "Gestion des Produits");
        setActive(btnProduits);
    }

    @FXML public void showLivreurs() {
        loadView("livreur.fxml", "Gestion des Livreurs");
        setActive(btnLivreurs);
    }

    @FXML public void showServices() {
        // Placeholder : on utilise rendez_vous pour le moment
        loadView("rendez_vous.fxml", "Services & Rendez-vous");
        setActive(btnServices);
    }

    @FXML public void showReclamations() {
        loadView("reclamations.fxml", "Réclamations");
        setActive(btnReclamations);
    }

    @FXML public void showPartenaires() {
        loadView("contrat.fxml", "Partenaires");
        setActive(btnPartenaires);
    }

    @FXML public void showUtilisateurs() {
        loadView("utilisateurs.fxml", "Utilisateurs");
        setActive(btnUtilisateurs);
    }

    @FXML public void showStatistiques() {
        // Placeholder
        loadView("dashboard.fxml", "Statistiques & Dashboard");
        setActive(btnStatistiques);
    }

    // ══════════════════════════════════════════════════════════════
    // HELPERS
    // ══════════════════════════════════════════════════════════════

    /** Charge un FXML dans le panneau central et met à jour le titre de la top bar. */
    private void loadView(String fxmlFile, String title) {
        try {
            Node view = FXMLLoader.load(
                    getClass().getResource("/com/tgv/views/" + fxmlFile)
            );
            contentPane.getChildren().setAll(view);
            if (topBarTitle != null) topBarTitle.setText(title);
        } catch (IOException e) {
            System.err.println("❌ Erreur chargement vue : " + fxmlFile);
            e.printStackTrace();
        }
    }

    /** Supprime le style actif de tous les boutons et l'applique au bouton donné. */
    private void setActive(Button active) {
        allNavButtons.forEach(b -> b.getStyleClass().remove("nav-btn-active"));
        active.getStyleClass().add("nav-btn-active");
    }

    /**
     * Naviguer vers le Front-Office.
     * Cette vue remplacera l'écran complet car le Front-Office n'a pas de sidebar.
     */
    @FXML
    public void openFrontOffice(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tgv/views/front.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) contentPane.getScene().getWindow();
            stage.setTitle("TGV Delivery - Front Office");
            stage.setScene(new javafx.scene.Scene(root, 1200, 720));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // ══════════════════════════════════════════════════════════════
    // SIDEBAR HOVER & NAVBAR ACTIONS
    // ══════════════════════════════════════════════════════════════
    
    @FXML
    public void showLogoutMenu(javafx.scene.input.MouseEvent event) {
        javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();
        contextMenu.getStyleClass().add("logout-popup");
        
        javafx.scene.control.MenuItem logoutItem = new javafx.scene.control.MenuItem("Se déconnecter");
        logoutItem.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        logoutItem.setOnAction(e -> openFrontOffice(null));
        contextMenu.getItems().add(logoutItem);
        
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        // Affiche le menu juste au-dessus du nom (décalage à droite de 45px)
        contextMenu.show(source, javafx.geometry.Side.TOP, 45, -5);
    }
    
    @FXML
    public void showNotificationsMenu(javafx.scene.input.MouseEvent event) {
        javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();
        contextMenu.getStyleClass().add("logout-popup");
        
        javafx.scene.control.MenuItem notif1 = new javafx.scene.control.MenuItem("Nouvelle commande #CMD-102");
        javafx.scene.control.MenuItem notif2 = new javafx.scene.control.MenuItem("Livreur Karim est disponible");
        javafx.scene.control.MenuItem notif3 = new javafx.scene.control.MenuItem("Réclamation - Client Yassine");
        
        contextMenu.getItems().addAll(notif1, notif2, notif3);
        
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        contextMenu.show(source, javafx.geometry.Side.BOTTOM, -130, 5);
    }
    
    @FXML
    public void toggleSearchBar() {
        if (searchBox != null) {
            boolean isVisible = searchBox.isVisible();
            searchBox.setVisible(!isVisible);
            searchBox.setManaged(!isVisible);
        }
    }
    
    @FXML
    public void onSidebarEntered() {
        setSidebarExpanded(true);
    }
    
    @FXML
    public void onSidebarExited() {
        setSidebarExpanded(false);
    }
    
    private void setSidebarExpanded(boolean expanded) {
        double targetWidth = expanded ? 242.0 : 70.0;
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(200),
                new KeyValue(sidebar.prefWidthProperty(), targetWidth),
                new KeyValue(sidebar.minWidthProperty(), targetWidth),
                new KeyValue(sidebar.maxWidthProperty(), targetWidth)
            )
        );
        timeline.play();
        
        logoImage.setVisible(expanded);
        logoImage.setManaged(expanded);
        
        ContentDisplay display = expanded ? ContentDisplay.LEFT : ContentDisplay.GRAPHIC_ONLY;
        for (Button btn : allNavButtons) {
            btn.setContentDisplay(display);
        }
    }

    @FXML
    public void toggleFullScreen() {
        if (contentPane.getScene() != null && contentPane.getScene().getWindow() instanceof Stage) {
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        }
    }

    @FXML
    public void toggleTheme() {
        // Simple theme toggle placeholder (will add dark-theme class to root)
        if (contentPane.getScene() != null) {
            javafx.scene.Parent root = contentPane.getScene().getRoot();
            if (root.getStyleClass().contains("dark-theme")) {
                root.getStyleClass().remove("dark-theme");
            } else {
                root.getStyleClass().add("dark-theme");
            }
        }
    }
}

