package es.codeurjc.dad.profesores_a_casa.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    User save(User user);
    User deleteById(long id);
    List<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByLogname(String logname);
}
