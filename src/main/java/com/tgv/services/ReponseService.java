package com.tgv.services;

import com.tgv.models.Reponse;
import com.tgv.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseService {

    private Connection cnx;

    public ReponseService() {
        cnx = DBConnection.getInstance();
    }

    public ReponseService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    public void ajouter(Reponse r) throws SQLException {
        String sql = "INSERT INTO reponse(content, author, created_at, updated_at, reclamation_id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, r.getContent());
        ps.setString(2, r.getAuthor());
        ps.setString(3, r.getCreatedAt());
        ps.setString(4, r.getUpdatedAt());
        ps.setInt(5, r.getReclamationId());

        ps.executeUpdate();
        System.out.println("Reponse ajoutée !");
    }

    public List<Reponse> afficher() throws SQLException {
        List<Reponse> list = new ArrayList<>();

        String sql = "SELECT * FROM reponse";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Reponse r = new Reponse(
                    rs.getInt("id"),
                    rs.getString("content"),
                    rs.getString("author"),
                    rs.getString("created_at"),
                    rs.getString("updated_at"),
                    rs.getInt("reclamation_id")
            );
            list.add(r);
        }

        return list;
    }

    public void modifier(Reponse r) throws SQLException {
        String sql = "UPDATE reponse SET content=?, author=?, updated_at=?, reclamation_id=? WHERE id=?";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, r.getContent());
        ps.setString(2, r.getAuthor());
        ps.setString(3, r.getUpdatedAt());
        ps.setInt(4, r.getReclamationId());
        ps.setInt(5, r.getId());

        ps.executeUpdate();
        System.out.println("Reponse modifiée !");
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reponse WHERE id=?";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
        System.out.println("Reponse supprimée !");
    }
    public List<Reponse> getByReclamation(int idReclamation) throws SQLException {

        List<Reponse> list = new ArrayList<>();

        String sql = "SELECT * FROM reponse WHERE reclamation_id = ?";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, idReclamation);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Reponse r = new Reponse(
                    rs.getInt("id"),
                    rs.getString("content"),
                    rs.getString("author"),
                    rs.getString("created_at"),
                    rs.getString("updated_at"),
                    rs.getInt("reclamation_id")
            );

            list.add(r);
        }

        return list;
    }
}
