package src.models;

import src.connection.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private int id;
    private int idHeureLimiteReservation;
    private int idHeureLimiteAnnulation;
    private Timestamp dateCreation;

    // Constructeurs
    public Configuration() {}

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHeureLimiteReservation() {
        return idHeureLimiteReservation;
    }

    public void setIdHeureLimiteReservation(int idHeureLimiteReservation) {
        this.idHeureLimiteReservation = idHeureLimiteReservation;
    }

    public int getIdHeureLimiteAnnulation() {
        return idHeureLimiteAnnulation;
    }

    public void setIdHeureLimiteAnnulation(int idHeureLimiteAnnulation) {
        this.idHeureLimiteAnnulation = idHeureLimiteAnnulation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    // Méthode pour enregistrer une configuration
    public void save() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DbConnection.getConnection();
            String sql = "INSERT INTO reservation_configuration (heure_limite_reservation, heure_limite_annulation) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.idHeureLimiteReservation);
            stmt.setInt(2, this.idHeureLimiteAnnulation);
            stmt.executeUpdate();
            System.out.println("Configuration enregistrée avec succès.");
        } finally {
            DbConnection.closeResources(null, stmt, conn);
        }
    }

    // Méthode pour récupérer toutes les configurations
    public static List<Configuration> getAll() throws SQLException, ClassNotFoundException {
        List<Configuration> configurations = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnection.getConnection();
            String sql = "SELECT * FROM reservation_configuration";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Configuration config = new Configuration();
                config.setId(rs.getInt("id"));
                config.setIdHeureLimiteReservation(rs.getInt("heure_limite_reservation"));
                config.setIdHeureLimiteAnnulation(rs.getInt("heure_limite_annulation")); 
                config.setDateCreation(rs.getTimestamp("date_creation"));
                configurations.add(config);
            }
        } finally {
            DbConnection.closeResources(rs, stmt, conn);
        }
        return configurations;
    }

    public static Configuration getCurrent() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Configuration config = null;
    
        try {
            conn = DbConnection.getConnection();
            String sql = "SELECT * FROM reservation_configuration ORDER BY date_creation DESC LIMIT 1";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
    
            if (rs.next()) {
                config = new Configuration();
                config.setId(rs.getInt("id"));
                config.setIdHeureLimiteReservation(rs.getInt("heure_limite_reservation"));
                config.setIdHeureLimiteAnnulation(rs.getInt("heure_limite_annulation")); 
            }
        } finally {
            DbConnection.closeResources(rs, stmt, conn);
        }
        return config;
    }
    
    
}
