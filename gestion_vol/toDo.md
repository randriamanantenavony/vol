## Conception MCD
- admin :
   - id
   - mail
   - mdp

- modele :
   - id
   - libelle

- type_siege :
   - id
   - libelle

- avion :
   - id
   - nom
   - id_modele
   - date_fabrication

- avion_detail :
   - id
   - id_avion
   - id_type_siege
   - nombre

- passager :
   - id
   - nom
   - prenom
   - mail

- ville :
   - id
   - nom
   - pays

- vol :
   - id
   - dateHeureDepart
   - dateHeureArrivee
   - id_avion
   - id_ville_depart
   - id_ville_arrivee

- vol_detail :
   - id
   - id_vol
   - id_type_siege
   - prix
   - nombre disponible

- etat_reservation :
   - id
   - libelle

- reservation :
   - id
   - dateHeure
   - id_passager
   - id_etat_reservation

- reservation_detail :
   - id_reservation_detail
   - id_reservation
   - id_vol
   - id_type_siege
   - nombre

- promotion :
   - id
   - dateHeure
   - date_expiration
   - id_vol
   - id_type_siege
   - nombre
   - pourcentage_promotion

- reservation_configuration:
   - id
   - heure_limite_reservation
   - heure_limite_annulation

## To do list :
   # préparation du projet  : ok
   - initialisation du projet 
      - vue,lib, controller, models,base
   - recherche template     : ok
   - intégration du template dans le projet : ok
   - deploiement vers tomcat : ok
   - test dans tomcat si tout marche bien : ok


   # login :
   - Affichage : mail, mdp, valider  : ok
   - Metier :    ok
      - Login
      - Connection
   - Controller : appel de la fonction login : ok
      - si true : creation de la session 
      - si false : renvoi du message d'erreur

   # CRUD vol : 
      * Insertion : ok
         - Affichage :   ok
            - Date et heure de départ
            - Date et heure d’arrivée
            - Sélection d’un avion
            - Sélection de la ville de départ
            - Sélection de la ville d’arrivée
            - Bouton "Enregistrer"   
         - Metier :  ok 
            - Avion
            - Ville 
            - Vol : 

         - Controller :   ok
            - VolController 
               - save, showFormulaire

         - Base : ok
           - modele,avion,ville + insertion de données : ok

      * Read :    ok
         - Affichage : 
            - list vol 
         - Metier :
            - Vol : getAll
         - Controller :
            - dispatch vers la vue  
         - Base : vue v_detail_vol  : ok
      
      * Delete : 
         - Affichage :  ok
            - list vol + delete
         - Metier :
            - Vol : delete(id)
         - Controller :
            - recuperation id vol 
            - appel de la fonction delete
      
   
      * Update : 
         - Affichage : ok
               - list vol + bouton update 
            - Metier : ok
               - Vol : getById,update(vol,id)
            - Controller : ok
               - dispatch vers la vue 
               - update lors de la validation

   ## Recherche multi-critères sur un vol :  ok
      - Affichage : 
         - input heure depart et arrivee
         - ville depart
         - ville arrivee
         - avion

      - Metier : getByCriteria()
      - Controller : 
         - reception des données
         - appel de la fonction getByCriteria()

   ## configuration promotion  : ok 
       - Affichage : 
          - input pourcentage
          - bouton valider    
       - Metier : 
          - Promotion : save()
       - Controller : 
          - reception données + insertion 
 
   ## configuration reservation  : ok
       - Affichage : 
          - input duree
          - bouton valider    
       - Metier : 
          - Reservation : save()
       - Controller : 
          - reception données + insertion
     
   ## liste des reservations 
       - affichage
       - metier : classe Reservation : getAll
       - controller : dispatch vers la vue
       - base : v_detail_reservation


   ## configuration annulation reservation
       - Affichage : 
          - input duree
          - bouton valider    
       - Metier : 
          - Promotion : save()
       - Controller : 
          - reception données + insertion

   ## insertion de promotion par vol
      - Affichage :
         - liste vol disponibles
         - nb siege promotion
         - pourcentage promotion par siege

   ## login client 
            - Affichage : mail, valider  :  ok
            - Metier :   ok
               - Login
               - Connection
            - Controller : appel de la fonction login :  ok
               - si true : creation de la session 
               - si false : renvoi du message d'erreur

   ## Reservation d'un vol :
      - Affichage : 
         - Liste des vols disponibles
         - Sélection du type de siège
         -  Champ "Nombre de places"
         - input image passeport
         -  Bouton "Réserver"
      - Métier : 
         - typeSiege
         - Reservation : save, getAll
         - contrle données : heure de reservation
      - Controller :
         - reception données + insertion dans la base
      - Base : 
         - lister les vols disponibles :
         - modifier la table reservation ajouter imagepath
          

   ## Annulation d'un vol :  ok
      - Affichage : 
         - Liste des vols 
         - Bouton "Annuler"
      - Métier : 
         - contrle données : heure de fin annulation reservation
         - Reservation : save()
      - Controller :
         - reception données + changement de statut dans la base


   ## upload fichier :
      - image d'un passeport lors d'une reservation

   ## reponse api : getConfiguration actuel

   ## a tester : 
       - delete
       - update
       - login client
       - insertion de reservation
       - liste de reservation 
       - annulation d'un vol
