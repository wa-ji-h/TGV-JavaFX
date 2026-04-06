package com.tgv.services;

import java.util.List;

/**
 * Interface générique CRUD pour tous les services de l'application.
 * Chaque module implémente cette interface pour son entité.
 *
 * @param <T> Le type de l'entité (Commande, Produit, Livreur, etc.)
 */
public interface CrudService<T> {
    List<T> findAll();
    T findById(int id);
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(int id);
}
