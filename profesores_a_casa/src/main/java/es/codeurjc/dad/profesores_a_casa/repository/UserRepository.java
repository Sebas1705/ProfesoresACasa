package es.codeurjc.dad.profesores_a_casa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.User;


public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByLogname(String logname);
}
