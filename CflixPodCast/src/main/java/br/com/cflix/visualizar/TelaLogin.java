package br.com.cflix.visualizar;

import br.com.cflix.dao.UsuarioDAO;
import br.com.cflix.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
/**
 * A classe TelaLogin representa a interface de autenticação inicial.
 * Ela permite que o usuário insira suas credenciais (login e senha) e receba permissões 
 * específicas com base no seu tipo de usuário.
 *
 * Funcionalidades principais:
 * - Autenticação de usuários com base em credenciais.
 * - Exibição de mensagem personalizada de boas-vindas com o tipo de usuário.
 * - Redirecionamento do usuário para a tela correspondente ao seu tipo de permissão.
 *
 * Tipos de usuário suportados:
 * - Administrador: Acesso completo, incluindo cadastro, listagem e exclusão.
 * - Operador: Acesso a cadastro e listagem.
 * - Usuário: Acesso apenas à listagem.
 */
public class TelaLogin extends JFrame {

    private JTextField jTextFieldUsuario;
    private JPasswordField jPasswordFieldSenha;
    private JButton jButtonLogin;

    public TelaLogin() {
        initComponents();
    }

    private void initComponents() {
        // Configurações da janela
        setTitle("CENAFLIX - Login");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new BorderLayout());

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 50, 0)); // Ajustada a margem geral do painel

        // Título
        JLabel titulo = new JLabel("CENAFLIX", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 36)); // Fonte aumentada para 36
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0)); // Margem ajustada para mais espaço após o título
        mainPanel.add(titulo);

        // Espaço adicional antes do campo de login
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Campo de usuário
        JLabel labelUsuario = new JLabel("Login:");
        labelUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelUsuario);

        jTextFieldUsuario = new JTextField(20);
        jTextFieldUsuario.setMaximumSize(new Dimension(300, 30));
        jTextFieldUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jTextFieldUsuario);

        // Espaço entre os campos
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo de senha
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 18));
        labelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelSenha);

        jPasswordFieldSenha = new JPasswordField(20);
        jPasswordFieldSenha.setMaximumSize(new Dimension(300, 30));
        jPasswordFieldSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jPasswordFieldSenha);

        // Espaço maior antes do botão
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Aumentado o espaço antes do botão

        // Botão de login
        jButtonLogin = new JButton("LOGIN");
        jButtonLogin.setFont(new Font("Arial", Font.BOLD, 18));
        jButtonLogin.setBackground(Color.GRAY);
        jButtonLogin.setForeground(Color.BLACK);
        jButtonLogin.setFocusPainted(false);
        jButtonLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButtonLogin.addActionListener(e -> processarLogin());
        mainPanel.add(jButtonLogin);

        // Espaço adicional após o botão
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adicionando espaço após o botão

        // Adicionar painel principal à janela
        add(mainPanel, BorderLayout.CENTER);
    }

    private void processarLogin() {
        String usuario = jTextFieldUsuario.getText().trim();
        String senha = new String(jPasswordFieldSenha.getPassword()).trim();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogado = usuarioDAO.buscarPorNomeESenha(usuario, senha);

        if (usuarioLogado != null) {
            JOptionPane.showMessageDialog(this, "Olá " + usuarioLogado.getUsuario() + ", sua permissão é de " + usuarioLogado.getTipo() + ". Seja bem-vindo!");
            abrirTelaPorTipoUsuario(usuarioLogado);
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaPorTipoUsuario(Usuario usuario) {
        switch (usuario.getTipo().toLowerCase()) {
            case "administrador" -> new TelaCadastro(usuario).setVisible(true);
            case "operador" -> new TelaCadastro(usuario).setVisible(true);
            case "usuario" -> new TelaListagem(usuario).setVisible(true);
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
