package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Report;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames="cache")
public interface ReportRepository extends JpaRepository<Report,Long>{
    
    @CacheEvict(allEntries=true)
    Report save(Report report);

    @CacheEvict(allEntries=true)
    Report deleteById(long id);

    @Cacheable
    List<Report> findAll();

    @Cacheable
    Optional<Report> findById(long id);
}
