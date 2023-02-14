package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Post;



public interface PostRepository extends JpaRepository<Post,Long>{
    
}
