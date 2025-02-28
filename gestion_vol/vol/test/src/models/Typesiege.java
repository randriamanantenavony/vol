package src.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.connection.DbConnection;

public class Typesiege {
    private int id;
    private String libelle;

    // Constructeurs
    public Typesiege() {}

    public Typesiege(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    // Méthode pour récupérer tous les types de sièges depuis la base de données
    public static List<Typesiege> getAll(Connection conn) {
        List<Typesiege> liste = new ArrayList<>();
        String sql = "SELECT id, libelle FROM type_siege";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Typesiege ts = new Typesiege(rs.getInt("id"), rs.getString("libelle"));
                liste.add(ts);
            }
            DbConnection.closeResources(rs, stmt, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }
}
