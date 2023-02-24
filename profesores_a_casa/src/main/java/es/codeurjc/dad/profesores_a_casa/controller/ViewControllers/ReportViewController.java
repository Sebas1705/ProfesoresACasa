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
public class ReportViewController {
    
    @Autowired private GeneralService service;

    @GetMapping("/newReport")
    public String formReport(Model model,HttpSession session,@RequestParam long postId){
        return service.getReportPage(model,session,postId);
    }
    @PostMapping("/report")
    public String createReport(Model model,HttpSession session,@RequestParam String motive, @RequestParam String description){
        return service.makeNewReport(model,session,motive,description);
    }
}
