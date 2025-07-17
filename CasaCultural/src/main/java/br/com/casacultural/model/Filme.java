package br.com.casacultural.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String sinopse;
    private String genero;
    private int ano;
    

    

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Analise> analises = new ArrayList<>();

    public Filme() {}

    public Filme(String titulo, String sinopse, String genero, int ano) {
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.genero = genero;
        this.ano = ano;
    }

    

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getGenero() {
        return genero;
    }

    public int getAno() {
        return ano;
    }

    public List<Analise> getAnalises() {
        return analises;
    }

  

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSinopse(String sinopse) {
    if (sinopse != null && sinopse.length() > 500) {
        throw new IllegalArgumentException("A sinopse deve ter no máximo 500 caracteres.");
    }
    this.sinopse = sinopse;
}


    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setAnalises(List<Analise> analises) {
        this.analises = analises;
    }
}
