package src.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.connection.DbConnection;

public class Ville {
    private int id;
    private String nom;
    private String pays;

    public Ville(int id, String nom, String pays) {
        this.id = id;
        this.nom = nom;
        this.pays = pays;
    }

    public Ville() {
    }

    public Ville(int int1, String string) {
       this.id = int1;
       this.nom = string;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPays() {
        return pays;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Ville{id=" + id + ", nom='" + nom + "', pays='" + pays + "'}";
    }


    public static List<Ville> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        List<Ville> villes = new ArrayList<>();
        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection();
        }

        String query = "SELECT id, nom, pays FROM ville";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                villes.add(new Ville(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("pays")
                ));
            }
        }
        return villes;
    }
}
