package com.tgv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TGV Delivery — Point d'entrée principal de l'application JavaFX.
 * Lance la fenêtre principale avec le layout FXML de navigation.
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/tgv/views/main.fxml")
        );
        Scene scene = new Scene(loader.load(), 1200, 720);

        stage.setTitle("TGV Delivery — Système de Gestion");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
