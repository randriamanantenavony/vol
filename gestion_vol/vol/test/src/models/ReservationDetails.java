package src.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetails {
    private int reservationId;
    private Timestamp reservationDate;
    private String reservationPasseportImg;
    private int reservationDetailId;
    private int volId;
    private Timestamp volDepart;
    private Timestamp volArrivee;
    private String typeSiege;
    private int nombreSieges;

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationPasseportImg() {
        return reservationPasseportImg;
    }

    public void setReservationPasseportImg(String reservationPasseportImg) {
        this.reservationPasseportImg = reservationPasseportImg;
    }

    public int getReservationDetailId() {
        return reservationDetailId;
    }

    public void setReservationDetailId(int reservationDetailId) {
        this.reservationDetailId = reservationDetailId;
    }

    public int getVolId() {
        return volId;
    }

    public void setVolId(int volId) {
        this.volId = volId;
    }

    public Timestamp getVolDepart() {
        return volDepart;
    }

    public void setVolDepart(Timestamp volDepart) {
        this.volDepart = volDepart;
    }

    public Timestamp getVolArrivee() {
        return volArrivee;
    }

    public void setVolArrivee(Timestamp volArrivee) {
        this.volArrivee = volArrivee;
    }

    public String getTypeSiege() {
        return typeSiege;
    }

    public void setTypeSiege(String typeSiege) {
        this.typeSiege = typeSiege;
    }

    public int getNombreSieges() {
        return nombreSieges;
    }

    public void setNombreSieges(int nombreSieges) {
        this.nombreSieges = nombreSieges;
    }

    // Method to fetch reservation details from the database
    public static List<ReservationDetails> getReservationDetails(Connection connection) throws SQLException {
        List<ReservationDetails> reservationDetails = new ArrayList<>();
        String query = "SELECT reservation_id, reservation_date, reservation_passeport_img, id_reservation_detail, " +
                       "vol_id, vol_depart, vol_arrivee, type_siege, nombre_sieges FROM vue_reservation_details";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ReservationDetails detail = new ReservationDetails();
                detail.setReservationId(resultSet.getInt("reservation_id"));
                detail.setReservationDate(resultSet.getTimestamp("reservation_date"));
                detail.setReservationPasseportImg(resultSet.getString("reservation_passeport_img"));
                detail.setReservationDetailId(resultSet.getInt("id_reservation_detail"));
                detail.setVolId(resultSet.getInt("vol_id"));
                detail.setVolDepart(resultSet.getTimestamp("vol_depart"));
                detail.setVolArrivee(resultSet.getTimestamp("vol_arrivee"));
                detail.setTypeSiege(resultSet.getString("type_siege"));
                detail.setNombreSieges(resultSet.getInt("nombre_sieges"));
                reservationDetails.add(detail);
            }
        }

        return reservationDetails;
    }
}

