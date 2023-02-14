package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

    Page<Post> findAll(Pageable pageable);
    
}
