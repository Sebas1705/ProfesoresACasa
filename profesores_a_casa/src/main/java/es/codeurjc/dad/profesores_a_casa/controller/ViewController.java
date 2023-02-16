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

    @Autowired private PostService posts;
    @Autowired private UserService users;
    @Autowired private ContractService contracts;
    @Autowired private RankingService rankings;
    @Autowired private ReportService reports;

    //Casos 1:1->
    //  Bidireccional:
    //      Sin cascade: se guarda cada uno por separado y se hace el set
    //      Con cascade: se guarda solo el principal tras setear el secundario
    //Casos N:1->
    //  Bidireccional:
    //      Sin cascade: se guarda la entidad secundaria, se setea a la principal y se guarda la principal
    //      Con cascade: se hace funcion de add en la secundaria que fue mapeada

    @PostConstruct
    public void init(){
        for (int i=0;i<60;i++){
            User student=new User("ExampleLogname_0_"+i,"ExamplePassword_0_"+i,"ExampleEmail_0_"+i);
            User teacher=new User("ExampleLogname_1_"+i,"ExamplePassword_1_"+i,"ExampleEmail_1_"+i);
            users.save(student);
            users.save(teacher);
            Post post=new Post("ExampleTitle_"+i,"ExampleDescription_"+i,(Math.random()*50));
            post.setRanking(new Ranking(Math.random()*i,i));
            posts.save(post);
            Report report=new Report("ExampleMotive_"+i,"ExampleDescription_"+i);
            student.addReport(report);
            post.addReport(report);
            teacher.addPost(post);
            reports.save(report);
            Contract contract=new Contract("ExampleDescription_"+i);
            contracts.save(contract);
            student.addContractAsStudent(contract);
            teacher.addContractAsTeacher(contract);
            post.addContract(contract);
            users.save(student);
            users.save(teacher);
            posts.save(post);
            reports.save(report);
            contracts.save(contract);    
        }
    }


    @GetMapping("/")
    public String home(Model model,HttpSession sesion,Pageable pageable){
        
        model.addAttribute("Nuevo",sesion.isNew());
        
        Page<Post> post=posts.getPage(pageable);
		model.addAttribute("posts", post);
		model.addAttribute("hasPrev", post.hasPrevious());
		model.addAttribute("hasNext", post.hasNext());	
        model.addAttribute("prevPage",post.getNumber()-1);
        model.addAttribute("nextPage",post.getNumber()+1);	
		int totalpages=post.getTotalPages();
        List<Integer> indexNext=new ArrayList<Integer>();
        List<Integer> indexPrev=new ArrayList<Integer>();
        for(int i=post.getNumber()+1;i<totalpages;i++)indexNext.add(i);
        for(int i=0;i<post.getNumber();i++)indexPrev.add(i);
        model.addAttribute("actualPage",post.getNumber());
        model.addAttribute("prevPages",indexPrev);
        model.addAttribute("nextPages",indexNext);

        return "Home";
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
        List<User> usuario = users.findUser(sesion.getId());
        if(usuario.size() != 0){
            Page<Post> post=posts.getPage(pageable);
            model.addAttribute("Posts", usuario.get(0).getPosts());
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