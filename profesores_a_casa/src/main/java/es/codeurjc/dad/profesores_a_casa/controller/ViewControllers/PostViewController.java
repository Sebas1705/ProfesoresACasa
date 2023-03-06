package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class PostViewController {

    @Autowired private GeneralService service;
    @Autowired private UserService users;
    @Autowired private PostService posts;

    @GetMapping("/post")
    public String showPost(Model model,HttpSession session,@RequestParam long postId){
        User user=(User) session.getAttribute("User");
        model.addAttribute("User",user);
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            model.addAttribute("Post", post.get());
            model.addAttribute("rankingAverage",post.get().getRanking().getAverage());
            return "Post";
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/newPost")
    public String formPost(Model model,HttpSession session){
        User user=(User)session.getAttribute("User");
        model.addAttribute("User",user);
        return "NewPost";
    }
    @PostMapping("/createPost")
    public String createPost(Model model,HttpSession session,@RequestParam String title, @RequestParam String description, @RequestParam double price){
        User user=(User) session.getAttribute("User");
        Post post = new Post(title,description,price);
        post.setRanking(new Ranking(1,1));
        posts.save(post);
        user.addPost(post);
        users.save(user);
        posts.save(post);
        session.setAttribute("User", user);
        return service.setUpMiPerfil(model, session);
    } 
    @GetMapping("/rank")
    public String rank(Model model,HttpSession session,@RequestParam int punt,@RequestParam long postId){
        User user=(User)session.getAttribute("User");
        model.addAttribute("User",user);
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            Ranking r = post.get().getRanking();
            r.incrementScore(punt);
            post.get().setRanking(r);
            posts.save(post.get());
            model.addAttribute("Post", post.get());
            int avg=post.get().getRanking().getAverage();
            List<Integer> stars=new ArrayList<Integer>();
            for(int i=1;i<=avg&&i<=5;i++)stars.add(i);
            model.addAttribute("Stars",stars);
            return "Post";
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/deletePost")
    public String deletePost(Model model,HttpSession session,@RequestParam long postId){
        User user=(User)session.getAttribute("User");
        Optional<Post> post_p=posts.findPost(postId);
        Post post=post_p.get();
        if(posts.exists(post)){
            user=post.getOwnerUser();
            user.removePost(post);
            posts.deletePost(post.getId());
            users.save(user);
        } 
        session.setAttribute("User",user);
        model.addAttribute("User",user);
        return service.setUpMiPerfil(model,session);
    }
}
