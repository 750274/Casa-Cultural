package br.com.casacultural.controller;

import br.com.casacultural.model.Analise;
import br.com.casacultural.model.Filme;
import br.com.casacultural.repository.AnaliseRepository;
import br.com.casacultural.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/analises")
public class AnaliseRestController {

    @Autowired
    private AnaliseRepository analiseRepo;

    @Autowired
    private FilmeRepository filmeRepo;

    @GetMapping
    public List<Analise> listarTodas() {
        return analiseRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analise> buscarPorId(@PathVariable Integer id) {
        return analiseRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/filme/{filmeId}")
    public ResponseEntity<Analise> adicionarAnalise(@PathVariable Integer filmeId, @RequestBody Analise analise) {
        Optional<Filme> filme = filmeRepo.findById(filmeId);
        if (filme.isPresent()) {
            analise.setFilme(filme.get());
            return ResponseEntity.ok(analiseRepo.save(analise));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Analise> atualizar(@PathVariable Integer id, @RequestBody Analise novaAnalise) {
        return analiseRepo.findById(id).map(analise -> {
            analise.setTexto(novaAnalise.getTexto());
            analise.setNota(novaAnalise.getNota());
            return ResponseEntity.ok(analiseRepo.save(analise));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAnalise(@PathVariable Integer id) {
        if (!analiseRepo.existsById(id)) return ResponseEntity.notFound().build();
        analiseRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
