package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Post {
    
    @OneToOne
    @Column(name = "OWNER_USER", nullable = false)
    private User ownerUser;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID", unique = true, nullable=false)
    private long postId;

    @Column(name = "TITLE", nullable=false)
    private String title;

    @Column(name = "DESCRIPTION", nullable=true)
    private String description;

    @Column(name = "PRICE", nullable=false)
    private double price;

    public Post(){}
}
