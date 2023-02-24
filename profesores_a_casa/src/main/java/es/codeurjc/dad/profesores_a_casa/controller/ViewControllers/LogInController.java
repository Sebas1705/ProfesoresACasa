package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.dad.profesores_a_casa.service.GeneralService;

@Controller
public class LogInController {
    
    @Autowired private GeneralService service;

    @GetMapping("/logIn")
    public String inicioSesion(Model model){
        return service.getLogPage(model);
    }
    @PostMapping("/logIn")
    public String getLogin(Model model,HttpSession session,String logname,String password){
        return service.makeLogIn(model,session,logname,password);
    }  
    @GetMapping("/logout")
    public String getLogout(Model model,HttpSession session){
        return service.makeLogOut(model,session);
    }
}
