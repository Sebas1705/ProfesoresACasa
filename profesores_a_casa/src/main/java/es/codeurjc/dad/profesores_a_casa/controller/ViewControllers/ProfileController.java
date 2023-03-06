package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

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
import es.codeurjc.dad.profesores_a_casa.service.*;
import es.codeurjc.dad.profesores_a_casa.model.*;

@Controller
public class ProfileController {
    
    @Autowired private GeneralService service;
    @Autowired private UserService users;
    @Autowired private PostService posts;
    @Autowired private ContractService contracts;

    @GetMapping("/myProfile")
    public String miPerfil(Model model,HttpSession session){
        User user = (User) session.getAttribute("User");
        if(user!=null){
            List<Post> lPosts=posts.findPosts(user);
            List<Contract> cT=contracts.findContractAsTeacher(user);
            List<Contract> cS=contracts.findContractAsStudent(user);
            model.addAttribute("posts",lPosts);
            model.addAttribute("cT",cT);
            model.addAttribute("cS",cS);
            model.addAttribute("User", user);
            model.addAttribute("isPerfil",true);
            return "MyProfile";
        }
        session.invalidate();
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/otherProfile")
    public String verPerfil(Model model,HttpSession session,@RequestParam long userId){
        User user=(User)session.getAttribute("User");
        model.addAttribute("User",user);
        Optional<User> userShow=users.findUser(userId);
        model.addAttribute("User", user);
        if(userShow.isPresent()){
            User u=userShow.get();
            List<Post> lPosts=posts.findPosts(u);
            model.addAttribute("posts",lPosts);
            model.addAttribute("UserShow",u);
            return "OtherProfile";
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/deleteUser")
    public String deleteUser(Model model,HttpSession session){
        User user=(User)session.getAttribute("User");
        users.delete(user.getId());
        session.invalidate();
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/changeDataUser")
    public String getChangePage(Model model,HttpSession session){
        User user=(User)session.getAttribute("User");
        model.addAttribute("User",user);
        return "EditUserProfile";
    }
    @PostMapping("/changeProfile")
    public String changeProfile(Model model,HttpSession session,@RequestParam String logname,@RequestParam String selfDescription){
        User user=(User)session.getAttribute("User");
        if(!users.findUser(logname).isPresent())user.setLogname(logname);
        user.setSelfDescription(selfDescription);
        users.save(user);
        session.setAttribute("User",user);
        return service.setUpMiPerfil(model,session); 
    }
}
