package br.com.biblioteca.controller;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.modelo.Emprestimo;
import br.com.biblioteca.modelo.Livro;
import br.com.biblioteca.modelo.Usuario;

import java.time.LocalDate;
import java.util.List;

public class EmprestimosController {

    private final EmprestimoDAO emprestimoDAO;
    private final LivroDAO livroDAO;
    private final UsuarioDAO usuarioDAO;

    public EmprestimosController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.livroDAO = new LivroDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void registrarEmprestimo(int livroId, int usuarioId) {
        Livro livro = livroDAO.buscarPorId(livroId);
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);

        if (livro == null || usuario == null) {
            throw new IllegalArgumentException("Livro ou usuário não encontrado.");
        }

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("O livro não está disponível.");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setStatus("EM ANDAMENTO");

        emprestimoDAO.salvar(emprestimo);

        livro.setDisponivel(false);
        livroDAO.atualizar(livro);
    }

    public void registrarDevolucao(int emprestimoId) {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);

        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        if (!"EM ANDAMENTO".equals(emprestimo.getStatus())) {
            throw new IllegalStateException("O empréstimo já foi finalizado.");
        }

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus("FINALIZADO");

        emprestimoDAO.atualizar(emprestimo);

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroDAO.atualizar(livro);
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoDAO.buscarTodos();
    }
}
