package com.tgv.models;

/**
 * Entité Livreur — représente un livreur de l'équipe TGV.
 */
public class Livreur {

    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String vehicule;
    private boolean disponible;
    private String zone;

    public Livreur() {}

    public Livreur(int id, String nom, String prenom, String telephone,
                   String vehicule, boolean disponible, String zone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.vehicule = vehicule;
        this.disponible = disponible;
        this.zone = zone;
    }

    // ─── Getters & Setters ────────────────────────────────────────
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getVehicule() { return vehicule; }
    public void setVehicule(String vehicule) { this.vehicule = vehicule; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    @Override
    public String toString() {
        return "Livreur{id=" + id + ", nom='" + nom + " " + prenom + "', disponible=" + disponible + "}";
    }
}
