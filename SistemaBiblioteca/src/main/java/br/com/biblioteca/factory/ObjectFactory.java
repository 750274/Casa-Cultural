package br.com.biblioteca.factory;

import br.com.biblioteca.modelo.Livro;

public class ObjectFactory {

    public Livro criarLivro(String titulo, String autor, String genero, int ano) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setGenero(genero);
        livro.setAno(ano);
        livro.setDisponivel(true); // Marca o livro como disponível por padrão
        return livro;
    }
}
