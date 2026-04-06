package com.tgv.services;

import com.tgv.models.Commande;
import com.tgv.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service CRUD pour les Commandes.
 * Décommentez les blocs SQL et adaptez les noms de colonnes à votre BDD.
 */
public class CommandeService implements CrudService<Commande> {

    @Override
    public List<Commande> findAll() {
        List<Commande> commandes = new ArrayList<>();
        // TODO: Décommentez et adaptez selon votre schéma BDD
        // try (Connection conn = DBConnection.getInstance();
        //      Statement stmt = conn.createStatement();
        //      ResultSet rs = stmt.executeQuery("SELECT * FROM commandes")) {
        //     while (rs.next()) commandes.add(mapRow(rs));
        // } catch (SQLException e) { e.printStackTrace(); }
        return commandes;
    }

    @Override
    public Commande findById(int id) {
        // TODO: SELECT * FROM commandes WHERE id = ?
        return null;
    }

    @Override
    public boolean save(Commande commande) {
        // TODO: INSERT INTO commandes (reference, statut, date_commande, montant_total, livreur_id, produit_id)
        //       VALUES (?, ?, ?, ?, ?, ?)
        return false;
    }

    @Override
    public boolean update(Commande commande) {
        // TODO: UPDATE commandes SET statut=?, montant_total=? WHERE id=?
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO: DELETE FROM commandes WHERE id=?
        return false;
    }

    // Helper: convertit un ResultSet en objet Commande
    // private Commande mapRow(ResultSet rs) throws SQLException {
    //     Commande c = new Commande();
    //     c.setId(rs.getInt("id"));
    //     c.setReference(rs.getString("reference"));
    //     c.setStatut(rs.getString("statut"));
    //     c.setDateCommande(rs.getDate("date_commande").toLocalDate());
    //     c.setMontantTotal(rs.getDouble("montant_total"));
    //     c.setLivreurId(rs.getInt("livreur_id"));
    //     c.setProduitId(rs.getInt("produit_id"));
    //     return c;
    // }
}
