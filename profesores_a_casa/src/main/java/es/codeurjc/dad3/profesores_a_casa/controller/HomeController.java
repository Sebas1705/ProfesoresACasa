package es.codeurjc.dad3.profesores_a_casa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(){
        return "index";
    }
}
