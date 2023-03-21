package es.codeurjc.dad.profesores_a_casa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.codeurjc.dad.profesores_a_casa.model.*;

@Service
public class DataBaseInitialazer {
    
    @Autowired private PostService posts;
    @Autowired private UserService users;
    @Autowired private ContractService contracts;
    @Autowired private ReportService reports;
    @Autowired private PasswordEncoder passwordEncoder;


    //Casos 1:1->
    //  Bidireccional:
    //      Sin cascade: se guarda cada uno por separado y se hace el set
    //      Con cascade: se guarda solo el principal tras setear el secundario
    //Casos N:1->
    //  Bidireccional:
    //      Sin cascade: se guarda la entidad secundaria, se setea a la principal y se guarda la principal
    //      Con cascade: se hace funcion de add en la secundaria que fue mapeada

    public void autoInitDBTest(int n){
        for (int i=0;i<n;i++){
            User student=new User("ExampleLogname_0_"+i,passwordEncoder.encode("123"),"ExampleEmail_0_"+i);
            User teacher=new User("ExampleLogname_1_"+i,passwordEncoder.encode("ExamplePassword_1_"+i),"ExampleEmail_1_"+i);
            users.save(student);
            users.save(teacher);
            Post post=new Post("ExampleTitle_"+i,"ExampleDescription_"+i,(double)Math.round((Math.random()*101)*100d)/100d);
            post.setRanking(new Ranking((int)(Math.random()*6),1));
            posts.save(post);
            Report report=new Report("ExampleMotive_"+i,"ExampleDescription_"+i);
            student.addReport(report);
            post.addReport(report);
            teacher.addPost(post);
            reports.save(report);
            Contract contract=new Contract();
            contracts.save(contract);
            student.addContractAsStudent(contract);
            teacher.addContractAsTeacher(contract);
            post.addContract(contract);
            users.save(student);
            users.save(teacher);
            posts.save(post);
            reports.save(report);
            contracts.save(contract);    
        }
    }
}
