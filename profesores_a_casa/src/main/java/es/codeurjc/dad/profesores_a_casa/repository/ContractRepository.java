package es.codeurjc.dad.profesores_a_casa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.*;

public interface ContractRepository extends JpaRepository<Contract,Long>{

    Contract save(Contract contract);
    Contract deleteById(long id);
    List<Contract> findByTeacher(User teacher);
    List<Contract> findByStudent(User student);
    List<Contract> findByPost(Post post);
    List<Contract> findAll();
    Optional<Contract> findById(long id);
}

