package br.com.casacultural.controller;

import br.com.casacultural.model.Filme;
import br.com.casacultural.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmes") 
public class FilmeRestController {

    @Autowired
    private FilmeRepository filmeRepo;

    @GetMapping
    public List<Filme> listarFilmes() {
        
        System.out.println("🔥 Endpoint /api/filmes acessado");
        return filmeRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilme(@PathVariable Integer id) {
        Optional<Filme> filme = filmeRepo.findById(id);
        return filme.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Filme criarFilme(@RequestBody Filme filme) {
        return filmeRepo.save(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Integer id, @RequestBody Filme filme) {
        if (!filmeRepo.existsById(id)) return ResponseEntity.notFound().build();
        filme.setId(id);
        return ResponseEntity.ok(filmeRepo.save(filme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!filmeRepo.existsById(id)) return ResponseEntity.notFound().build();
        filmeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
