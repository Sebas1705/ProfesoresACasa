package es.codeurjc.dad.profesores_a_casa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.dad.profesores_a_casa.service.ExampleService;

@Controller
public class HomeController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/")
    public String home(Model model, HttpSession sesion){
        sesion.getId();
        return "index";
    }

    @GetMapping("/pagina")
    public String pagina(Model model){
        model.addAttribute("hola","Buenas");
        return "pagina";
    }
}