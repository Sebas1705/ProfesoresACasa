package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.security.Principal;
import java.util.Optional;

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
public class ReportViewController {
    
    @Autowired private ReportService reports;
    @Autowired private PostService posts;
    @Autowired private UserService users;
    @Autowired private RabbitMQProducer notifications;

    @GetMapping("/newReport")
    public String formReport(Model model,HttpServletRequest request,@RequestParam long postId){
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()) model.addAttribute("User",user.get());
        }
        model.addAttribute("Post",postId);
        return "NewReport";
    }
    @PostMapping("/report")
    public String createReport(Model model,HttpServletRequest request,@RequestParam String motive,@RequestParam String description,@RequestParam long postId){
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> userP=users.findUser(principal.getName());
            if(userP.isPresent()){
                model.addAttribute("User",userP.get());
                Optional<Post> post=posts.findPost(postId);
                if(post.isPresent()){
                    User user=userP.get();
                    Post p=post.get();
                    Report report = new Report(motive, description);
                    report.setAuthor(user);
                    p.addReport(report);
                    users.save(user);
                    posts.save(p);
                    reports.save(report);
                    notifications.sendMessage("R-"+user.getEmail()+
                                                "-"+p.getTitle()+
                                                "-"+motive+
                                                "-"+description);
                }
            }
        }
        return "redirect:/";
    }
}
