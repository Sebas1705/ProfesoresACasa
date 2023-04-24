package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.security.Principal;
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
import es.codeurjc.dad.profesores_a_casa.service.*;
import es.codeurjc.dad.profesores_a_casa.model.*;

@Controller
public class ProfileController {

    @Autowired private UserService users;
    @Autowired private PostService posts;
    @Autowired private ContractService contracts;
    @Autowired private RabbitMQProducer notifications;

    @GetMapping("/myProfile")
    public String miPerfil(Model model,HttpServletRequest request) throws ServletException{
        Principal principal = request.getUserPrincipal();
        if(principal!=null){
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()){
                model.addAttribute("User",user.get());
                List<Post> lPosts=posts.findPosts(user.get());
                List<Contract> cT=contracts.findContractAsTeacher(user.get());
                List<Contract> cS=contracts.findContractAsStudent(user.get());
                model.addAttribute("posts",lPosts);
                model.addAttribute("cT",cT);
                model.addAttribute("cS",cS);
                model.addAttribute("isPerfil",true);
                return "MyProfile";
            }
        } 
        request.logout();
        return "redirect:/";
    }
    @GetMapping("/otherProfile")
    public String verPerfil(Model model,HttpServletRequest request,@RequestParam long userId){
        Optional<User> userShow=users.findUser(userId);
        if(userShow.isPresent()){
            Principal principal = request.getUserPrincipal();
            if(principal!=null) {
                Optional<User> user=users.findUser(principal.getName());
                if(user.isPresent()) model.addAttribute("User",user.get());
            }
            User u=userShow.get();
            List<Post> lPosts=posts.findPosts(u);
            model.addAttribute("posts",lPosts);
            model.addAttribute("UserShow",u);
            return "OtherProfile";
        }
        return "redirect:/";
    }
    @GetMapping("/deleteUser")
    public String deleteUser(Model model,HttpServletRequest request) throws ServletException{
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()){
                String email=user.get().getEmail();
                String name=user.get().getLogname();
                model.addAttribute("User",user.get());
                users.delete(user.get().getId());
                notifications.sendMessage("D-"+email+"-"+name);
                request.logout();
            }
        }
        return "redirect:/";
    }
    @GetMapping("/changeDataUser")
    public String getChangePage(Model model,HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()) model.addAttribute("User",user.get());
        }
        return "EditUserProfile";
    }
    @PostMapping("/changeProfile")
    public String changeProfile(Model model,HttpServletRequest request,@RequestParam String logname,@RequestParam String selfDescription) throws ServletException{
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> userP=users.findUser(principal.getName());
            if(userP.isPresent()){
                User user = userP.get();
                if(!users.findUser(logname).isPresent())user.setLogname(logname);
                user.setSelfDescription(selfDescription);
                users.save(user);
                request.logout();
            } 
        }  
        return "redirect:/"; 
    }
}
