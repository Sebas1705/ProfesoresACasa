package es.codeurjc.dad.profesores_a_casa.controller.Advices;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DefaultModelAtributtes {

    @ModelAttribute("descriptionIntro")
    public String getDescriptionIntro(){
        return "TutorsAtHome is a web application connecting private tutors who offer home lessons " +
        "with students looking to hire them. " +
        "Browse tutor offers posted by registered teachers, hire the one that fits you best, " +
        "or become a tutor yourself and publish your own offers. " +
        "Create an account to hire and post, or log in if you already have one!";
    }

    @ModelAttribute("Bienvenido")
    public String getWelcome(){return "~~Welcome!!~~";}

    @ModelAttribute("BienvenidoDeVuelta")
    public String getWelcomeBack() {return "Welcome back!!";}

}
