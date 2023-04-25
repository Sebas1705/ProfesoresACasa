package es.codeurjc.dad.profesores_a_casa.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.codeurjc.dad.profesores_a_casa.model.Report;
import es.codeurjc.dad.profesores_a_casa.repository.ReportRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames="cache")
public class ReportService {
    
    @Autowired
    private ReportRepository reports;

    @PostConstruct
    public void init(){

    }

    @Cacheable
    public List<Report> getReports(){
        return reports.findAll();
    }

    @Cacheable
    public ResponseEntity<Report> getById(long id){
        Optional<Report> report = reports.findById(id);
        if(report.isPresent()) return ResponseEntity.ok(report.get());
        else return ResponseEntity.notFound().build();
    }

    @CacheEvict(allEntries=true)
    public ResponseEntity<Report> create(Report report,URI location){
        reports.save(report);
        return ResponseEntity.created(location).body(report);
    }

    @CachePut
    public ResponseEntity<Report> update(long id,Report upReport){
        Optional<Report> report=reports.findById(id);
        if(report.isPresent()){
            upReport.setId(id);
            reports.save(upReport);
            return ResponseEntity.ok(upReport);
        }else return ResponseEntity.notFound().build();
    }

    @CacheEvict(allEntries=true)
    public ResponseEntity<Report> delete(long id){
        Optional<Report> report=reports.findById(id);
        if(report.isPresent()){
            reports.deleteById(id);
            return ResponseEntity.ok(report.get());
        }else return ResponseEntity.notFound().build();
    }

    @CacheEvict(allEntries=true)
    public void save(Report report){
        reports.save(report);
    }
}
