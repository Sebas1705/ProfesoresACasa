package es.codeurjc.dad.profesores_a_casa.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.codeurjc.dad.profesores_a_casa.model.Post;
import es.codeurjc.dad.profesores_a_casa.repository.PostRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    
    @Autowired
    private PostRepository posts;

    @PostConstruct
    public void init(){

    }

    public Page<Post> getPage(Pageable pageable){
        return posts.findAll(pageable);
    }

    public List<Post> getPosts(){
        return posts.findAll();
    }

    public ResponseEntity<Post> getById(long id){
        Optional<Post> post = posts.findById(id);
        if(post.isPresent()) return ResponseEntity.ok(post.get());
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Post> create(Post post,URI location){
        posts.save(post);
        return ResponseEntity.created(location).body(post);
    }

    public ResponseEntity<Post> update(long id,Post upPost){
        Optional<Post> post=posts.findById(id);
        if(post.isPresent()){
            upPost.setId(id);
            posts.save(upPost);
            return ResponseEntity.ok(upPost);
        }else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Post> delete(long id){
        Optional<Post> post=posts.findById(id);
        if(post.isPresent()){
            posts.deleteById(id);
            return ResponseEntity.ok(post.get());
        }else return ResponseEntity.notFound().build();
    }

    public void save(Post post){
        posts.save(post);
    }
}
