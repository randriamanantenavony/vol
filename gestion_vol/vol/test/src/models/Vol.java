package src.models;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import src.connection.DbConnection;

public class Vol {
    private int id;
    private Timestamp dateHeureDepart;
    private Timestamp dateHeureArrivee;
    private Avion idAvion;
    private Ville villeDepart;
    private Ville villeArrivee;
    private String etat;

    public Vol(int id, Timestamp dateHeureDepart, Timestamp dateHeureArrivee, Avion idAvion, Ville villeDepart, Ville villeArrivee,String e) {
        this.id = id;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrivee = dateHeureArrivee;
        this.idAvion = idAvion;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.etat = e;
    }

    public Vol(int id, Timestamp dateHeureDepart, Timestamp dateHeureArrivee, Avion idAvion, Ville villeDepart, Ville villeArrivee) {
        this.id = id;
        this.dateHeureDepart = dateHeureDepart;
        this.dateHeureArrivee = dateHeureArrivee;
        this.idAvion = idAvion;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
    }

    public Vol(){}

    public void setEtat(String etat){
        this.etat = etat;
    }

    public String getEtat(){return this.etat; }
    // Getters
    public int getId() {
        return id;
    }

    public Timestamp getDateHeureDepart() {
        return dateHeureDepart;
    }

    public Timestamp getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public Avion getIdAvion() {
        return idAvion;
    }

    public Ville getVilleDepart() {
        return villeDepart;
    }

    public Ville getVilleArrivee() {
        return villeArrivee;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDateHeureDepart(Timestamp dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public void setDateHeureArrivee(Timestamp dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public void setIdAvion(Avion idAvion) {
        this.idAvion = idAvion;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    public void setVilleArrivee(Ville villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public static List<Vol> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        List<Vol> vols = new ArrayList<>();
        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection();
        }

        String query = " select * from v_detail_vol";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ville villeDepart = new Ville(
                    rs.getInt("id_ville_depart"),
                    rs.getString("ville_depart")
                );
                Ville villeArrivee = new Ville(
                    rs.getInt("id_ville_arrivee"),
                    rs.getString("ville_arrivee")
                );

                Avion avion = new Avion();
                avion.setId(rs.getInt("id_avion"));
                avion.setNom(rs.getString("nom_avion"));

                vols.add(new Vol(
                    rs.getInt("id"),
                    rs.getTimestamp("dateheuredepart"),
                    rs.getTimestamp("dateheurearrivee"),
                    avion,
                    villeDepart,
                    villeArrivee,
                    rs.getString("statut")
                ));
            }
        }
        return vols;
    }

    
    public static Vol getById(Connection connection, int id_vol) throws SQLException, ClassNotFoundException {
        Vol vol = null; // Initialisation à null pour gérer le cas où aucun vol n'est trouvé

        // Vérification de la connexion (inutile si la connexion est toujours fournie)
        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection(); // Récupérer la connexion (votre classe de connexion)
        }

        String query = "SELECT * FROM v_detail_vol WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_vol);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Utilisation de if au lieu de while, car on cherche un seul vol par ID
                    Ville villeDepart = new Ville(
                            rs.getInt("id_ville_depart"),
                            rs.getString("ville_depart")
                    );
                    Ville villeArrivee = new Ville(
                            rs.getInt("id_ville_arrivee"),
                            rs.getString("ville_arrivee")
                    );

                    Avion avion = new Avion();
                    avion.setId(rs.getInt("id_avion"));
                    avion.setNom(rs.getString("nom_avion"));

                    vol = new Vol( // Création d'un nouvel objet Vol
                            rs.getInt("id"),
                            rs.getTimestamp("dateheuredepart"),
                            rs.getTimestamp("dateheurearrivee"),
                            avion,
                            villeDepart,
                            villeArrivee
                    );
                }
            }
        }
        return vol;
    }


    public  void save(Connection connection) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection();
        }

        String query = "INSERT INTO vol (dateHeureDepart, dateHeureArrivee, id_avion, id_ville_depart, id_ville_arrivee) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, this.getDateHeureDepart());
            stmt.setTimestamp(2, this.getDateHeureArrivee());
            stmt.setInt(3, this.getIdAvion().getId());
            stmt.setInt(4, this.getVilleDepart().getId());
            stmt.setInt(5, this.getVilleArrivee().getId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static void supprimerVol(Connection connection, int idVol) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            connection = DbConnection.getConnection();
        }

        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM vol WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idVol);

            int lignesAffectees = statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static boolean mettreAJourVol(Connection connexion, Vol vol) throws SQLException {
        PreparedStatement statement = null;
        try {
            // Requête SQL pour mettre à jour le vol
            String query = "UPDATE vol SET dateheuredepart = ?, dateheurearrivee = ?, id_avion = ?, id_ville_depart = ?, id_ville_arrivee = ? WHERE id = ?";

            // Préparation de la requête
            statement = connexion.prepareStatement(query);
            statement.setTimestamp(1, vol.getDateHeureDepart());
            statement.setTimestamp(2, vol.getDateHeureArrivee());
            statement.setInt(3, vol.getIdAvion().getId()); // Supposons que Avion a une méthode getId()
            statement.setInt(4, vol.getVilleDepart().getId()); // Supposons que Ville a une méthode getId()
            statement.setInt(5, vol.getVilleArrivee().getId()); // Supposons que Ville a une méthode getId()
            statement.setInt(6, vol.getId());

            // Exécution de la requête
            int lignesAffectees = statement.executeUpdate();

            // Vérification du nombre de lignes affectées (1 si la mise à jour a réussi)
            return lignesAffectees > 0;

        } finally {
            // Fermeture du statement dans le bloc finally pour garantir qu'il est toujours fermé
            if (statement != null) {
                statement.close();
            }
        }
    }


public static void annulerVol(Connection connection, int idVol) throws SQLException {
    String sqlUpdate = "UPDATE vol SET statut = 'Annulé' WHERE id = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sqlUpdate)) {
        stmt.setInt(1, idVol);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            System.out.println("Le vol avec ID " + idVol + " a été annulé.");
        } else {
            System.out.println("Aucun vol trouvé avec ID " + idVol);
        }
    }
}

public static List<Vol> rechercherVols(Connection connection, String depart, String arrivee,
                                       Integer idAvion, Integer villeDepart, Integer villeArrivee)
        throws SQLException, Exception {

    if (connection == null || connection.isClosed()) {
        connection = DbConnection.getConnection();
    }
    List<Vol> vols = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM v_detail_vol WHERE 1=1");
    List<Object> params = new ArrayList<>();

    try {
        Timestamp timestampDepart = null;
        Timestamp timestampArrivee = null;

        // Conversion de la date de départ
        if (depart != null && !depart.isEmpty()) {
            try {
                LocalDateTime localDateTimeDepart = LocalDateTime.parse(depart, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                timestampDepart = Timestamp.valueOf(localDateTimeDepart);
                query.append(" AND dateheuredepart >= ?");
                params.add(timestampDepart);
                System.out.println("Timestamp départ converti : " + timestampDepart);
            } catch (Exception e) {
                throw new IllegalArgumentException("Format de la date de départ invalide. Utiliser le format : yyyy-MM-dd'T'HH:mm.");
            }
        }

        // Conversion de la date d'arrivée
        if (arrivee != null && !arrivee.isEmpty()) {
            try {
                LocalDateTime localDateTimeArrivee = LocalDateTime.parse(arrivee, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                timestampArrivee = Timestamp.valueOf(localDateTimeArrivee);
                query.append(" AND dateheurearrivee <= ?");
                params.add(timestampArrivee);
                System.out.println("Timestamp arrivée converti : " + timestampArrivee);
            } catch (Exception e) {
                throw new IllegalArgumentException("Format de la date d'arrivée invalide. Utiliser le format : yyyy-MM-dd'T'HH:mm.");
            }
        }

        // Ajout des autres critères
        if (idAvion != null) {
            query.append(" AND id_avion = ?");
            params.add(idAvion);
        }
        if (villeDepart != null) {
            query.append(" AND id_ville_depart = ?");
            params.add(villeDepart);
        }
        if (villeArrivee != null) {
            query.append(" AND id_ville_arrivee = ?");
            params.add(villeArrivee);
        }

        // Exécution de la requête
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Création de l'objet Vol à partir des résultats
                    Vol vol = new Vol(
                            resultSet.getInt("id"),
                            resultSet.getTimestamp("dateheuredepart"),
                            resultSet.getTimestamp("dateheurearrivee"),
                            new Avion(resultSet.getInt("id_avion"), resultSet.getString("nom_avion")),
                            new Ville(resultSet.getInt("id_ville_depart"), resultSet.getString("ville_depart")),
                            new Ville(resultSet.getInt("id_ville_arrivee"), resultSet.getString("ville_arrivee"))
                    );
                    vols.add(vol);
                }
            }
        }
    } catch (IllegalArgumentException e) {
        System.err.println("Erreur : " + e.getMessage());
        throw e;
    }

    return vols;
}


}
