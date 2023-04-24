package es.codeurjc.dad.profesores_a_casa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.dad.profesores_a_casa.model.Ranking;

import java.util.List;
import java.util.Optional;


public interface RankingRepository extends JpaRepository<Ranking,Long>{
    
    Ranking save(Ranking ranking);
    Ranking deleteById(long id);
    List<Ranking> findAll();
    Optional<Ranking> findById(long id);

}


