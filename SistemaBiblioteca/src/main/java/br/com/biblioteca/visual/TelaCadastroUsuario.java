package br.com.biblioteca.visual;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {

    private JTextField campoNome;
    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipo;

    public TelaCadastroUsuario() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Usuários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Título
        JLabel tituloLabel = new JLabel("Cadastro de Usuários");
        tituloLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        tituloLabel.setForeground(new Color(34, 34, 59));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(tituloLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Campo Nome
        JLabel labelNome = criarLabel("Nome:");
        campoNome = criarCampoTexto();
        mainPanel.add(labelNome);
        mainPanel.add(campoNome);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo Login
        JLabel labelLogin = criarLabel("Login:");
        campoLogin = criarCampoTexto();
        mainPanel.add(labelLogin);
        mainPanel.add(campoLogin);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo Senha
        JLabel labelSenha = criarLabel("Senha:");
        campoSenha = criarCampoTextoSenha();
        mainPanel.add(labelSenha);
        mainPanel.add(campoSenha);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Combo Tipo
        JLabel labelTipo = criarLabel("Tipo:");
        comboTipo = new JComboBox<>(new String[]{"admin", "operador", "cliente"});
        comboTipo.setMaximumSize(new Dimension(400, 30));
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 16));
        comboTipo.setForeground(Color.DARK_GRAY);
        mainPanel.add(labelTipo);
        mainPanel.add(comboTipo);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão Cadastrar
        JButton botaoCadastrar = criarBotao("CADASTRAR");
        botaoCadastrar.addActionListener(e -> salvarUsuario());
        mainPanel.add(botaoCadastrar);

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

    private void salvarUsuario() {
        String nome = campoNome.getText().trim();
        String login = campoLogin.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String tipo = comboTipo.getSelectedItem().toString();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setLogin(login);
        novoUsuario.setSenha(senha);
        novoUsuario.setTipo(tipo);

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.salvar(novoUsuario);

            JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new TelaUsuario().setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroUsuario().setVisible(true));
    }
}
