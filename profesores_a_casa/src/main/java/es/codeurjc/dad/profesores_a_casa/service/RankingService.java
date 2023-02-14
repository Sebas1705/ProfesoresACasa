package es.codeurjc.dad.profesores_a_casa.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.codeurjc.dad.profesores_a_casa.model.Ranking;
import es.codeurjc.dad.profesores_a_casa.repository.RankingRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class RankingService {
    
    @Autowired
    private RankingRepository rankings;

    @PostConstruct
    public void init(){

    }

    public List<Ranking> getRankings(){
        return rankings.findAll();
    }

    public ResponseEntity<Ranking> getById(long id){
        Optional<Ranking> ranking = rankings.findById(id);
        if(ranking.isPresent()) return ResponseEntity.ok(ranking.get());
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Ranking> create(Ranking ranking,URI location){
        rankings.save(ranking);
        return ResponseEntity.created(location).body(ranking);
    }

    public ResponseEntity<Ranking> update(long id,Ranking upRanking){
        Optional<Ranking> ranking=rankings.findById(id);
        if(ranking.isPresent()){
            upRanking.setId(id);
            rankings.save(upRanking);
            return ResponseEntity.ok(upRanking);
        }else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Ranking> delete(long id){
        Optional<Ranking> ranking=rankings.findById(id);
        if(ranking.isPresent()){
            rankings.deleteById(id);
            return ResponseEntity.ok(ranking.get());
        }else return ResponseEntity.notFound().build();
    }

    public void save(Ranking ranking){
        rankings.save(ranking);
    }
}
