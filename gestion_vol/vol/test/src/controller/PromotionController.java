package src.controller;

import src.connection.DbConnection;
import mg.controller.principal.*; // Importez l'annotation Controller
import mg.annotation.*;
import mg.tool.*;

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
public class PromotionController {
    
    @Url(value="/app/promotion")
    @Get()
    public ModelAndView showFormulaire() {
        ModelAndView mav = new ModelAndView("/views/liste_promotion");

        List<Promotion> promotion = new ArrayList<>();
     
        try {
            Connection c = DbConnection.getConnection();
            promotion =  Promotion.getAll(c);
        } catch (Exception e) {
           e.printStackTrace();
        }

        mav.add("promo",promotion);        
        return mav;
    }

}
