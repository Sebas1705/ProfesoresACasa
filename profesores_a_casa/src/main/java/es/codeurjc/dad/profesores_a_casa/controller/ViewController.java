package es.codeurjc.dad.profesores_a_casa.controller;

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

    @Autowired private PostService posts;
    @Autowired private UserService users;
    @Autowired private ContractService contracts;
    @Autowired private RankingService rankings;
    @Autowired private ReportService reports;

    @PostConstruct
    public void init(){
        User ant=null;
        for(int i = 0;i<100;i++){
            Contract c=null;
            Report r=null;
            User u=new User("ExampleLogname_"+i,"Examplepassword_"+i);
            Post p=new Post("ExampleTitle"+i,"ExampleDescription"+i,99.99);
            if(i%5==0){
                c = new Contract(p,ant,u);
                contracts.save(c);
            }
            if(i%8==0){
                r=new Report("Motivo"+i,"Description"+i,p);
            }
            p.setRanking(new Ranking());
            rankings.save(p.getRanking());
            if(i%3==0){
                p.getRanking().setTotalScore(i*((int)Math.random()*100));
                p.getRanking().setNumRankings(i);    
            }
            if(c!=null)u.getContract().add(c);
            if(r!=null)u.getReports().add(r);
            users.save(u);
            p.setOwnerUser(u);
            posts.save(p);
            ant=u;
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