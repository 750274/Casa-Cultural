package br.com.casacultural.controller;

import br.com.casacultural.model.Analise;
import br.com.casacultural.model.Filme;
import br.com.casacultural.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/filmes")
public class FilmeController {
    @Autowired
    private FilmeRepository repo;

    // Formulário novo filme (redireciona para HTML estático)
    @GetMapping("/novo")
    public String novoForm() {
        return "redirect:/form-filme.html";
    }
    
    // Salvar filme via formulário
    @PostMapping("/salvar")
    public String salvarFilme(@ModelAttribute Filme filme) {
        repo.save(filme);
        return "redirect:/lista-filmes.html";
    }
    
    // Redirecionar para a lista
    @GetMapping("/listar")
    public String listar() {
        return "redirect:/lista-filmes.html";
    }
    
    // Redirecionar para detalhes com ID
    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable int id) {
        Optional<Filme> optFilme = repo.findById(id);
        if (optFilme.isEmpty()) return "redirect:/lista-filmes.html";
        return "redirect:/detalhes-filme.html?id=" + id;
    }
    
    @PostMapping("/analisar/{id}")
    public String analisar(@PathVariable int id, @ModelAttribute Analise analise) {
        Optional<Filme> optFilme = repo.findById(id);
        if (optFilme.isPresent()) {
            Filme filme = optFilme.get();
            analise.setFilme(filme);
            filme.getAnalises().add(analise);
            repo.save(filme);
        }
        return "redirect:/detalhes-filme.html?id=" + id;
    }
    
    // REMOVIDO: @GetMapping("/") - agora está no HomeController
}