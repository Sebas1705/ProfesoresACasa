package es.codeurjc.dad.profesores_a_casa.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
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
        service.autoInitDBTest(25);
    }


    //Home:
    @GetMapping("/")
    public String home(Model model,HttpSession session,Pageable pageable){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        service.setUpOfPosts(model,pageable,null,false);
        return "Home";
    }


    //LogIn:
    @GetMapping("/InicioSesion")
    public String inicioSesion(Model model){
        model.addAttribute("Incorrect",false);
        model.addAttribute("error", null);
        return "InicioSesión";
    }
    @PostMapping("/log")
    public String getLog(Model model,HttpSession session,String log,String pass){
        Optional<User> u=users.findUser(log);
        if(u.isPresent()&&u.get().getPassword().equals(pass)){
            session.setAttribute("User",u.get());
            model.addAttribute("User", u.get());
            service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
            return "Home";
        }
        model.addAttribute("Incorrect",true);
        model.addAttribute("error","No existe el usuario o la contraseña no es correcta");
        return "InicioSesión";
    }  
    @GetMapping("/logout")
    public String getLogout(Model model,HttpSession session,Pageable pageable){
        session.invalidate();
        service.setUpOfPosts(model,pageable,null,false);
        return "Home";
    }

    //SignUp:
    @GetMapping("/Registro")
    public String registro(Model model){
        model.addAttribute("Incorrect", false);
        model.addAttribute("error",null);
        return "Registro";
    }
    @PostMapping("/signIn")
    public String signIn(Model model,HttpSession session,String email,String log,String pass){
        Optional<User> u=users.findUser(log);
        if(u.isPresent()){
            model.addAttribute("Incorrect",true);
            model.addAttribute("error","El nombre ya esta en uso");
            return "Registro";
        }
        User newUser=new User(log,pass,email);
        users.save(newUser);
        session.setAttribute("User",newUser);
        model.addAttribute("User",newUser);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //Perfiles:
    @GetMapping("/MiPerfil")
    public String miPerfil(Model model,HttpSession session){
        User user = (User) session.getAttribute("User");
        if(user!=null){
            model.addAttribute("User", user);
            
            return "VerPerfil";
        }
        session.invalidate();
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    @GetMapping("/NuevaOferta")
    public  String NuevaOferta(Model model){
        return "NuevaOferta";
    }

    @PostMapping("/Oferta")
    public String guardarOferta(@RequestParam String titulo, @RequestParam String descripcion, @RequestParam double precio){
        Post post = new Post(titulo, descripcion, precio);
        posts.save(post);
        return "Oferta";
    }

    @GetMapping("/Oferta")
    public String mostrarOfertas(Model model, HttpSession session,Pageable pageable,@RequestParam long id){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<Post> post = posts.findPost(id);
        if(post.isPresent()){
            model.addAttribute("Post", post.get());
            model.addAttribute("rankingAverage", post.get().getRanking().getAverage());
            model.addAttribute("id_s", u.getId());
            return "Oferta";
        }
        service.setUpOfPosts(model,pageable,null,false);
        return "Home";
    }
    
    @GetMapping("/NuevaDenuncia")
    public String NuevaDenuncia(Model model){
        return "NuevaDenuncia";
    }

    @PostMapping("/Denuncias")
    public String guardarDenuncia(Model model,HttpSession session, @RequestParam String motive, @RequestParam String description){
        Report report = new Report(motive, description);
        reports.save(report);
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    @PostMapping("/NuevoContrato")
    public String NuevoContrato(Model model,HttpSession session, @RequestParam long postId,
                                     @RequestParam long student, @RequestParam long teacher){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);

        Optional<User> s = users.findUser(student);
        Optional<User> t = users.findUser(teacher);
        Optional<Post> p = posts.findPost(postId);
        if(s.isPresent() && t.isPresent() && p.isPresent()){
            Contract contract = new Contract();
            contracts.save(contract);
            s.get().addContractAsStudent(contract);
            t.get().addContractAsTeacher(contract);
            p.get().addContract(contract);
            users.save(s.get());
            users.save(t.get());
            posts.save(p.get());
            contracts.save(contract);
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    @PostMapping("/Contratos")
    public String guardarContrato(Model model,HttpSession session, @RequestParam String motive, @RequestParam String description){
        Report report = new Report(motive, description);
        reports.save(report);
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    @GetMapping("/Contratos")
    public String contratos(Model model){
        return "Contratos";
    }

}