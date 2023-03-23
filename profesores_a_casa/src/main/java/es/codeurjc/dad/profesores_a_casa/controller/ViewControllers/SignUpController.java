package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class SignUpController {
    
    @Autowired private UserService users;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RabbitMQProducer notifications;

    @GetMapping("/signUp")
    public String getSignUp(Model model){
        model.addAttribute("Incorrect", false);
        model.addAttribute("error",null);
        return "SignUp";
    }
    @PostMapping("/signUp")
    public String signUp(Model model,HttpServletRequest request,String email,String logname,String password) throws ServletException{
        Optional<User> user=users.findUser(logname);
        if(user.isPresent()){
            model.addAttribute("Incorrect",true);
            model.addAttribute("error","El nombre ya esta en uso");
            return "SignUp";
        }
        User newUser=new User(logname,passwordEncoder.encode(password),email);
        users.save(newUser);
        notifications.sendMessage("S-"+email+"-"+logname);
        request.login(logname, passwordEncoder.encode(password));
        return "redirect:/";
    }
}
