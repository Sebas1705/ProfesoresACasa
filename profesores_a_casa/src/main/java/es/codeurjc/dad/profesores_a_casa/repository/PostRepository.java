package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.model.User;

public interface PostRepository extends JpaRepository<Post,Long>{

    Post save(Post post);
    Post deleteById(Post post);
    Page<Post> findByOwnerUser(User ownerUser,Pageable pageable);
    List<Post> findByOwnerUser(User ownerUser);
    List<Post> findAll();
    Optional<Post> findById(long id);
    boolean existsById(long id);
}
