package es.codeurjc.dad.profesores_a_casa.controller;

/* 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import es.codeurjc.dad.profesores_a_casa.model.User;
import es.codeurjc.dad.profesores_a_casa.repository.UserRepository;

//@Controller
public class DataBaseUsage implements CommandLineRunner{

    /@Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {

        // save a couple of customers
        repository.save(new User("Jack", "Bauer"));
        repository.save(new User("Chloe", "O'Brian"));
        repository.save(new User("Kim", "Bauer"));
        repository.save(new User("David", "Palmer"));
        repository.save(new User("Michelle", "Dessler"));

        // fetch all customers
        List<User> customers = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : customers) {
            System.out.println(user);
        }
        System.out.println();

        // fetch an individual customer by ID
        User customer = repository.findById(1L).get();
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        List<User> bauers = repository.findByLogName("Jack");
        System.out.println("Customer found with findByLogName('Jack'):");
        System.out.println("--------------------------------------------");
        for (User bauer : bauers) {
            System.out.println(bauer);
        }

        repository.delete(bauers.get(0));
    }
}*/