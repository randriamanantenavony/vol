package src.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import mg.annotation.Controller;
import mg.annotation.Get;
import mg.annotation.Url;
import mg.tool.ModelAndView;
import src.connection.DbConnection;
import src.models.ReservationDetails;
import src.models.Vol;

@Controller
public class ReservationDetailsController {
    
@Url(value="/app/list_reservation")
@Get()
public ModelAndView getListeReservation() {
    ModelAndView mav = new ModelAndView("/views/liste_reservation");
    List<ReservationDetails> reservations = new ArrayList<>();
 
    try {
        Connection c = DbConnection.getConnection();
        reservations = ReservationDetails.getReservationDetails(c);
    } catch (Exception e) {
       e.printStackTrace();
    }

    mav.add("reservations", reservations);
    
    return mav;
}


}
