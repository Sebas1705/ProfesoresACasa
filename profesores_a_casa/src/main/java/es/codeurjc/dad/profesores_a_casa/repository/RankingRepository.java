package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Ranking;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames="cache")
public interface RankingRepository extends JpaRepository<Ranking,Long>{
    
    @CacheEvict(allEntries=true)
    Ranking save(Ranking ranking);

    @CacheEvict(allEntries=true)
    Ranking deleteById(long id);

    @Cacheable
    List<Ranking> findAll();

    @Cacheable
    Optional<Ranking> findById(long id);

}


