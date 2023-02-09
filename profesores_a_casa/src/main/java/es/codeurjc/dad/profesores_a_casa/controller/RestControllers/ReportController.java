package es.codeurjc.dad.profesores_a_casa.controller.RestControllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.dad.profesores_a_casa.model.Report;
import es.codeurjc.dad.profesores_a_casa.service.ReportService;
import java.util.*;

@RestController
@RequestMapping("/AppAPI/Reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public List<Report> getAll(){
        return reportService.getReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable long id){
        return reportService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Report> createReport(@RequestBody Report report){
        return reportService.create(report,
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(report.getId()).toUri());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> replaceReport(@PathVariable long id,@RequestBody Report report){
        return reportService.update(id,report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Report> deleteReport(@PathVariable long id){
        return reportService.delete(id);
    }
}
