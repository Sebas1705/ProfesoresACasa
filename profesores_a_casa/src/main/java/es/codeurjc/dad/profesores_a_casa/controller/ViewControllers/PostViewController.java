package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class PostViewController {

    @Autowired private UserService users;
    @Autowired private PostService posts;
    @Autowired private RabbitMQProducer notifications;

    @GetMapping("/post")
    public String showPost(Model model,HttpServletRequest request,@RequestParam long postId){
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            Principal principal = request.getUserPrincipal();
            if(principal!=null) {
                Optional<User> user=users.findUser(principal.getName());
                if(user.isPresent()) model.addAttribute("User",user.get());
            }
            model.addAttribute("Post", post.get());
            model.addAttribute("rankingAverage",post.get().getRanking().getAverage());
            int avg=post.get().getRanking().getAverage();
            List<Integer> stars=new ArrayList<Integer>();
            for(int i=1;i<=avg&&i<=5;i++)stars.add(i);
            model.addAttribute("Stars",stars);
            return "Post";
        }
        return "redirect:/";
    }
    @GetMapping("/newPost")
    public String formPost(Model model,HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()) model.addAttribute("User",user.get());
        }
        return "NewPost";
    }
    @PostMapping("/createPost")
    public String createPost(Model model,HttpServletRequest request,@RequestParam String title, @RequestParam String description, @RequestParam double price) throws ServletException{
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> userP=users.findUser(principal.getName());
            if(userP.isPresent()){
                User user=userP.get();
                Post post = new Post(title,description,price);
                post.setRanking(new Ranking(1,1));
                posts.save(post);
                user.addPost(post);
                users.save(user);
                posts.save(post);
                notifications.sendMessage("I-"+post.getOwnerUser().getEmail()+
                                            "-"+title+
                                            "-"+description+
                                            "-"+price);       
            }
        }
        return "redirect:/myProfile";
    } 
    @GetMapping("/rank")
    public String rank(Model model,HttpServletRequest request,@RequestParam int punt,@RequestParam long postId){
        Optional<Post> post = posts.findPost(postId);
        if(post.isPresent()){
            Principal principal = request.getUserPrincipal();
            if(principal!=null) {
                Optional<User> user=users.findUser(principal.getName());
                if(user.isPresent()) model.addAttribute("User",user.get());
            }
            Ranking r = post.get().getRanking();
            r.incrementScore(punt);
            post.get().setRanking(r);
            posts.save(post.get());
            model.addAttribute("Post",post.get());
            int avg=post.get().getRanking().getAverage();
            List<Integer> stars=new ArrayList<Integer>();
            for(int i=1;i<=avg&&i<=5;i++)stars.add(i);
            model.addAttribute("Stars",stars);
            return "Post";
        }
        return "redirect:/";
    }
    @GetMapping("/deletePost")
    public String deletePost(Model model,HttpServletRequest request,@RequestParam long postId) throws ServletException{
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> userP=users.findUser(principal.getName());
            if(userP.isPresent()) model.addAttribute("User",userP.get());
            Optional<Post> post_p=posts.findPost(postId);
            Post post=post_p.get();
            if(posts.exists(post)){
                notifications.sendMessage("P-"+post.getOwnerUser().getEmail()+
                                            "-"+post.getTitle());
                User user=userP.get();
                user=post.getOwnerUser();
                user.removePost(post);
                posts.deletePost(post.getId());
                users.save(user);
            } 
        }
        return "redirect:/myProfile";
    }
}
