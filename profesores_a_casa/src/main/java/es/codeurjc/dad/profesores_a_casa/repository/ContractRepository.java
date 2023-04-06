package es.codeurjc.dad.profesores_a_casa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.*;

@CacheConfig(cacheNames="cache")
public interface ContractRepository extends JpaRepository<Contract,Long>{

    @CacheEvict(allEntries=true)
    Contract save(Contract contract);
    
    @CacheEvict(allEntries=true)
    Contract deleteById(long id);
    
    @Cacheable 
    List<Contract> findByTeacher(User teacher);
    
    @Cacheable 
    List<Contract> findByStudent(User student);
    
    @Cacheable 
    List<Contract> findByPost(Post post);

    @Cacheable 
    List<Contract> findAll();

    @Cacheable
    Optional<Contract> findById(long id);
}

