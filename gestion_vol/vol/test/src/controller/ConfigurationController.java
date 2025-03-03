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
public class ConfigurationController {
    
    @Url(value="/app/showConfig")
    @Get()
    public ModelAndView showFormulaire() {
        ModelAndView mav = new ModelAndView("/views/configuration");
        return mav;
    }

@Url(value="/app/config")
@Post()
public ModelAndView saveConfig(@RequestParameter("heure1") Integer reservation, @RequestParameter("heure2") Integer annulation) {

    ModelAndView mav = new ModelAndView("/views/login_reussi");

    try {
       Configuration c = new Configuration();
       c.setIdHeureLimiteReservation(reservation);
       c.setIdHeureLimiteAnnulation(annulation);
       c.save();
   } catch (Exception e) {
       e.printStackTrace();
    }

    return mav;
}

}
