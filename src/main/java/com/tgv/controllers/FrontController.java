package com.tgv.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FrontController {

    @FXML
    private StackPane frontContentArea;

    @FXML
    public void openDashboard(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tgv/views/main.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("TGV Delivery - Administration");
            stage.setScene(new Scene(root, 1200, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadAccueilReclamation() {
        loadView("/com/tgv/views/client-accueil-reclamation.fxml");
    }

    public void loadAjouterReclamation() {
        loadView("/com/tgv/views/client-ajouter-reclamation.fxml");
    }

    public void loadMesReclamations() {
        loadView("/com/tgv/views/client-mes-reclamations.fxml");
    }

    public void loadModifierReclamation(int reclamationId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tgv/views/client-modifier-reclamation.fxml"));
            Parent view = loader.load();
            
            ClientModifierReclamationController controller = loader.getController();
            controller.initData(reclamationId, this);

            if (frontContentArea != null) {
                frontContentArea.getChildren().clear();
                frontContentArea.getChildren().add(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadView(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent view = loader.load();

            // Pass a reference of FrontController to the child controllers if they have a setFrontController method
            Object controller = loader.getController();
            if (controller instanceof ClientAccueilReclamationController) {
                ((ClientAccueilReclamationController) controller).setFrontController(this);
            } else if (controller instanceof ClientAjouterReclamationController) {
                ((ClientAjouterReclamationController) controller).setFrontController(this);
            } else if (controller instanceof ClientMesReclamationsController) {
                ((ClientMesReclamationsController) controller).setFrontController(this);
            }

            if (frontContentArea != null) {
                frontContentArea.getChildren().clear();
                frontContentArea.getChildren().add(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load view: " + path);
        }
    }
}
