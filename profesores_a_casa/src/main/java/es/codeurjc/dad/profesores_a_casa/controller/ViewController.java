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
    @GetMapping("/logIn")
    public String inicioSesion(Model model){
        model.addAttribute("Incorrect",false);
        model.addAttribute("error", null);
        return "LogIn";
    }
    @PostMapping("/logIn")
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
        return "LogIn";
    }  
    @GetMapping("/logout")
    public String getLogout(Model model,HttpSession session,Pageable pageable){
        session.invalidate();
        service.setUpOfPosts(model,pageable,null,false);
        return "Home";
    }

    //SignUp:
    @GetMapping("/signUp")
    public String registro(Model model){
        model.addAttribute("Incorrect", false);
        model.addAttribute("error",null);
        return "SignUp";
    }
    @PostMapping("/signUp")
    public String signIn(Model model,HttpSession session,String email,String log,String pass){
        Optional<User> u=users.findUser(log);
        if(u.isPresent()){
            model.addAttribute("Incorrect",true);
            model.addAttribute("error","El nombre ya esta en uso");
            return "SignUp";
        }
        User newUser=new User(log,pass,email);
        users.save(newUser);
        session.setAttribute("User",newUser);
        model.addAttribute("User",newUser);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //Perfiles:
    @GetMapping("/myProfile")
    public String miPerfil(Model model,HttpSession session){
        return service.setUpMiPerfil(model,session);
    }
    @GetMapping("/otherProfile")
    public String verPerfil(Model model,HttpSession session,@RequestParam long userId){
        User u=(User)session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> user=users.findUser(userId);
        return service.setUpPerfil(model, session, user);
    }

    //Posts:
    @GetMapping("/post")
    public String mostrarOferta(Model model, HttpSession session,@RequestParam long postId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            model.addAttribute("Post", post.get());
            model.addAttribute("rankingAverage", post.get().getRanking().getAverage());
            return "Post";
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/newPost")
    public String newPostForm(Model model,HttpSession session){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        return "NewPost";
    }
    @PostMapping("/createPost")
    public String newPost(Model model,HttpSession session,@RequestParam String title, @RequestParam String description, @RequestParam double price){
        User u=(User) session.getAttribute("User");
        Post post = new Post(title,description,price);
        post.setRanking(new Ranking(1,1));
        posts.save(post);
        u.addPost(post);
        users.save(u);
        posts.save(post);
        session.setAttribute("User", u);
        return service.setUpMiPerfil(model, session);
    } 
    @GetMapping("/rank")
    public String rank(Model model,HttpSession session,@RequestParam int punt,@RequestParam long postId){
        User u=(User)session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            Ranking r = post.get().getRanking();
            r.incrementScore(punt);
            post.get().setRanking(r);
            posts.save(post.get());
            model.addAttribute("Post", post.get());
            model.addAttribute("rankingAverage", post.get().getRanking().getAverage());
            return "Post";
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //Reports:
    @GetMapping("/newReport")
    public String nuevaDenuncia(Model model,HttpSession session,@RequestParam long postId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        session.setAttribute("Post",postId);
        return "NewReport";
    }
    @PostMapping("/report")
    public String guardarDenuncia(Model model,HttpSession session,@RequestParam String motive, @RequestParam String description){
        User u=(User) session.getAttribute("User");
        Optional<Post> post=posts.findPost((long)session.getAttribute("Post"));
        if(post.isPresent()){
            Post p=post.get();
            Report report = new Report(motive, description);
            report.setAuthor(u);
            p.addReport(report);
            users.save(u);
            posts.save(p);
            reports.save(report);
        }
        model.addAttribute("User",u);
        session.setAttribute("Post",null);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

    //Contratos:
    @GetMapping("/newContract")
    public String nuevoContrato(Model model,HttpSession session,@RequestParam long postId,@RequestParam long studentId,@RequestParam long teacherId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> s=users.findUser(studentId),t;
        Optional<Post> p;
        if(s.isPresent()){
            t=users.findUser(teacherId);
            if(t.isPresent()){
                p=posts.findPost(postId);
                if(p.isPresent()){
                    model.addAttribute("Post",p.get());
                    model.addAttribute("Teacher",t.get());
                    model.addAttribute("Student",s.get());
                    return "NewContract";
                }
            }
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/contract")
    public String guardadContrato(Model model,HttpSession session,@RequestParam long postId,@RequestParam long studentId,@RequestParam long teacherId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> s=users.findUser(studentId),t;
        Optional<Post> p;
        if(s.isPresent()){
            t=users.findUser(teacherId);
            if(t.isPresent()){
                p=posts.findPost(postId);
                if(p.isPresent()){
                    Contract contract=new Contract();
                    contracts.save(contract);
                    s.get().addContractAsStudent(contract);
                    t.get().addContractAsTeacher(contract);
                    p.get().addContract(contract);
                    users.save(s.get());
                    users.save(t.get());
                    posts.save(p.get());
                    contracts.save(contract); 
                    service.setUpMiPerfil(model,session);
                }
            } 
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }

}