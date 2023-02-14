package es.codeurjc.dad.profesores_a_casa.model;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Report> reports;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contract> contract;

    @Column(name = "SESSION_ID")
    private String sesionId;

    @Column(name = "LOGNAME")
    private String logname;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SELF_DESCRIPTION")
    private String selfDescription;

    @Column(name = "PRIVILEGED")
    private boolean privileged;

    public User(){}

    public User(String logname,String password){
        super();
        this.logname=logname;this.password=password;
    }

}
