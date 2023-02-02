package es.codeurjc.dad.profesores_a_casa.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    

    @GetMapping("/")
    public String home(Model model, HttpSession sesion){
        sesion.getId();
        return "index";
    }

    
}

