# TGV Delivery — Application Desktop JavaFX

Application de gestion de livraisons développée avec **JavaFX 21** et **MySQL**.

## 🗂 Structure du Projet

```
src/main/java/com/tgv/
├── MainApplication.java        ← Point d'entrée
├── controllers/
│   ├── MainController.java     ← Navigation sidebar
│   ├── DashboardController.java
│   ├── CommandeController.java
│   ├── ProduitController.java
│   └── LivreurController.java
├── models/
│   ├── Commande.java
│   ├── Produit.java
│   └── Livreur.java
├── services/
│   ├── CrudService.java        ← Interface générique CRUD
│   ├── CommandeService.java
│   ├── ProduitService.java
│   └── LivreurService.java
└── utils/
    └── DBConnection.java       ← Singleton connexion MySQL

src/main/resources/com/tgv/
├── views/
│   ├── main.fxml
│   ├── dashboard.fxml
│   ├── commande.fxml
│   ├── produit.fxml
│   └── livreur.fxml
├── css/
│   └── style.css
└── images/
```

## ⚙️ Prérequis

- Java 21+
- Maven 3.8+
- MySQL 8.x

## 🚀 Lancement

```bash
# 1. Créer la base de données MySQL
# CREATE DATABASE tgv_db;

# 2. Configurer la connexion dans :
# src/main/java/com/tgv/utils/DBConnection.java

# 3. Lancer l'application
mvn javafx:run
```

## 👥 Modules par Membres

| Module            | Entités                    | Responsable |
|-------------------|----------------------------|-------------|
| Gestion Commandes | Commande, Produit, Livreur | Membre 1    |
| ...               | ...                        | Membre 2-5  |

## 🏗 Architecture

- **MVC** : Models / Controllers (FXML) / Services (DAO)
- **Singleton** : `DBConnection` pour la connexion MySQL unique
- **Interface** : `CrudService<T>` à implémenter pour chaque module
