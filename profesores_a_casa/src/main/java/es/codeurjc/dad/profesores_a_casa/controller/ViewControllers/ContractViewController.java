package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.dad.profesores_a_casa.model.*;
import es.codeurjc.dad.profesores_a_casa.service.*;

@Controller
public class ContractViewController {
    
    @Autowired private GeneralService service;
    @Autowired private UserService users;
    @Autowired private PostService posts;
    @Autowired private ContractService contracts;

    //Contratos:
    @GetMapping("/newContract")
    public String formContract(Model model,HttpSession session,@RequestParam long postId,@RequestParam long studentId,@RequestParam long teacherId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> student=users.findUser(studentId);
        Optional<User> teacher;
        Optional<Post> post;
        if(student.isPresent()){
            teacher=users.findUser(teacherId);
            if(teacher.isPresent()){
                post=posts.findPost(postId);
                if(post.isPresent()){
                    model.addAttribute("Post",post.get());
                    model.addAttribute("Teacher",teacher.get());
                    model.addAttribute("Student",student.get());
                    return "NewContract";
                }
            }
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";
    }
    @GetMapping("/contract")
    public String createContract(Model model,HttpSession session,@RequestParam long postId,@RequestParam long studentId,@RequestParam long teacherId){
        User u=(User) session.getAttribute("User");
        model.addAttribute("User",u);
        Optional<User> student=users.findUser(studentId);
        Optional<User> teacher;
        Optional<Post> post;
        if(student.isPresent()){
            teacher=users.findUser(teacherId);
            if(teacher.isPresent()){
                post=posts.findPost(postId);
                if(post.isPresent()){
                    Contract contract=new Contract();
                    contracts.save(contract);
                    student.get().addContractAsStudent(contract);
                    teacher.get().addContractAsTeacher(contract);
                    post.get().addContract(contract);
                    users.save(student.get());
                    users.save(teacher.get());
                    posts.save(post.get());
                    contracts.save(contract); 
                    service.setUpMiPerfil(model,session);
                }
            } 
        }
        service.setUpOfPosts(model,PageRequest.of(0,10),null,false);
        return "Home";    
    }
    @GetMapping("/deleteContract")
    public String deleteContract(Model model,HttpSession session,@RequestParam long contractId,@RequestParam boolean teacher){
        Optional<Contract> contract_p=contracts.findContract(contractId);
        if(contract_p.isPresent()){
            Contract contract=contract_p.get();
            if(teacher)contract.setTeacherWantToDelete(!contract.isTeacherWantToDelete());
            else contract.setStudentWantToDelete(!contract.isStudentWantToDelete());
            contracts.save(contract);
            if(contract.isTeacherWantToDelete()&&contract.isStudentWantToDelete())contracts.delete(contractId);
        }
        service.setUpMiPerfil(model,session);
        return "MyProfile";
    }
}
