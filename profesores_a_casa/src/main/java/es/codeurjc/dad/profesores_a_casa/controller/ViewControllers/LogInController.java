package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LogInController {

    @GetMapping("/logIn")
    public String inicioSesion(Model model){
        model.addAttribute("Incorrect",false);
        model.addAttribute("error", null);
        return "LogIn";
    } 
    @GetMapping("/logInError")
    public String logInError(Model model){
        model.addAttribute("Incorrect",true);
        model.addAttribute("error", "No existe el usuario o la contraseña no es correcta");
        return "LogIn";
    } 

}
