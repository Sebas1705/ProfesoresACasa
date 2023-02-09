package es.codeurjc.dad.profesores_a_casa.controller.RestControllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.dad.profesores_a_casa.model.Ranking;
import es.codeurjc.dad.profesores_a_casa.service.RankingService;
import java.util.*;

@RestController
@RequestMapping("/AppAPI/Ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/")
    public List<Ranking> getAll(){
        return rankingService.getRankings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranking> getUserById(@PathVariable long id){
        return rankingService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Ranking> createUser(@RequestBody Ranking ranking){
        return rankingService.create(ranking,
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(ranking.getId()).toUri());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ranking> replaceUser(@PathVariable long id,@RequestBody Ranking ranking){
        return rankingService.update(id,ranking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ranking> deleteUser(@PathVariable long id){
        return rankingService.delete(id);
    }
}
