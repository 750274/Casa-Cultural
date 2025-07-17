package br.com.biblioteca.visual;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.modelo.Emprestimo;
import br.com.biblioteca.modelo.Livro;
import br.com.biblioteca.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class TelaEmprestimos extends JFrame {
    private EmprestimoDAO emprestimoDAO;
    private LivroDAO livroDAO;

    public TelaEmprestimos() {
        emprestimoDAO = new EmprestimoDAO();
        livroDAO = new LivroDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gerenciar Empréstimos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título
        JLabel tituloLabel = new JLabel("Gerenciar Empréstimos");
        tituloLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        tituloLabel.setForeground(new Color(34, 34, 59));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tituloLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20));
        buttonPanel.setOpaque(false);

        // Botões estilizados
        buttonPanel.add(criarBotao("Registrar Empréstimo", this::registrarEmprestimo));
        buttonPanel.add(criarBotao("Registrar Devolução", this::registrarDevolucao));
        buttonPanel.add(criarBotao("Visualizar Livros", this::visualizarLivros));

        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(new Color(85, 85, 220));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.addActionListener(acao);
        return botao;
    }

    private void registrarEmprestimo(ActionEvent e) {
        String tituloLivro = JOptionPane.showInputDialog(this, "Digite o título do livro para empréstimo:");
        if (tituloLivro == null || tituloLivro.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título do livro é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livro livro = livroDAO.buscarPorTitulo(tituloLivro);
        if (livro == null) {
            JOptionPane.showMessageDialog(this, "Livro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!livro.isDisponivel()) {
            JOptionPane.showMessageDialog(this, "Livro não está disponível para empréstimo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String loginUsuario = JOptionPane.showInputDialog(this, "Digite o login do usuário:");
        if (loginUsuario == null || loginUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login do usuário é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorLogin(loginUsuario);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setStatus("Emprestado");

        try {
            livro.setDisponivel(false);
            livroDAO.atualizar(livro);
            emprestimoDAO.salvar(emprestimo);
            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar empréstimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarDevolucao(ActionEvent e) {
        String titulo = JOptionPane.showInputDialog(this, "Digite o título do livro para registrar a devolução:");
        if (titulo != null && !titulo.trim().isEmpty()) {
            Emprestimo emprestimo = emprestimoDAO.buscarPorLivroTitulo(titulo);
            if (emprestimo != null) {
                emprestimo.setDataDevolucao(LocalDate.now());
                emprestimo.setStatus("Devolvido");

                Livro livro = emprestimo.getLivro();
                if (livro != null) {
                    livro.setDisponivel(true);
                    livroDAO.atualizar(livro);
                }

                emprestimoDAO.atualizar(emprestimo);
                JOptionPane.showMessageDialog(this, "Devolução registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Empréstimo não encontrado para o livro informado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void visualizarLivros(ActionEvent e) {
        List<Livro> livros = livroDAO.buscarTodos();
        StringBuilder livrosTexto = new StringBuilder("Livros cadastrados:\n\n");
        for (Livro livro : livros) {
            livrosTexto.append(String.format("Título: %s | Autor: %s | Disponível: %s\n",
                    livro.getTitulo(), livro.getAutor(), livro.isDisponivel() ? "Sim" : "Não"));
        }
        JOptionPane.showMessageDialog(this, livrosTexto.toString(), "Livros Cadastrados", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaEmprestimos().setVisible(true));
    }
}
