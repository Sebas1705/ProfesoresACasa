package es.codeurjc.dad.profesores_a_casa.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.codeurjc.dad.profesores_a_casa.model.User;
import es.codeurjc.dad.profesores_a_casa.repository.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository users;

    @PostConstruct
    public void init(){
        
    }

    public List<User> getUsers(){
        return users.findAll();
    }

    public ResponseEntity<User> getById(long id){
        Optional<User> user = users.findById(id);
        if(user.isPresent()) return ResponseEntity.ok(user.get());
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<User> create(User user,URI location){
        users.save(user);
        return ResponseEntity.created(location).body(user);
    }

    public ResponseEntity<User> update(long id,User upUser){
        Optional<User> user=users.findById(id);
        if(user.isPresent()){
            upUser.setId(id);
            users.save(upUser);
            return ResponseEntity.ok(upUser);
        }else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<User> delete(long id){
        Optional<User> user=users.findById(id);
        if(user.isPresent()){
            users.deleteById(id);
            return ResponseEntity.ok(user.get());
        }else return ResponseEntity.notFound().build();
    }

    public void save(User user){
        users.save(user);
    }

    public Optional<User> findUser(String logname){
        return users.findByLogname(logname);
    }

    public Optional<User> findUser(long id){
        return users.findById(id);
    }

    public boolean exist(User user){
        return users.existsById(user.getId());
    }

}
