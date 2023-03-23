package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class ContractViewController {
    
    @Autowired private UserService users;
    @Autowired private PostService posts;
    @Autowired private ContractService contracts;
    @Autowired private RabbitMQProducer notifications;

    //Contratos:
    @GetMapping("/newContract")
    public String formContract(Model model,HttpServletRequest request,@RequestParam long postId,@RequestParam long teacherId){
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()){
                model.addAttribute("User",user.get());
                Optional<User> teacher=users.findUser(teacherId);
                if(teacher.isPresent()){
                    Optional<Post> post=posts.findPost(postId);
                    if(post.isPresent()){
                        model.addAttribute("Post",post.get());
                        model.addAttribute("Teacher",teacher.get());
                        model.addAttribute("Student",user.get());
                        return "NewContract";
                    }
                }
            }
        }
        return "redirect:/";
    }
    @GetMapping("/contract")
    public String createContract(Model model,HttpServletRequest request,@RequestParam long postId,@RequestParam long teacherId) throws ServletException{
        Principal principal = request.getUserPrincipal();
        if(principal!=null) {
            Optional<User> user=users.findUser(principal.getName());
            if(user.isPresent()){
                model.addAttribute("User",user.get());
                Optional<User> teacher=users.findUser(teacherId);
                if(teacher.isPresent()){
                    Optional<Post> post=posts.findPost(postId);
                    if(post.isPresent()){
                        Contract contract=new Contract();
                        contracts.save(contract);
                        user.get().addContractAsStudent(contract);
                        teacher.get().addContractAsTeacher(contract);
                        post.get().addContract(contract);
                        users.save(user.get());
                        users.save(teacher.get());
                        posts.save(post.get());
                        contracts.save(contract);
                        notifications.sendMessage("N-"+user.get().getEmail()+
                                            "-"+teacher.get().getLogname()+
                                            "-"+user.get().getLogname()+
                                            "-"+contract.getPost().getTitle()); 
                        return "redirect:/myProfile";
                    } 
                }
            }
        }
        return "redirect:/";    
    }
    @GetMapping("/deleteContract")
    public String deleteContract(@RequestParam long contractId,@RequestParam boolean teacher) throws ServletException{
        Optional<Contract> contract_p=contracts.findContract(contractId);
        if(contract_p.isPresent()){
            Contract contract=contract_p.get();
            if(teacher)contract.setTeacherWantToDelete(!contract.isTeacherWantToDelete());
            else contract.setStudentWantToDelete(!contract.isStudentWantToDelete());
            contracts.save(contract);
            if(contract.isTeacherWantToDelete()&&contract.isStudentWantToDelete()){
                notifications.sendMessage("C-"+((teacher)?contract.getTeacher().getEmail():contract.getStudent().getEmail())+
                                            "-"+contract.getTeacher().getLogname()+
                                            "-"+contract.getStudent().getLogname()+
                                            "-"+contract.getPost().getTitle());
                contracts.delete(contractId);
            }
        }
        return "redirect:/myProfile";
    }
}
