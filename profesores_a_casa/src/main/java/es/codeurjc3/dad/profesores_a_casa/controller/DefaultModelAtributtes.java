package es.codeurjc3.dad.profesores_a_casa.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DefaultModelAtributtes {
    
    @ModelAttribute("texto")
    public String textoHome(){
        return "Pagina principal";
    }
}
