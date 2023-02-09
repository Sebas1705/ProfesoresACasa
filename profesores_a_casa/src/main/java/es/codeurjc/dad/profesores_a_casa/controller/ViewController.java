package es.codeurjc.dad.profesores_a_casa.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    

    @GetMapping("/")
    public String home(Model model, HttpSession sesion){
        sesion.getId();
        return "index";
    }
    @GetMapping("/inicioSesion")
    public String inicioSesion(Model model){
        return "formularioInicioSesion";
    }

    @GetMapping("/registro")
    public String registro(Model model){
        return "registro";
    }

    @GetMapping("/homeLog")
    public String homeLog(Model model){
        return "homeLog";
    }

    @GetMapping("/pagina")
    public String pagina(Model model){
        model.addAttribute("hola","Buenas");
        return "pagina";
    }
}