package br.com.biblioteca.visual;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField jTextFieldUsuario;
    private JPasswordField jPasswordFieldSenha;
    private JButton jButtonLogin;

    public TelaLogin() {
        initComponents();
    }

    private void initComponents() {
        
        setTitle("Sistema Biblioteca - Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título
        JLabel titulo = new JLabel("Sistema Biblioteca");
        titulo.setFont(new Font("Arial Black", Font.BOLD, 36));
        titulo.setForeground(new Color(34, 34, 59));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titulo);

        // Subtítulo
        JLabel subtitulo = new JLabel("Gerencie Livros, Usuários e Empréstimos Facilmente.");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(85, 85, 220));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(subtitulo);

        // Campo Login
        JLabel labelUsuario = criarLabel("Login:");
        jTextFieldUsuario = criarCampoTexto();
        mainPanel.add(labelUsuario);
        mainPanel.add(jTextFieldUsuario);

        // Espaçamento
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo Senha
        JLabel labelSenha = criarLabel("Senha:");
        jPasswordFieldSenha = criarCampoTextoSenha();
        mainPanel.add(labelSenha);
        mainPanel.add(jPasswordFieldSenha);

        // Espaçamento antes do botão
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão Login
        jButtonLogin = criarBotao("LOGIN");
        jButtonLogin.addActionListener(e -> processarLogin());
        mainPanel.add(jButtonLogin);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Método para criar um JLabel estilizado
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(new Color(34, 34, 59));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Método para criar JTextField estilizado
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setMaximumSize(new Dimension(400, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setForeground(Color.DARK_GRAY);
        return campo;
    }

    // Método para criar JPasswordField estilizado
    private JPasswordField criarCampoTextoSenha() {
        JPasswordField campo = new JPasswordField(20);
        campo.setMaximumSize(new Dimension(400, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setForeground(Color.DARK_GRAY);
        return campo;
    }

    // Método para criar botões estilizados
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(new Color(85, 85, 220));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    private void processarLogin() {
        String login = jTextFieldUsuario.getText().trim();
        String senha = new String(jPasswordFieldSenha.getPassword()).trim();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogado = usuarioDAO.buscarPorLoginESenha(login, senha);

        if (usuarioLogado != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo " + usuarioLogado.getNome() + "! Você está logado como: " + usuarioLogado.getTipo());
            abrirTelaPorTipoUsuario(usuarioLogado);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaPorTipoUsuario(Usuario usuario) {
        switch (usuario.getTipo().toLowerCase()) {
            case "admin" -> new TelaUsuario().setVisible(true);
            case "operador" -> new TelaEmprestimos().setVisible(true);
            case "cliente" -> JOptionPane.showMessageDialog(this, "Usuário comum: somente listagem permitida.");
            default -> JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
