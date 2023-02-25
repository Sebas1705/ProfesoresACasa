package es.codeurjc.dad.profesores_a_casa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.*;

public interface ContractRepository extends JpaRepository<Contract,Long>{
    List<Contract> findByTeacher(User teacher);
    List<Contract> findByStudent(User student);
    List<Contract> findByPost(Post post);
}

