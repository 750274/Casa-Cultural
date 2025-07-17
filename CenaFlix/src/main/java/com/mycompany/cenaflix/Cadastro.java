package com.mycompany.cenaflix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cadastro extends JFrame {

    private JTextField jTextFieldNome, jTextFieldData, jTextFieldCategoria;
    private JButton jButtonCadastrar, jButtonLimpar;

    public Cadastro() {
        setTitle("Cadastro de Filmes - Cenaflix");
        setSize(873, 529);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        // Fundo cinza para igualar ao exemplo da atividade
        JLabel jLabelTitulo = new JLabel("Cenaflix", JLabel.CENTER);
        jLabelTitulo.setFont(new Font("Arial", Font.BOLD, 38));
        jLabelTitulo.setOpaque(true);
        jLabelTitulo.setBackground(Color.LIGHT_GRAY);
        jLabelTitulo.setBounds(336, 10, 200, 50);

        JLabel jLabelSubtitulo = new JLabel("Cadastro de Filme", JLabel.CENTER);
        jLabelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 28));
        jLabelSubtitulo.setBounds(0, 80, 873, 30);

        int larguraCampo = 300;
        int alturaCampo = 30;
        int larguraJanela = 873;

        int xCentro = (larguraJanela - larguraCampo) / 2;

        JLabel jLabelNome = new JLabel("Nome do Filme:");
        jLabelNome.setBounds(xCentro - 150, 150, 150, 20);
        jTextFieldNome = new JTextField();
        jTextFieldNome.setBounds(xCentro, 150, larguraCampo, alturaCampo);

        JLabel jLabelData = new JLabel("Data de Lançamento:");
        jLabelData.setBounds(xCentro - 150, 200, 150, 20);
        jTextFieldData = new JTextField();
        jTextFieldData.setBounds(xCentro, 200, larguraCampo, alturaCampo);

        JLabel jLabelCategoria = new JLabel("Categoria:");
        jLabelCategoria.setBounds(xCentro - 150, 250, 150, 20);
        jTextFieldCategoria = new JTextField();
        jTextFieldCategoria.setBounds(xCentro, 250, larguraCampo, alturaCampo);

        jButtonCadastrar = new JButton("Cadastrar");
        jButtonLimpar = new JButton("Limpar");

        // Centralizar os botões
        int larguraBotao = 120;
        int espacoEntreBotoes = 40;
        int totalLarguraBotoes = (2 * larguraBotao) + espacoEntreBotoes;

        jButtonCadastrar.setBounds((larguraJanela - totalLarguraBotoes) / 2, 320, larguraBotao, 40);
        jButtonLimpar.setBounds((larguraJanela - totalLarguraBotoes) / 2 + larguraBotao + espacoEntreBotoes, 320, larguraBotao, 40);

        // Adicionar os componentes
        add(jLabelTitulo);
        add(jLabelSubtitulo);
        add(jLabelNome);
        add(jTextFieldNome);
        add(jLabelData);
        add(jTextFieldData);
        add(jLabelCategoria);
        add(jTextFieldCategoria);
        add(jButtonCadastrar);
        add(jButtonLimpar);

        jButtonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarFilme();
            }
        });

        jButtonLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }

    private void cadastrarFilme() {
        String nomeFilme = jTextFieldNome.getText();
        String dataLancamento = jTextFieldData.getText();
        String categoria = jTextFieldCategoria.getText();

        // Ajuste para o formato de data
        try {
            SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
            Date data = formatoBrasileiro.parse(dataLancamento);
            dataLancamento = formatoBanco.format(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro no formato da data. Utilize dd/MM/yyyy.");
            return;
        }
        String sql = null;
        sql = "INSERT INTO filmes (nome, datalancamento, categoria) VALUES (?, ?, ?)";

        try (Connection conn = CenaFlix.conexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeFilme);
            stmt.setString(2, dataLancamento);
            stmt.setString(3, categoria);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Filme cadastrado com sucesso!");
            limparCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        jTextFieldNome.setText("");
        jTextFieldData.setText("");
        jTextFieldCategoria.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Cadastro().setVisible(true);
        });
    }
}
