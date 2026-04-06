package com.tgv.services;

import com.tgv.models.Produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Service CRUD pour les Produits.
 */
public class ProduitService implements CrudService<Produit> {

    @Override
    public List<Produit> findAll() {
        List<Produit> produits = new ArrayList<>();
        // TODO: SELECT * FROM produits
        return produits;
    }

    @Override
    public Produit findById(int id) {
        // TODO: SELECT * FROM produits WHERE id = ?
        return null;
    }

    @Override
    public boolean save(Produit produit) {
        // TODO: INSERT INTO produits (nom, description, prix, stock, categorie) VALUES (?, ?, ?, ?, ?)
        return false;
    }

    @Override
    public boolean update(Produit produit) {
        // TODO: UPDATE produits SET nom=?, prix=?, stock=? WHERE id=?
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO: DELETE FROM produits WHERE id=?
        return false;
    }
}
