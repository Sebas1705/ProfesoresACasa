package es.codeurjc.dad.profesores_a_casa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.service.PostService;

@Controller
public class ViewController {

    @Autowired
    private PostService posts;

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
    public String Oferta(Model model){
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

    @PostMapping("/Oferta")
    public String guardarOferta(@RequestParam String oferta, @RequestParam String descripcion, @RequestParam double precio){
        Post post = new Post(oferta, descripcion, precio);
        posts.save(post);
        return "Oferta";
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