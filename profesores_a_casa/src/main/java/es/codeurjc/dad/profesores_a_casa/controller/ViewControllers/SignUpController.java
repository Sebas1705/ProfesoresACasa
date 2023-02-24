package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.dad.profesores_a_casa.service.GeneralService;

@Controller
public class SignUpController {
    
    @Autowired private GeneralService service;

    @GetMapping("/signUp")
    public String getSignUp(Model model){
        return service.getSignUpPage(model);
    }
    @PostMapping("/signUp")
    public String signUp(Model model,HttpSession session,String email,String logname,String password){
        return service.makeSignUp(model,session,logname,password,email);
    }
}
