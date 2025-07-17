package br.com.casacultural.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/lista-filmes.html";
    }
    
    @GetMapping("/lista-filmes")
    public String listaFilmes() {
        return "lista-filmes";
    }
    
    @GetMapping("/analises")
    public String analises() {
        return "analises";
    }
    
    @GetMapping("/detalhes-filme")
    public String detalhesFilme() {
        return "detalhes-filme";
    }
    
    @GetMapping("/form-filme")
    public String formFilme() {
        return "form-filme";
    }
}