package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.dad.profesores_a_casa.service.GeneralService;

@Controller
public class PostViewController {

    @Autowired private GeneralService service;

    @GetMapping("/post")
    public String showPost(Model model,HttpSession session,@RequestParam long postId){
        return service.showPost(model,session,postId);
    }
    @GetMapping("/newPost")
    public String formPost(Model model,HttpSession session){
        return service.getPostPage(model,session);
    }
    @PostMapping("/createPost")
    public String createPost(Model model,HttpSession session,@RequestParam String title, @RequestParam String description, @RequestParam double price){
        return service.makeNewPost(model,session,title,description,price);
    } 
    @GetMapping("/rank")
    public String rank(Model model,HttpSession session,@RequestParam int punt,@RequestParam long postId){
        return service.rankPost(model,session,postId,punt);
    }
    @GetMapping("/deletePost")
    public String deletePost(Model model,HttpSession session,@RequestParam long postId){
        return service.deletePost(model,session,postId);
    }
}
