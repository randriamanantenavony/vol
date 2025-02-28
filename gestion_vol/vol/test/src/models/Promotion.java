package src.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Promotion {
    private int idVol;
    private int idTypeSiege;
    private String typeSiege;
    private double prix;
    private double pourcentagePromotion;
    private double prixReel;
    private int nombre;

    public void setNombre(int n){this.nombre=n;}
    public int getNombre(){return this.nombre;}
    // Setters
    public void setIdVol(int idVol) {
        this.idVol = idVol;
    }

    public void setIdTypeSiege(int idTypeSiege) {
        this.idTypeSiege = idTypeSiege;
    }

    public void setTypeSiege(String typeSiege) {
        this.typeSiege = typeSiege;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setPourcentagePromotion(double pourcentagePromotion) {
        this.pourcentagePromotion = pourcentagePromotion;
    }

    public void setPrixReel(double prixReel) {
        this.prixReel = prixReel;
    }

    // Getter methods
    public int getIdVol() {
        return idVol;
    }

    public int getIdTypeSiege() {
        return idTypeSiege;
    }

    public String getTypeSiege() {
        return typeSiege;
    }

    public double getPrix() {
        return prix;
    }

    public double getPourcentagePromotion() {
        return pourcentagePromotion;
    }

    public double getPrixReel() {
        return prixReel;
    }

    // Méthode pour récupérer toutes les promotions à partir de la vue v_detail_promotion
    public static List<Promotion> getAll(Connection connection) throws SQLException {
        String query = "SELECT * FROM v_detail_promotion";
        List<Promotion> promotions = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setIdVol(rs.getInt("id_vol"));
                promotion.setIdTypeSiege(rs.getInt("id_type_siege"));
                promotion.setTypeSiege(rs.getString("type_siege"));
                promotion.setPrix(rs.getDouble("prix"));
                promotion.setPourcentagePromotion(rs.getDouble("pourcentage_promotion"));
                promotion.setPrixReel(rs.getDouble("prix_reel"));
                promotion.setNombre(rs.getInt("nombre_siege"));
                promotions.add(promotion);
            }
        }

        return promotions;
    }
}

