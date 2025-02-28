package src.controller;

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



@Controller
public class LoginController {

    @Url(value="/app/login")
    @Get()
    public ModelAndView showFormulaire() {
        ModelAndView mav = new ModelAndView("/views/login");
        return mav;
    }

    @Url(value="/app/SubmitForm")
    @Post()
    public ModelAndView validateForm(@RequestParameter("email") String mailString,@RequestParameter("mdp") String mdpString,@ModelAttribute MySession mysession) throws ClassNotFoundException, SQLException {
       
       ModelAndView reussi = new ModelAndView("/views/login_reussi");
       ModelAndView echec = new ModelAndView("/views/login");

       Connection c = DbConnection.getConnection();
       
       Admin admin = new Admin(mailString,mdpString);
       boolean isLogged = admin.verifierLogin(c);

       if (isLogged) {
            mysession.add("userSessionKey",true);
            System.out.println("Session 1 cree");
            mysession.add("userRole","admin");
            System.out.println("Session 2 cree");
            
       }
       else{
           System.out.println("Login ou mot de passe incorrect");
           return echec;
       }
       return reussi;
    }    
}
