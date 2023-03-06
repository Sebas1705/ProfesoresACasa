package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

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
public class ReportViewController {
    
    @Autowired private GeneralService service;
    @Autowired private ReportService reports;
    @Autowired private PostService posts;
    @Autowired private UserService users;

    @GetMapping("/newReport")
    public String formReport(Model model,HttpSession session,@RequestParam long postId){
        User user=(User) session.getAttribute("User");
        model.addAttribute("User",user);
        session.setAttribute("Post",postId);
        return "NewReport";
    }
    @PostMapping("/report")
    public String createReport(Model model,HttpSession session,@RequestParam String motive,@RequestParam String description){
        User user=(User) session.getAttribute("User");
        Optional<Post> post=posts.findPost((long)session.getAttribute("Post"));
        if(post.isPresent()){
            Post p=post.get();
            Report report = new Report(motive, description);
            report.setAuthor(user);
            p.addReport(report);
            users.save(user);
            posts.save(p);
            reports.save(report);
        }
        model.addAttribute("User",user);
        session.setAttribute("Post",null);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
}
