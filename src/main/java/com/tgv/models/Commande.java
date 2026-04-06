package com.tgv.models;

import java.time.LocalDate;

/**
 * Entité Commande — représente une commande de livraison.
 */
public class Commande {

    private int id;
    private String reference;
    private String statut;       // EN_ATTENTE | EN_COURS | LIVREE | ANNULEE
    private LocalDate dateCommande;
    private double montantTotal;
    private int livreurId;
    private int produitId;

    public Commande() {}

    public Commande(int id, String reference, String statut,
                    LocalDate dateCommande, double montantTotal) {
        this.id = id;
        this.reference = reference;
        this.statut = statut;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
    }

    // ─── Getters & Setters ────────────────────────────────────────
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public int getLivreurId() { return livreurId; }
    public void setLivreurId(int livreurId) { this.livreurId = livreurId; }

    public int getProduitId() { return produitId; }
    public void setProduitId(int produitId) { this.produitId = produitId; }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", ref='" + reference + "', statut='" + statut + "'}";
    }
}
