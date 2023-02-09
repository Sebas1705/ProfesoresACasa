package es.codeurjc.dad.profesores_a_casa.model;

import javax.persistence.*;

import java.util.List;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID", unique = true, nullable=false)
    private long id;

    @ManyToOne
    private User ownerUser;

    @OneToMany
    private List<Contract> contract;

    @OneToMany
    private List<Report> reports;

    @OneToOne(cascade = CascadeType.ALL)
    private Ranking ranking;

    @Column(name = "TITLE", nullable=false)
    private String title;

    @Column(name = "DESCRIPTION", nullable=true)
    private String description;

    @Column(name = "PRICE", nullable=false)
    private double price;

    public Post(){}
}
