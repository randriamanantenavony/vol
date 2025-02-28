/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/vol"; // Remplacez par votre URL PostgreSQL
    private static final String USER = "postgres"; // Remplacez par votre utilisateur PostgreSQL
    private static final String PASSWORD = "mickey"; // Remplacez par votre mot de passe PostgreSQL

    // Méthode pour obtenir une connexion
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connexion PostgreSQL établie avec succès.");
        return connection;
    }

    // Méthode pour fermer les ressources
    public static void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
