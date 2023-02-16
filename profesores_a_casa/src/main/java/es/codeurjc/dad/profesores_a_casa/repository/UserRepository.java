package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>{
    List<User> findBySessionId(String sessionId);
}
