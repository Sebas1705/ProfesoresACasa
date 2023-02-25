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
public class ProfileController {
    
    @Autowired private GeneralService service;

    @GetMapping("/myProfile")
    public String miPerfil(Model model,HttpSession session){
        return service.setUpMiPerfil(model,session);
    }
    @GetMapping("/otherProfile")
    public String verPerfil(Model model,HttpSession session,@RequestParam long userId){
        return service.setUpPerfil(model,session,userId);
    }
    @GetMapping("/deleteUser")
    public String deleteUser(Model model,HttpSession session){
        return service.deleteUser(model,session);
    }
    @GetMapping("/changeDataUser")
    public String getChangePage(Model model,HttpSession session){
        return service.getChangePage(model,session);
    }
    @PostMapping("/changeProfile")
    public String changeProfile(Model model,HttpSession session,@RequestParam String logname,@RequestParam String selfDescription){
        return service.changeProfile(model,session,logname,selfDescription);
    }
}
