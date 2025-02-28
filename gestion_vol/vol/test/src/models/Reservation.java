package src.models;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import src.connection.DbConnection;

public class Reservation {
    private int id;
    private Timestamp dateHeure;
    private int idPassager;
    private int idEtatReservation;
    private int idVol;
    String fileName;

    public void setFileName(String s){
        this.fileName = s;
    }

    public String getFileName(){
        return fileName;
    }

    // Constructeurs
    public Reservation() {}

    public Reservation(Timestamp dateHeure, int idPassager, int idEtatReservation) {
        this.dateHeure = dateHeure;
        this.idPassager = idPassager;
        this.idEtatReservation = idEtatReservation;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }

    public int getIdPassager() {
        return idPassager;
    }

    public void setIdPassager(int idPassager) {
        this.idPassager = idPassager;
    }

    public int getIdEtatReservation() {
        return idEtatReservation;
    }

    public void setIdEtatReservation(int idEtatReservation) {
        this.idEtatReservation = idEtatReservation;
    }

    public int insererReservation(Connection conn) throws SQLException {
        String sqlInsert = "INSERT INTO reservation (dateHeure, id_passager, id_etat_reservation,passeport_img) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, this.getIdPassager());
            stmt.setInt(3,1 );
            stmt.setString(4,this.getFileName());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Échec de l'insertion de la réservation.");
                return -1;
            }

            // Récupérer l'ID de la réservation insérée
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retourne l'ID de la réservation insérée
                }
            }
        }
        throw new SQLException("Échec de la récupération de l'ID de la réservation.");
    }

    
}
