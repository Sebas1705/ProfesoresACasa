package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.dad.profesores_a_casa.service.*;
import es.codeurjc.dad.profesores_a_casa.model.*;
@Controller
public class LogInController {
    
    @Autowired private GeneralService service;
    @Autowired private UserService users;

    @GetMapping("/logIn")
    public String inicioSesion(Model model){
        model.addAttribute("Incorrect",false);
        model.addAttribute("error", null);
        return "LogIn";
    }
    @PostMapping("/logIn")
    public String getLogin(Model model,HttpSession session,String logname,String password){
        Optional<User> user=users.findUser(logname);
        if(user.isPresent()&&user.get().getPassword().equals(password)){
            session.setAttribute("User",user.get());
            model.addAttribute("User", user.get());
            service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
            return "Home";
        }
        model.addAttribute("Incorrect",true);
        model.addAttribute("error","No existe el usuario o la contraseña no es correcta");
        return "LogIn";
    }  
    @GetMapping("/logout")
    public String getLogout(Model model,HttpSession session){
        session.invalidate();
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
}
