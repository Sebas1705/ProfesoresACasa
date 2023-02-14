package es.codeurjc.dad.profesores_a_casa.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.model.User;
import es.codeurjc.dad.profesores_a_casa.service.PostService;
import es.codeurjc.dad.profesores_a_casa.service.UserService;

@Controller
public class ViewController {

    @Autowired private PostService posts;
    @Autowired private UserService users;

    @PostConstruct
    public void init(){
        for(int i = 0;i<100;i++){
            User u=new User("ExampleLogname_"+i,"Examplepassword_"+i);
            users.save(u);
            Post p=new Post("ExampleTitle"+i,"ExampleDescription"+i,99.99);
            p.setOwnerUser(u);
            posts.save(p);
        }
    }

    @GetMapping("/")
    public String home(Model model,HttpSession sesion,Pageable pageable){
        model.addAttribute("Nuevo",sesion.isNew());
        
        Page<Post> post=posts.getPage(pageable);
		model.addAttribute("posts", post);
		model.addAttribute("hasPrev", post.hasPrevious());
		model.addAttribute("hasNext", post.hasNext());
		model.addAttribute("nextPage", post.getNumber()+1);
		model.addAttribute("prevPage", post.getNumber()-1);		
		
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