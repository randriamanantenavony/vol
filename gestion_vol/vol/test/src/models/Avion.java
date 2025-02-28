package src.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.connection.DbConnection;

public class Avion {
    private int id;
    private String nom;
    private String modele;
    private Date dateFabrication;

    public Avion(int id, String nom, String modele, Date dateFabrication) {
        this.id = id;
        this.nom = nom;
        this.modele = modele;
        this.dateFabrication = dateFabrication;
    }

    public Avion() {
        //TODO Auto-generated constructor stub
    }

    public Avion(int int1, String string) {
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

    public String getModele() {
        return modele;
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setDateFabrication(Date dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public static List<Avion> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        List<Avion> avions = new ArrayList<>();
        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection();
        }

        String query = "SELECT a.id, a.nom, m.libelle AS modele, a.date_fabrication " +
                       "FROM avion a JOIN modele m ON a.id_modele = m.id";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                avions.add(new Avion(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("modele"),
                    rs.getDate("date_fabrication")
                ));
            }
        }
        return avions;
    }
}

