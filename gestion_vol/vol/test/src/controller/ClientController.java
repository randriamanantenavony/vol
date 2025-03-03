package src.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.connection.DbConnection;
import src.models.Admin;
import src.models.Avion;
import src.models.Passager;
import src.models.Ville;
import src.models.Vol;
import mg.controller.principal.*;
import mg.annotation.*;
import mg.tool.*;


@Controller
public class ClientController {
    
    @Url(value="/app/list_vol_client")
    @Get()
    public ModelAndView getListeVol() {
        ModelAndView mav = new ModelAndView("/views/liste_vol_client");
         
        List<Vol> vols = new ArrayList<>();
     
        try {
            vols = Vol.getAll(null);
        } catch (Exception e) {
           e.printStackTrace();
        }
    
        mav.add("vols", vols);
        
        return mav;
    }


  @Url(value="/app/criteria")
  @Get()
  public ModelAndView findByCriteria() {
      ModelAndView mav = new ModelAndView("/views/rechercher");

      System.out.println("je suis dans rechercheByCriteria");
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



@Url(value="/app/resultat")
@Post()
public ModelAndView resultatRecherche(@RequestParameter("date1") String depart, 
                        @RequestParameter("date2") String arrivee,
                        @RequestParameter("avion") Integer id_avion,
                        @RequestParameter("depart") Integer ville_depart, 
                        @RequestParameter("arrivee") Integer ville_arrive) {

    ModelAndView mav = new ModelAndView("/views/recherche_result");
    List<Vol> vols = new ArrayList<>();
    try {
    vols = Vol.rechercherVols(null, depart, arrivee, id_avion, ville_depart, ville_arrive);
    System.out.println("taillde des resultats : " + vols.size());
    
    } catch (Exception e) {
       e.printStackTrace();
    }
    mav.add("vols",vols);
    return mav;
}


}
