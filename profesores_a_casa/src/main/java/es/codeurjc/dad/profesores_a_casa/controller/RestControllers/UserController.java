package es.codeurjc.dad.profesores_a_casa.controller.RestControllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.dad.profesores_a_casa.model.User;
import es.codeurjc.dad.profesores_a_casa.service.UserService;
import java.util.*;

@RestController
@RequestMapping("/AppAPI/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> getAll(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return userService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return userService.create(user,
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(user.getId()).toUri());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> replaceUser(@PathVariable long id,@RequestBody User user){
        return userService.update(id,user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id){
        return userService.delete(id);
    }
}
