package br.com.biblioteca.controller;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.modelo.Livro;

public class LivroController {
    private LivroDAO livroDAO;

    public LivroController() {
        livroDAO = new LivroDAO();
    }

    public void salvarLivro(Livro livro) {
        livroDAO.salvar(livro);
    }
}
