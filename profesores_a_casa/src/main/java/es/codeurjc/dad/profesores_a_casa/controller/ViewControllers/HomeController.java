package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.model.User;
import es.codeurjc.dad.profesores_a_casa.service.DataBaseInitialazer;
import es.codeurjc.dad.profesores_a_casa.service.PostService;
import es.codeurjc.dad.profesores_a_casa.service.UserService;

@Controller
public class HomeController {
    
    @Autowired private DataBaseInitialazer service;
    @Autowired private UserService users;
    @Autowired private PostService posts;

    @PostConstruct
    public void init(){
        //service.autoInitDBTest(25);
    }

    //Home:
    @GetMapping("/")
    public String home(Model model,HttpServletRequest request,Pageable pageable){
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()) model.addAttribute("User",user.get());
        }
        Page<Post> post=posts.getPage(PageRequest.of(pageable.getPageNumber(),10));
        model.addAttribute("isPerfil",false);
        model.addAttribute("posts",post);
        model.addAttribute("hasPrev",post.hasPrevious());
        model.addAttribute("hasNext",post.hasNext());	
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
}
