package com.tgv.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton pour la connexion MySQL.
 * Modifier URL, USER et PASSWORD selon votre configuration locale.
 *
 * Base de données à créer : CREATE DATABASE tgv_db;
 */
public class DBConnection {

    // ============================================================
    //  ⚙️  CONFIGURATION — Modifiez ici selon votre environnement
    // ============================================================
    private static final String URL      = "jdbc:mysql://localhost:3306/tgv_db";
    private static final String USER     = "root";
    private static final String PASSWORD = "";
    // ============================================================

    private static Connection instance;

    private DBConnection() {}

    /**
     * Retourne la connexion unique à la base de données.
     * Crée la connexion si elle n'existe pas encore.
     */
    public static Connection getInstance() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion MySQL établie avec succès.");
            } catch (SQLException e) {
                System.err.println("❌ Erreur de connexion MySQL : " + e.getMessage());
                System.err.println("   Vérifiez que MySQL est démarré et que tgv_db existe.");
                throw new RuntimeException("Impossible de se connecter à la base de données.", e);
            }
        }
        return instance;
    }

    /**
     * Ferme la connexion proprement (à appeler à la fermeture de l'app).
     */
    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                instance = null;
                System.out.println("🔌 Connexion MySQL fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur fermeture connexion : " + e.getMessage());
            }
        }
    }
}
