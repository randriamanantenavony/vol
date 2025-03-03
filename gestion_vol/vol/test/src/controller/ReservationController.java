package src.controller;

import mg.controller.principal.*; // Importez l'annotation Controller
import mg.annotation.*;
import mg.tool.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import src.models.*;
import mg.tool.*;
import mg.exception.*;
import src.connection.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class ReservationController {    

    @Url(value="/app/reservation")
    @Get()
    public ModelAndView reserver() {
        ModelAndView mav = new ModelAndView("/views/login_reussi_client");

        List<Vol> vols = new ArrayList<>();
        List<Typesiege> types = new ArrayList<>();
        try {
            Connection c = DbConnection.getConnection();
            vols = Vol.getAll(c);
            types = Typesiege.getAll(c);
        } catch (Exception e) {
           e.printStackTrace();
        }
      

        mav.add("vols",vols);
        mav.add("types",types);
        return mav;
    }


@Url(value="/app/save_reservation")
@Post()
public ModelAndView saveReservation(@RequestParameter("vol") Integer vol, 
                       @RequestParameter("typesiege") Integer typesiege,
                       @RequestParameter("siege") Integer siege,
                       @RequestParameter("passager") Integer passager,
                       @RequestParameter("passeport") MultiPart file) {

    ModelAndView mav = new ModelAndView("/views/login_reussi_client");
    try {
        System.out.println("Passager trouvé");

        Connection c = DbConnection.getConnection();
        LocalDateTime dateHeureReservation = LocalDateTime.now();

        boolean isDansLeTemps = ReservationDetail.verifierLimiteReservation(c, vol, dateHeureReservation);
        System.out.println("est dans le temps" + isDansLeTemps);

        if (!isDansLeTemps) {
            System.out.println("Temps limite dépassé pour la réservation.");
            return null;
        }

        System.out.println("Vous êtes dans le temps");

        // Vérification et enregistrement du fichier
        String fileName = null;
        if (file != null && file.getFileContent() != null && file.getFileSize() > 0) {
            System.out.println("file trouvee");
            try {
                String uploadDirectory = "E:\\uploads\\";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                fileName = file.getFileName();
                System.out.println("nom file " + fileName);
                String filePath = uploadDirectory + fileName;
                System.out.println("filepath : " + filePath);

                try (InputStream fileContent = file.getFileContent();
                     FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                System.out.println("fichier copie");
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }

        System.out.println("coucou reservation");
        // Enregistrement de la réservation
        Reservation reservation = new Reservation();
        reservation.setIdPassager(passager);
        reservation.setFileName(fileName);
        int id_reservation = reservation.insererReservation(c);

        System.out.println("Réservation insérée avec ID : " + id_reservation);

        // Enregistrement des détails de la réservation
        ReservationDetail detail = new ReservationDetail();
        detail.setIdReservation(id_reservation);
        detail.setIdTypeSiege(typesiege);
        detail.setIdVol(vol);
        detail.setNombre(siege);
        detail.insert(c);

    } catch (Exception e) {
        e.printStackTrace();
      }

      return mav;
    }

@Url(value="/app/annuler_reservation")
@Get()
public ModelAndView deleteReservation(@RequestParameter("id") Integer id_vol) {
    ModelAndView mav = new ModelAndView("list_reservation");
    try {
       ReservationDetail.deleteReservation(null, id_vol);
    } catch (Exception e) {
       e.printStackTrace();
    }
    return mav;
}


}
