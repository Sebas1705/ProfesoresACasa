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
        return "Home";
    }
    @GetMapping("/InicioSesión")
    public String inicioSesion(Model model){
        return "InicioSesión";
    }

    @GetMapping("/Registro")
    public String registro(Model model){
        return "Registro";
    }

    @GetMapping("/HomeLog")
    public String homeLog(Model model){
        return "HomeLog";
    }

    @GetMapping("/PersonalizarPerfil")
    public String personalizarPerfil(Model model){
        return "PersonalizarPerfil";
    }

    @GetMapping("/VerPerfil")
    public String VerPerfil(Model model){
        return "VerPerfil";
    }

    @GetMapping("/Contratos")
    public String contratos(Model model){
        return "Contratos";
    }

    @GetMapping("/Oferta")
    public String oferta(Model model){
        return "Oferta";
    }

    @GetMapping("/Denuncias")
    public String denuncias(Model model){
        return "Denuncias";
    }

    @GetMapping("/NuevaOferta")
    public  String NuevaOferta(Model model){
        return "NuevaOferta";
    }

    @GetMapping("/NuevaDenuncia")
    public String NuevaDenuncia(Model model){
        return "NuevaDenuncia";
    }

    @GetMapping("/NuevoContrato")
    public String NuevoContrato(Model model){
        return "NuevoContrato";
    }



}