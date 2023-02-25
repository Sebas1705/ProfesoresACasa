package es.codeurjc.dad.profesores_a_casa.controller.ViewControllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.dad.profesores_a_casa.service.GeneralService;

@Controller
public class ContractViewController {
    
    @Autowired private GeneralService service;

    //Contratos:
    @GetMapping("/newContract")
    public String formContract(Model model,HttpSession session,@RequestParam long postId,@RequestParam long studentId,@RequestParam long teacherId){
        return service.getContractPage(model,session,postId,studentId,teacherId);
    }
    @GetMapping("/contract")
    public String createContract(Model model,HttpSession session,@RequestParam long postId,@RequestParam long studentId,@RequestParam long teacherId){
        return service.makeNewContract(model,session,postId,studentId,teacherId);
    }
    @GetMapping("/deleteContract")
    public String deleteContract(Model model,HttpSession session,@RequestParam long contractId,@RequestParam boolean teacher){
        return service.deleteContract(model,session,contractId,teacher);
    }
}
