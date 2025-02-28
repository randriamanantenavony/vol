/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.models;

/**
 *
 * @author SAROBIDY
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mg.annotation.NotNull;

public class Admin {
    private int id;

    @NotNull
    private String mail;

    @NotNull
    private String mdp;

    public Admin(String mail, String mdp) {
        this.mail = mail;
        this.mdp = mdp;
    }

    public boolean verifierLogin(Connection connection) {
        
        String sql = "SELECT id FROM admin WHERE mail = ? AND mdp = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.mail);
            preparedStatement.setString(2, this.mdp);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Retourne true si un enregistrement est trouvé
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

