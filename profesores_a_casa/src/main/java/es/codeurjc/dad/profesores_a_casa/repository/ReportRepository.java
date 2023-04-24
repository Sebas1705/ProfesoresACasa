package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long>{
    
    Report save(Report report);
    Report deleteById(long id);
    List<Report> findAll();
    Optional<Report> findById(long id);
}
