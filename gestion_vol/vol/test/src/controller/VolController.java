package src.controller;

import mg.controller.principal.*; // Importez l'annotation Controller
import mg.annotation.*;
import mg.tool.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import src.models.*;
import mg.tool.*;
import mg.exception.*;
import src.connection.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


@Auth
@Roles("admin")
@Controller
public class VolController {

    @Url(value="/app/showFormulaire")
    @Get()
    public ModelAndView showFormulaire() {
        ModelAndView mav = new ModelAndView("/views/save_vol");

        List<Avion> avions = new  ArrayList<>();
        List<Ville> villes = new ArrayList<>();
     
        try {
            avions =  Avion.getAll(null);
            villes = Ville.getAll(null);
        } catch (Exception e) {
           e.printStackTrace();
        }

        mav.add("avions",avions);
        mav.add("ville", villes);
        
        return mav;
    }

    @Url(value="/app/save")
    @Post()
    @IsError("/app/showFormulaire")
    public ModelAndView validateForm(@RequestParameter("date1") String depart, 
                                 @RequestParameter("date2") String arrivee,
                                 @RequestParameter("avion") Integer id_avion,
                                 @RequestParameter("depart") Integer ville_depart, 
                                 @RequestParameter("arrivee") Integer ville_arrive ) throws ClassNotFoundException, SQLException {

    // Crée un objet ModelAndView pour la page de succès
    ModelAndView reussi = new ModelAndView("/views/login_reussi");

    // Connexion à la base de données
    Connection c = DbConnection.getConnection();

    // Création de l'objet Avion et Ville
    Avion avion = new Avion();
    avion.setId(id_avion);

    Ville v_depart = new Ville();
    v_depart.setId(ville_depart);

    Ville v_arrivee = new Ville();
    v_arrivee.setId(ville_arrive);

    // Déclaration des variables Timestamp
    Timestamp timestampDepart = null;
    Timestamp timestampArrivee = null;

    // Vérification et conversion de la date de départ
    if (depart != null && !depart.isEmpty()) {
        try {
            LocalDateTime localDateTimeDepart = LocalDateTime.parse(depart, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            timestampDepart = Timestamp.valueOf(localDateTimeDepart);
            System.out.println("Timestamp depart converti : " + timestampDepart);
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion de la date de départ : " + e.getMessage());
        }
    }

    // Vérification et conversion de la date d'arrivée
    if (arrivee != null && !arrivee.isEmpty()) {
        try {
            LocalDateTime localDateTimeArrivee = LocalDateTime.parse(arrivee, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            timestampArrivee = Timestamp.valueOf(localDateTimeArrivee);
            System.out.println("Timestamp arrivee converti : " + timestampArrivee);
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion de la date d'arrivée : " + e.getMessage());
        }
    }

    // Création de l'objet Vol et ajout des informations
    Vol vol = new Vol();
    vol.setDateHeureDepart(timestampDepart);
    vol.setDateHeureArrivee(timestampArrivee);
    vol.setIdAvion(avion);
    vol.setVilleDepart(v_depart);
    vol.setVilleArrivee(v_arrivee);

    vol.save(c);

    // Ajout du message de réussite à l'attribut de la requête
    reussi.add("message", "Insertion réussie ");

    return reussi;
}


@Url(value="/app/list_vol")
@Get()
public ModelAndView getListeVol() {
    ModelAndView mav = new ModelAndView("/views/liste_vol");
     
    List<Vol> vols = new ArrayList<>();
 
    try {
        vols = Vol.getAll(null);
    } catch (Exception e) {
       e.printStackTrace();
    }

    mav.add("vols", vols);
    
    return mav;
}

@Url(value="/app/delete")
@Get()
public ModelAndView deleteVol(@RequestParameter("id") Integer id_vol) {

    ModelAndView mav = new ModelAndView("/views/login_reussi");
    try {
       Vol.supprimerVol(null, id_vol);
    } catch (Exception e) {
       e.printStackTrace();
       return null;
    }

    return mav;
}
  

@Url(value="/app/update_redirect")
@Get()
public ModelAndView updateredirectVol(@RequestParameter("id") Integer id_vol) {
    ModelAndView mav = new ModelAndView("/views/update_vol");
    List<Avion> avions = new  ArrayList<>();
    List<Ville> villes = new ArrayList<>();
    Vol update = new Vol();
      
    try {
       update = Vol.getById(null, id_vol);
       avions =  Avion.getAll(null);
       villes = Ville.getAll(null);
     
    } catch (Exception e) {
       e.printStackTrace();
    }

    mav.add("vol",update);
    mav.add("avions",avions);
    mav.add("ville", villes);

    return mav;
}


@Url(value="/app/update")
@Post()
public ModelAndView updateVol(        @RequestParameter("id_vol") Integer id_vol, 
                                @RequestParameter("date1") String depart, 
                                @RequestParameter("date2") String arrivee,
                                @RequestParameter("avion") Integer id_avion,
                                @RequestParameter("depart") Integer ville_depart, 
                                @RequestParameter("arrivee") Integer ville_arrive) throws Exception{

     Connection c = DbConnection.getConnection();
    
     ModelAndView mav = new ModelAndView("/views/login_reussi");

    Avion avion = new Avion();
    avion.setId(id_avion);

    Ville v_depart = new Ville();
    v_depart.setId(ville_depart);

    Ville v_arrivee = new Ville();
    v_arrivee.setId(ville_arrive);
    Timestamp timestampDepart = null;
    Timestamp timestampArrivee = null;
    if (depart != null && !depart.isEmpty()) {
        try {
            LocalDateTime localDateTimeDepart = LocalDateTime.parse(depart, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            timestampDepart = Timestamp.valueOf(localDateTimeDepart);
            System.out.println("Timestamp depart converti : " + timestampDepart);
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion de la date de départ : " + e.getMessage());
        }
    }

    // Vérification et conversion de la date d'arrivée
    if (arrivee != null && !arrivee.isEmpty()) {
        try {
            LocalDateTime localDateTimeArrivee = LocalDateTime.parse(arrivee, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            timestampArrivee = Timestamp.valueOf(localDateTimeArrivee);
            System.out.println("Timestamp arrivee converti : " + timestampArrivee);
        } catch (Exception e) {
            System.out.println("Erreur lors de la conversion de la date d'arrivée : " + e.getMessage());
        }
    }

    // Création de l'objet Vol et ajout des informations
    Vol vol = new Vol();
    vol.setId(id_vol);
    vol.setDateHeureDepart(timestampDepart);
    vol.setDateHeureArrivee(timestampArrivee);
    vol.setIdAvion(avion);
    vol.setVilleDepart(v_depart);
    vol.setVilleArrivee(v_arrivee);

    vol.mettreAJourVol(c, vol);
    System.out.println("reussi");
    return mav;

  }

//   @Url(value="/app/criteria")
//   @Get()
//   public ModelAndView findByCriteria() {
//       ModelAndView mav = new ModelAndView("/views/rechercher");

//       System.out.println("je suis dans rechercheByCriteria");
//       List<Avion> avions = new  ArrayList<>();
//       List<Ville> villes = new ArrayList<>();
   
//       try {
//           avions =  Avion.getAll(null);
//           villes = Ville.getAll(null);
//       } catch (Exception e) {
//          e.printStackTrace();
//       }

//       mav.add("avions",avions);
//       mav.add("ville", villes);
      
//       return mav;
//   }



// @Url(value="/app/resultat")
// @Post()
// public ModelAndView resultatRecherche(@RequestParameter("date1") String depart, 
//                         @RequestParameter("date2") String arrivee,
//                         @RequestParameter("avion") Integer id_avion,
//                         @RequestParameter("depart") Integer ville_depart, 
//                         @RequestParameter("arrivee") Integer ville_arrive) {

//     ModelAndView mav = new ModelAndView("/views/recherche_result");
//     List<Vol> vols = new ArrayList<>();
//     try {
//     vols = Vol.rechercherVols(null, depart, arrivee, id_avion, ville_depart, ville_arrive);
//     System.out.println("taillde des resultats : " + vols.size());
    
//     } catch (Exception e) {
//        e.printStackTrace();
//     }
//     mav.add("vols",vols);
//     return mav;
// }

@Url(value="/app/annule")
@Get()
public ModelAndView annulationVol(@RequestParameter("id") Integer id_vol) {

    ModelAndView mav = new ModelAndView("/views/login_reussi");

    try {
        Connection c = null;
        if (c == null || c.isClosed()) {
            c =  DbConnection.getConnection();
        } 
         Vol.annulerVol(c, id_vol);
    } catch (Exception e) {
       e.printStackTrace();
       return null;
    }

    return mav;
}



@Url(value="/app/api")
@Get()
@RestApi
public ModelAndView volRest() {
    System.out.println("je suis dans la methode rest api");

    ModelAndView mav = new ModelAndView("");
    ArrayList<Ville> villes = new ArrayList<>();
    villes.add(new Ville(1, "Paris", "France"));
    villes.add(new Ville(2, "New York", "Etats-Unis"));
    villes.add(new Ville(3, "Tokyo", "Japon"));

    mav.add("villes",villes);
    return mav;
}



@Url(value="/app/api1")
@Get()
@RestApi
public String showApi() {
   String var1 = "Hello";
   return var1;
}

}


