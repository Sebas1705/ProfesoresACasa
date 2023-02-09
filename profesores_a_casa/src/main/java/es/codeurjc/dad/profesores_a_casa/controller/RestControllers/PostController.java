package es.codeurjc.dad.profesores_a_casa.controller.RestControllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.service.PostService;
import java.util.*;

@RestController
@RequestMapping("/AppAPI/Posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public List<Post> getAll(){
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id){
        return postService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        return postService.create(post,
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(post.getId()).toUri());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> replacePost(@PathVariable long id,@RequestBody Post post){
        return postService.update(id,post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable long id){
        return postService.delete(id);
    }
}