package br.com.cflix.visualizar;

import br.com.cflix.modelo.Usuario;
import br.com.cflix.modelo.Podcast;
import br.com.cflix.dao.PodcastDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A classe TelaCadastro representa a interface de cadastro de podcasts.
 * Permite que usuários autorizados insiram novos registros de podcasts com as seguintes informações:
 * - Produtor
 * - Nome do episódio
 * - Número do episódio
 * - Duração
 * - URL do repositório
 *
 * Funcionalidades principais:
 * - Cadastro de podcasts com validação de dados obrigatórios.
 * - Navegação para a tela de listagem de podcasts.
 * 
 * Permissões de acesso:
 * - Administrador: acesso completo à tela.
 * - Operador: acesso completo à tela.
 */
public class TelaCadastro extends JFrame {

    private JTextField jTextFieldProdutor;
    private JTextField jTextFieldNomeEpisodio;
    private JTextField jTextFieldNumeroEpisodio;
    private JTextField jTextFieldDuracao;
    private JTextField jTextFieldUrlRepositorio;
    private JButton jButtonSalvar;
    private JButton jButtonListar;
    private Usuario usuarioLogado;

     /**
     * Construtor da classe TelaCadastro.
     * 
     * @param usuario O usuário logado que acessou esta tela.
     */
    public TelaCadastro(Usuario usuario) {
        this.usuarioLogado = usuario;
        initComponents();
    }

    private void initComponents() {
        // Configurações da janela
        setTitle("CENAFLIX - Cadastrar Podcast");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Título
        JLabel titulo = new JLabel("CENAFLIX", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titulo);

        // Subtítulo
        JLabel subTitulo = new JLabel("CADASTRAR PODCAST", SwingConstants.CENTER);
        subTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(subTitulo);

        // Campo de Produtor
        JLabel labelProdutor = new JLabel("Produtor");
        labelProdutor.setFont(new Font("Arial", Font.PLAIN, 16));
        labelProdutor.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelProdutor);

        jTextFieldProdutor = new JTextField(20);
        jTextFieldProdutor.setMaximumSize(new Dimension(300, 30));
        jTextFieldProdutor.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jTextFieldProdutor);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo de Nome do Episódio
        JLabel labelNomeEpisodio = new JLabel("Nome do Episódio");
        labelNomeEpisodio.setFont(new Font("Arial", Font.PLAIN, 16));
        labelNomeEpisodio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelNomeEpisodio);

        jTextFieldNomeEpisodio = new JTextField(20);
        jTextFieldNomeEpisodio.setMaximumSize(new Dimension(300, 30));
        jTextFieldNomeEpisodio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jTextFieldNomeEpisodio);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo de Número do Episódio
        JLabel labelNumeroEpisodio = new JLabel("Nº do Episódio");
        labelNumeroEpisodio.setFont(new Font("Arial", Font.PLAIN, 16));
        labelNumeroEpisodio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelNumeroEpisodio);

        jTextFieldNumeroEpisodio = new JTextField(20);
        jTextFieldNumeroEpisodio.setMaximumSize(new Dimension(300, 30));
        jTextFieldNumeroEpisodio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jTextFieldNumeroEpisodio);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo de Duração
        JLabel labelDuracao = new JLabel("Duração");
        labelDuracao.setFont(new Font("Arial", Font.PLAIN, 16));
        labelDuracao.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelDuracao);

        jTextFieldDuracao = new JTextField(20);
        jTextFieldDuracao.setMaximumSize(new Dimension(300, 30));
        jTextFieldDuracao.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jTextFieldDuracao);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo de URL do Repositório
        JLabel labelUrlRepositorio = new JLabel("URL do repositório");
        labelUrlRepositorio.setFont(new Font("Arial", Font.PLAIN, 16));
        labelUrlRepositorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(labelUrlRepositorio);

        jTextFieldUrlRepositorio = new JTextField(20);
        jTextFieldUrlRepositorio.setMaximumSize(new Dimension(300, 30));
        jTextFieldUrlRepositorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(jTextFieldUrlRepositorio);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        jButtonSalvar = new JButton("Cadastrar");
        jButtonSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        jButtonSalvar.setBackground(Color.GRAY);
        jButtonSalvar.setFocusPainted(false);
        jButtonSalvar.addActionListener(this::salvarPodcast);
        buttonPanel.add(jButtonSalvar);

        jButtonListar = new JButton("Ver Listagem");
        jButtonListar.setFont(new Font("Arial", Font.BOLD, 16));
        jButtonListar.setBackground(Color.GRAY);
        jButtonListar.setFocusPainted(false);
        jButtonListar.addActionListener(this::listarPodcasts);
        buttonPanel.add(jButtonListar);

        mainPanel.add(buttonPanel);

        // Adicionar painel principal à janela
        add(mainPanel, BorderLayout.CENTER);
    }

    private void salvarPodcast(ActionEvent e) {
        String produtor = jTextFieldProdutor.getText();
        String nomeEpisodio = jTextFieldNomeEpisodio.getText();
        String numeroEpisodioStr = jTextFieldNumeroEpisodio.getText();
        String duracao = jTextFieldDuracao.getText();
        String urlRepositorio = jTextFieldUrlRepositorio.getText();

        if (produtor.isEmpty() || nomeEpisodio.isEmpty() || numeroEpisodioStr.isEmpty() || duracao.isEmpty() || urlRepositorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numeroEpisodio;
        try {
            numeroEpisodio = Integer.parseInt(numeroEpisodioStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número do episódio deve ser um valor numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Podcast podcast = new Podcast();
        podcast.setProdutor(produtor);
        podcast.setNomeEpisodio(nomeEpisodio);
        podcast.setNumEpisodio(numeroEpisodio);
        podcast.setDuracao(duracao);
        podcast.setUrl(urlRepositorio);

        PodcastDAO podcastDAO = new PodcastDAO();
        try {
            podcastDAO.salvar(podcast);
            JOptionPane.showMessageDialog(this, "Podcast salvo com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o podcast: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarPodcasts(ActionEvent e) {
        new TelaListagem(usuarioLogado).setVisible(true);
        dispose();
    }
}
