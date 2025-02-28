package src.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import src.connection.DbConnection;

public class ReservationDetail {
    private int idReservationDetail;
    private int idReservation;
    private int idVol;
    private int idTypeSiege;
    private int nombre;

    // Constructeurs
    public ReservationDetail() {}

    public ReservationDetail(int idReservation, int idVol, int idTypeSiege, int nombre) {
        this.idReservation = idReservation;
        this.idVol = idVol;
        this.idTypeSiege = idTypeSiege;
        this.nombre = nombre;
    }

    // Getters et Setters
    public int getIdReservationDetail() {
        return idReservationDetail;
    }

    public void setIdReservationDetail(int idReservationDetail) {
        this.idReservationDetail = idReservationDetail;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdVol() {
        return idVol;
    }

    public void setIdVol(int idVol) {
        this.idVol = idVol;
    }

    public int getIdTypeSiege() {
        return idTypeSiege;
    }

    public void setIdTypeSiege(int idTypeSiege) {
        this.idTypeSiege = idTypeSiege;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

        public static boolean verifierLimiteReservation(Connection connection, int idVol, LocalDateTime dateHeureReservation) throws SQLException, ClassNotFoundException {
        // Récupérer l'heure limite de réservation
        Configuration config = Configuration.getCurrent();
        if (config == null) {
            System.out.println("Aucune configuration trouvée !");
            return false;
        }
        int heureLimiteReservation = config.getIdHeureLimiteReservation();
        if (heureLimiteReservation == 0) {
            throw new SQLException("Aucune configuration de réservation trouvée.");
        }

        // Récupérer la date de départ du vol
        String queryVol = "SELECT dateHeureDepart FROM vol WHERE id = ?";
        LocalDateTime dateHeureDepart = null;

        try (PreparedStatement stmt = connection.prepareStatement(queryVol)) {
            stmt.setInt(1, idVol);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dateHeureDepart = rs.getTimestamp("dateHeureDepart").toLocalDateTime();
            }
        }

        if (dateHeureDepart == null) {
            throw new SQLException("Vol non trouvé.");
        }

        // Vérifier si la réservation respecte la limite
        LocalDateTime limiteReservation = dateHeureDepart.minusHours(heureLimiteReservation);

        return !dateHeureReservation.isAfter(limiteReservation);
    }

    // Méthode pour insérer un détail de réservation
    public void insert(Connection conn) {
        String sql = "INSERT INTO reservation_detail (id_reservation, id_vol, id_type_siege, nombre) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReservation);
            stmt.setInt(2, idVol);
            stmt.setInt(3, idTypeSiege);
            stmt.setInt(4, nombre);
            
            stmt.executeUpdate();
            DbConnection.closeResources(null, stmt, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteReservation(Connection connection, int reservationId) throws ClassNotFoundException, SQLException {
        String deleteReservationDetailQuery = "DELETE FROM reservation_detail WHERE id_reservation = ?";
        String deleteReservationQuery = "DELETE FROM reservation WHERE id = ?";

        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection();
        }
        // Start a transaction
        try {
            connection.setAutoCommit(false);  // Disable auto-commit to manage transaction manually
            
            // Delete related reservation details first to avoid foreign key constraint issues
            try (PreparedStatement deleteDetailStmt = connection.prepareStatement(deleteReservationDetailQuery)) {
                deleteDetailStmt.setInt(1, reservationId);
                deleteDetailStmt.executeUpdate();
            }
            
            // Now, delete the reservation
            try (PreparedStatement deleteReservationStmt = connection.prepareStatement(deleteReservationQuery)) {
                deleteReservationStmt.setInt(1, reservationId);
                int rowsAffected = deleteReservationStmt.executeUpdate();
                
                // If the reservation was deleted, commit the transaction
                if (rowsAffected > 0) {
                    connection.commit();
                    return true; // Successfully deleted reservation and its details
                } else {
                    connection.rollback(); // If no rows were affected, roll back the transaction
                    return false; // Reservation not found
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback if there is any error while deleting reservation
                throw e;
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback the entire transaction in case of an error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace(); // Handle rollback exception (optional)
            }
            e.printStackTrace(); // Log or handle the error
            return false; // Return false if an error occurs during deletion
        } finally {
            try {
                connection.setAutoCommit(true); // Restore auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception when resetting auto-commit
            }
        }
    }

}
