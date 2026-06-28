package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LogInController {

    @GetMapping("/logIn")
    public String logIn(Model model){
        model.addAttribute("Incorrect",false);
        model.addAttribute("error", null);
        return "LogIn";
    } 
    @GetMapping("/logInError")
    public String logInError(Model model){
        model.addAttribute("Incorrect",true);
        model.addAttribute("error", "User not found or incorrect password");
        return "LogIn";
    } 

}
