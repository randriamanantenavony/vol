package src.models;

import java.sql.*;

public class Passager {
    private int id;
    private String mail;

    public Passager(){}
    public Passager(String mail) {
        this.mail = mail;
    }

    public void setId(int id){this.id = id;}
    public void setMail(String mail){this.mail=mail;}

    public static Passager login(Connection connection, String mail) throws SQLException {
        Passager p = new Passager();
        

        String selectQuery = "SELECT id,mail FROM passager WHERE mail = ?";
        String insertQuery = "INSERT INTO passager (mail) VALUES (?) RETURNING id"; 

        // Vérifie si le passager existe déjà
        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, mail);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setMail(rs.getString("mail"));
                return p; // Retourne le passager existant
            }
        }

        // Insère un nouveau passager s'il n'existe pas
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, mail);
            ResultSet rs = insertStmt.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setMail(rs.getString("mail"));
                return p; // Retourne le nouveau passager
            }
        }

        throw new SQLException("Erreur lors de l'insertion du passager.");
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }
}

