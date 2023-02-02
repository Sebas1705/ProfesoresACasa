package es.codeurjc.dad.profesores_a_casa.model;

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
    @Column(name = "CONTRACT_ID", unique = true, nullable=false)
    private long userId;

    @Column(name = "SESSION_ID", unique = true, nullable=false)
    private String sesionId;

    @Column(name = "LOG_NAME", unique = true, nullable=false)
    private String logName;

    @Column(name = "PASSWORD", nullable=false)
    private String password;

    @Column(name = "SELF_DESCRIPTION", nullable=true)
    private String selfDescription;

    @Column(name = "PRIVILEGED", nullable=false)
    private boolean privileged;

    public User(){}
}
