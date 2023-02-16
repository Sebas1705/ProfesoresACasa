package es.codeurjc.dad.profesores_a_casa.controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class ViewController {

    
    @Autowired private GeneralService service;
    @Autowired private UserService users;
    @Autowired private PostService posts;
    @Autowired private ReportService reports;
    @Autowired private ContractService contracts;
    
    @PostConstruct
    public void init(){
        service.autoInitDBTest(50);
    }


    @GetMapping("/")
    public String home(Model model,HttpSession session,Pageable pageable){
        service.setUpOfPosts(model,pageable,null,false);
        return "Home";
    }

    @PostMapping("/log")
    public String getLog(Model model,Pageable pageable,String logname,String password,String record){
        return "log";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "log";
    }


    @GetMapping("/InicioSesion")
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

    @GetMapping("/Oferta")
    public String mostrarOfertas(Model model, HttpSession sesion,Pageable pageable){
        User usuario = users.findUser(sesion.getId());
        if(usuario!=null){
            Page<Post> post=posts.getPagefromUser(usuario,pageable);
            model.addAttribute("Posts", post);
            model.addAttribute("hasPrev", post.hasPrevious());
            model.addAttribute("hasNext", post.hasNext());
            model.addAttribute("nextPage", post.getNumber()+1);
            model.addAttribute("prevPage", post.getNumber()-1);	
        }else{
            model.addAttribute("Posts", null);
        }
        return "Oferta";
    }
    
    @GetMapping("/NuevaDenuncia")
    public String NuevaDenuncia(Model model){
        return "NuevaDenuncia";
    }

    @GetMapping("/Denuncias")
    public String denuncias(Model model){
        return "Denuncias";
    }

    @PostMapping("/Denuncias")
    public String guardarDenuncia(Model model, @RequestParam String motive, @RequestParam String description){
        Report report = new Report(motive, description);
        reports.save(report);
        return "Denuncias";
    }

    @GetMapping("/NuevoContrato")
    public String NuevoContrato(Model model){
        return "NuevoContrato";
    }

    @GetMapping("/Contratos")
    public String contratos(Model model){
        return "Contratos";
    }

}