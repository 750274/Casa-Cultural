package br.com.biblioteca.visual;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.modelo.Livro;
import br.com.biblioteca.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaUsuario extends JFrame {

    private LivroDAO livroDAO;
    private UsuarioDAO usuarioDAO;

    public TelaUsuario() {
        livroDAO = new LivroDAO();
        usuarioDAO = new UsuarioDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Painel Administrador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new BorderLayout());

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título
        JLabel tituloLabel = new JLabel("Painel Administrador");
        tituloLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        tituloLabel.setForeground(new Color(34, 34, 59)); // Azul escuro
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tituloLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Painel de botões com layout Grid
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 20, 20));
        buttonPanel.setOpaque(false);

        // Adicionando botões estilizados
        buttonPanel.add(criarBotao("Cadastrar Usuário", e -> abrirTelaCadastroUsuario()));
        buttonPanel.add(criarBotao("Cadastrar Livros", e -> abrirTelaCadastroLivro()));
        buttonPanel.add(criarBotao("Gerenciar Empréstimos", e -> abrirTelaEmprestimos()));
        buttonPanel.add(criarBotao("Visualizar Usuários", e -> visualizarUsuarios()));
        buttonPanel.add(criarBotao("Excluir Livros", e -> excluirLivro()));
        buttonPanel.add(criarBotao("Alterar Disponibilidade", e -> alterarDisponibilidadeLivro()));
        buttonPanel.add(criarBotao("Excluir Usuários", e -> excluirUsuario()));
        buttonPanel.add(criarBotao("Visualizar Livros", e -> visualizarLivros()));

        mainPanel.add(buttonPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão de Sair
        JButton sairButton = criarBotao("Sair", e -> dispose());
        sairButton.setBackground(new Color(169, 169, 169)); // Cinza
        sairButton.setFont(new Font("Arial", Font.BOLD, 16));
        sairButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(sairButton);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton criarBotao(String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(new Color(85, 85, 220)); // Azul escuro
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botao.addActionListener(acao);
        return botao;
    }

    // Métodos de funcionalidades
    private void abrirTelaCadastroUsuario() {
        new TelaCadastroUsuario().setVisible(true);
    }

    private void abrirTelaCadastroLivro() {
        new TelaCadastroLivro().setVisible(true);
    }

    private void abrirTelaEmprestimos() {
        new TelaEmprestimos().setVisible(true);
    }

    private void excluirLivro() {
        String titulo = JOptionPane.showInputDialog(this, "Digite o título do livro a ser excluído:");
        if (titulo != null && !titulo.trim().isEmpty()) {
            Livro livro = livroDAO.buscarPorTitulo(titulo);
            if (livro != null) {
                livroDAO.deletar(livro);
                JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void alterarDisponibilidadeLivro() {
        String titulo = JOptionPane.showInputDialog(this, "Digite o título do livro para alterar a disponibilidade:");
        if (titulo != null && !titulo.trim().isEmpty()) {
            Livro livro = livroDAO.buscarPorTitulo(titulo);
            if (livro != null) {
                boolean disponivel = JOptionPane.showConfirmDialog(this, "O livro está disponível?") == JOptionPane.YES_OPTION;
                livro.setDisponivel(disponivel);
                livroDAO.atualizar(livro);
                JOptionPane.showMessageDialog(this, "Disponibilidade alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Livro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void visualizarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        StringBuilder usuariosTexto = new StringBuilder("Usuários cadastrados:\n\n");
        for (Usuario usuario : usuarios) {
            usuariosTexto.append(String.format("Nome: %s | Login: %s | Tipo: %s\n", usuario.getNome(), usuario.getLogin(), usuario.getTipo()));
        }
        JOptionPane.showMessageDialog(this, usuariosTexto.toString(), "Usuários Cadastrados", JOptionPane.INFORMATION_MESSAGE);
    }

    private void visualizarLivros() {
        List<Livro> livros = livroDAO.buscarTodos();
        StringBuilder livrosTexto = new StringBuilder("Livros cadastrados:\n\n");
        for (Livro livro : livros) {
            livrosTexto.append(String.format("Título: %s | Autor: %s | Gênero: %s | Disponível: %s\n",
                    livro.getTitulo(), livro.getAutor(), livro.getGenero(), livro.isDisponivel() ? "Sim" : "Não"));
        }
        JOptionPane.showMessageDialog(this, livrosTexto.toString(), "Livros Cadastrados", JOptionPane.INFORMATION_MESSAGE);
    }

    private void excluirUsuario() {
        String login = JOptionPane.showInputDialog(this, "Digite o login do usuário a ser excluído:");
        if (login != null && !login.trim().isEmpty()) {
            Usuario usuario = usuarioDAO.buscarPorLogin(login);
            if (usuario != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o usuário: " + usuario.getNome() + "?",
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    usuarioDAO.deletar(usuario);
                    JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaUsuario().setVisible(true));
    }
}
