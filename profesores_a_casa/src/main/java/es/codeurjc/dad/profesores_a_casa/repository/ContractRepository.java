package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Contract;

public interface ContractRepository extends JpaRepository<Contract,Long>{
    
}

