package src.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import src.connection.DbConnection;
import src.models.Admin;
import src.models.Passager;
import src.models.Vol;
import mg.controller.principal.*;
import mg.annotation.*;
import mg.tool.*;
import java.util.List;


@Controller
public class LoginClientController {
    
    @Url(value="/app/login_client")
    @Get()
    public ModelAndView showFormulaireClient() {
        ModelAndView mav = new ModelAndView("/views/login_client");
        return mav;
    }

    @Url(value="/app/SubmitForm_client")
    @Post()
    public ModelAndView validateForm(@RequestParameter("email") String mailString,@ModelAttribute MySession mysession) throws ClassNotFoundException, SQLException {
       
       ModelAndView reussi = new ModelAndView("/views/login_reussi_client");
       List<Vol> vols = new ArrayList<>();

       Connection c = DbConnection.getConnection();
       Passager p = Passager.login(c, mailString);
        if (p != null) {
            mysession.add("passager",p);
            System.out.println("Session 2 cree pour le client");  
            vols = Vol.getAll(c);
       }
       else{
           System.out.println("Login ou mot de passe incorrect");
           return null;
       }
       reussi.add("vols", vols);
       return reussi;
    } 

}
