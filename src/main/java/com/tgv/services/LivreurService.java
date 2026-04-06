package com.tgv.services;

import com.tgv.models.Livreur;

import java.util.ArrayList;
import java.util.List;

/**
 * Service CRUD pour les Livreurs.
 */
public class LivreurService implements CrudService<Livreur> {

    @Override
    public List<Livreur> findAll() {
        List<Livreur> livreurs = new ArrayList<>();
        // TODO: SELECT * FROM livreurs
        return livreurs;
    }

    @Override
    public Livreur findById(int id) {
        // TODO: SELECT * FROM livreurs WHERE id = ?
        return null;
    }

    @Override
    public boolean save(Livreur livreur) {
        // TODO: INSERT INTO livreurs (nom, prenom, telephone, vehicule, disponible, zone) VALUES (?, ?, ?, ?, ?, ?)
        return false;
    }

    @Override
    public boolean update(Livreur livreur) {
        // TODO: UPDATE livreurs SET disponible=?, zone=? WHERE id=?
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO: DELETE FROM livreurs WHERE id=?
        return false;
    }
}
