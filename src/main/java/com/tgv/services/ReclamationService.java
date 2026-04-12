package com.tgv.services;

import com.tgv.models.Reclamation;
import com.tgv.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {

    private Connection cnx;

    public ReclamationService() {
        cnx = DBConnection.getInstance();
    }

    private Boolean hasPhoneColumnCache;

    private boolean hasPhoneColumn() {
        if (hasPhoneColumnCache != null) {
            return hasPhoneColumnCache;
        }

        boolean exists = false;
        try {
            DatabaseMetaData meta = cnx.getMetaData();
            try (ResultSet cols = meta.getColumns(null, null, "reclamation", "phone")) {
                exists = cols.next();
            }

            if (!exists) {
                try (ResultSet cols = meta.getColumns(null, null, "RECLAMATION", "PHONE")) {
                    exists = cols.next();
                }
            }
        } catch (SQLException e) {
            exists = false;
        }

        hasPhoneColumnCache = exists;
        return exists;
    }

    public void ajouter(Reclamation r) throws SQLException {

        if (hasPhoneColumn()) {
            String sql = "INSERT INTO reclamation(subject, message, status, created_at, updated_at, user_id, type_service, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, r.getSubject());
            ps.setString(2, r.getMessage());
            ps.setString(3, r.getStatus());
            ps.setString(4, r.getCreatedAt());
            ps.setString(5, r.getUpdatedAt());

            if (r.getUserId() != null) {
                ps.setInt(6, r.getUserId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setString(7, r.getTypeService());
            ps.setString(8, r.getPhone());

            ps.executeUpdate();
        } else {
            String sql = "INSERT INTO reclamation(subject, message, status, created_at, updated_at, user_id, type_service) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, r.getSubject());
            ps.setString(2, r.getMessage());
            ps.setString(3, r.getStatus());
            ps.setString(4, r.getCreatedAt());
            ps.setString(5, r.getUpdatedAt());

            if (r.getUserId() != null) {
                ps.setInt(6, r.getUserId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setString(7, r.getTypeService());

            ps.executeUpdate();
        }
        System.out.println("Reclamation ajoutée !");
    }

    public List<Reclamation> afficher() throws SQLException {

        List<Reclamation> list = new ArrayList<>();

        String sql = "SELECT r.*, " +
                "CASE WHEN EXISTS (SELECT 1 FROM reponse rp WHERE rp.reclamation_id = r.id) " +
                "THEN 'Oui' ELSE 'Non' END AS deja_repondu " +
                "FROM reclamation r";

        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Reclamation r = new Reclamation(
                    rs.getInt("id"),
                    rs.getString("subject"),
                    rs.getString("message"),
                    rs.getString("status"),
                    rs.getString("created_at"),
                    rs.getString("updated_at"),
                    (Integer) rs.getObject("user_id"),
                    rs.getString("type_service")
            );

            r.setDejaRepondu(rs.getString("deja_repondu"));

            if (hasPhoneColumn()) {
                r.setPhone(rs.getString("phone"));
            }

            list.add(r);
        }

        return list;
    }

    public void modifier(Reclamation r) throws SQLException {

        if (hasPhoneColumn()) {
            String sql = "UPDATE reclamation SET subject=?, message=?, status=?, updated_at=?, user_id=?, type_service=?, phone=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, r.getSubject());
            ps.setString(2, r.getMessage());
            ps.setString(3, r.getStatus());
            ps.setString(4, r.getUpdatedAt());

            if (r.getUserId() != null) {
                ps.setInt(5, r.getUserId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setString(6, r.getTypeService());
            ps.setString(7, r.getPhone());
            ps.setInt(8, r.getId());

            ps.executeUpdate();
        } else {
            String sql = "UPDATE reclamation SET subject=?, message=?, status=?, updated_at=?, user_id=?, type_service=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, r.getSubject());
            ps.setString(2, r.getMessage());
            ps.setString(3, r.getStatus());
            ps.setString(4, r.getUpdatedAt());

            if (r.getUserId() != null) {
                ps.setInt(5, r.getUserId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setString(6, r.getTypeService());
            ps.setInt(7, r.getId());

            ps.executeUpdate();
        }

        System.out.println("Reclamation modifiée !");
    }

    public void supprimer(int id) throws SQLException {

        String sql = "DELETE FROM reclamation WHERE id=?";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

        System.out.println("Reclamation supprimée !");
    }
}
