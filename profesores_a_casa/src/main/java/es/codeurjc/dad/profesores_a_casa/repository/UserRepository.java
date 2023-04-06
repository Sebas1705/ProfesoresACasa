package es.codeurjc.dad.profesores_a_casa.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


@CacheConfig(cacheNames="cache")
public interface UserRepository extends JpaRepository<User,Long>{

    @CacheEvict(allEntries=true)
    User save(User user);

    @CacheEvict(allEntries=true)
    User deleteById(long id);

    @Cacheable
    List<User> findAll();

    @Cacheable
    Optional<User> findById(long id);

    @Cacheable
    Optional<User> findByLogname(String logname);
}
