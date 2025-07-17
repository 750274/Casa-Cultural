package br.com.biblioteca.visual;

import br.com.biblioteca.controller.LivroController;
import br.com.biblioteca.modelo.Livro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroLivro extends JFrame {
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoGenero;
    private JTextField campoAno;
    private LivroController livroController;

    public TelaCadastroLivro() {
        livroController = new LivroController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Livro");
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
        JLabel tituloLabel = new JLabel("Cadastro de Livros");
        tituloLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        tituloLabel.setForeground(new Color(34, 34, 59));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tituloLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Campo de título
        mainPanel.add(criarCampo("Título:", campoTitulo = new JTextField()));

        // Campo de autor
        mainPanel.add(criarCampo("Autor:", campoAutor = new JTextField()));

        // Campo de gênero
        mainPanel.add(criarCampo("Gênero:", campoGenero = new JTextField()));

        // Campo de ano
        mainPanel.add(criarCampo("Ano:", campoAno = new JTextField()));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão salvar
        JButton botaoSalvar = criarBotao("SALVAR", this::salvarLivro);
        mainPanel.add(botaoSalvar);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel criarCampo(String labelTexto, JTextField campoTexto) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelTexto);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(new Color(34, 34, 59));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        campoTexto.setMaximumSize(new Dimension(400, 30));
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 16));
        campoTexto.setForeground(Color.DARK_GRAY);
        campoTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(campoTexto);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        return panel;
    }

    private JButton criarBotao(String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(new Color(85, 85, 220));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.addActionListener(acao);
        return botao;
    }

    private void salvarLivro(ActionEvent e) {
        try {
            String titulo = campoTitulo.getText().trim();
            String autor = campoAutor.getText().trim();
            String genero = campoGenero.getText().trim();
            int ano = Integer.parseInt(campoAno.getText().trim());

            Livro livro = new Livro(titulo, autor, genero, ano);
            livroController.salvarLivro(livro);

            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O campo 'Ano' deve conter apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroLivro().setVisible(true));
    }
}
