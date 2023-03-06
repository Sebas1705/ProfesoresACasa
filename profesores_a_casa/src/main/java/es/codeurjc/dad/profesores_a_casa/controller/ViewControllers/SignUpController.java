package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class SignUpController {
    
    @Autowired private GeneralService service;
    @Autowired private UserService users;

    @GetMapping("/signUp")
    public String getSignUp(Model model){
        model.addAttribute("Incorrect", false);
        model.addAttribute("error",null);
        return "SignUp";
    }
    @PostMapping("/signUp")
    public String signUp(Model model,HttpSession session,String email,String logname,String password){
        Optional<User> user=users.findUser(logname);
        if(user.isPresent()){
            model.addAttribute("Incorrect",true);
            model.addAttribute("error","El nombre ya esta en uso");
            return "SignUp";
        }
        User newUser=new User(logname,password,email);
        users.save(newUser);
        session.setAttribute("User",newUser);
        model.addAttribute("User",newUser);
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
}
