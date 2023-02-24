package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.codeurjc.dad.profesores_a_casa.model.User;
import es.codeurjc.dad.profesores_a_casa.service.GeneralService;

@Controller
public class HomeController {
    
    @Autowired private GeneralService service;

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
}
