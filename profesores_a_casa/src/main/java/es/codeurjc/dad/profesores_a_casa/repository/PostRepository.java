package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.model.User;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


@CacheConfig(cacheNames="cache")
public interface PostRepository extends JpaRepository<Post,Long>{

    @CacheEvict(allEntries=true)
    Post save(Post post);

    @CacheEvict(allEntries=true)
    Post deleteById(Post post);

    @Cacheable
    Page<Post> findByOwnerUser(User ownerUser,Pageable pageable);
    
    @Cacheable
    List<Post> findByOwnerUser(User ownerUser);

    @Cacheable
    List<Post> findAll();

    @Cacheable
    Optional<Post> findById(long id);

    @Cacheable
    boolean existsById(long id);
}
