package br.com.cflix.visualizar;

import br.com.cflix.modelo.Usuario;
import br.com.cflix.modelo.Podcast;
import br.com.cflix.dao.PodcastDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
/**
 * A classe TelaListagem representa a interface de listagem de podcasts na aplicação CENAFLIX.
 * Permite que usuários listem e filtrem podcasts com base no produtor. 
 * Usuários com permissões específicas podem cadastrar ou excluir podcasts diretamente desta interface.
 * 
 * Funcionalidades principais:
 * - Exibir todos os podcasts cadastrados.
 * - Filtrar podcasts por produtor.
 * - Adicionar novos podcasts (para administradores e operadores).
 * - Excluir podcasts (somente para administradores).
 */
public class TelaListagem extends JFrame {

    private Usuario usuarioLogado;
    private JTextField campoFiltroProdutor;
    private JTable tabela;
    private PodcastDAO podcastDAO;
    private JButton botaoCadastrar;
    private JButton botaoExcluir;

    public TelaListagem(Usuario usuario) {
        this.usuarioLogado = usuario;
        podcastDAO = new PodcastDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("CENAFLIX - Listagem");
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
        JLabel subTitulo = new JLabel("LISTAGEM", SwingConstants.CENTER);
        subTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(subTitulo);

        // Filtro de produtor
        JPanel painelFiltro = new JPanel();
        painelFiltro.setBackground(Color.LIGHT_GRAY);
        JLabel labelFiltro = new JLabel("Pesquisar podcast por produtor");
        labelFiltro.setFont(new Font("Arial", Font.PLAIN, 16));
        campoFiltroProdutor = new JTextField(20);
        JButton botaoFiltrar = new JButton("Filtrar");
        botaoFiltrar.setFont(new Font("Arial", Font.BOLD, 16));
        botaoFiltrar.setBackground(Color.GRAY);
        botaoFiltrar.setFocusPainted(false);
        botaoFiltrar.addActionListener(this::filtrarPorProdutor);
        painelFiltro.add(labelFiltro);
        painelFiltro.add(campoFiltroProdutor);
        painelFiltro.add(botaoFiltrar);
        mainPanel.add(painelFiltro);

        // Tabela
        tabela = new JTable(new DefaultTableModel(new Object[]{"ID", "Produtor", "Nome do Episódio", "Nº Episódio", "Duração", "URL do Repo"}, 0));
        JScrollPane scrollPane = new JScrollPane(tabela);
        mainPanel.add(scrollPane);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        if (usuarioLogado.getTipo().equalsIgnoreCase("administrador") || usuarioLogado.getTipo().equalsIgnoreCase("operador")) {
            botaoCadastrar = new JButton("Cadastrar");
            botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 16));
            botaoCadastrar.setBackground(Color.GRAY);
            botaoCadastrar.setFocusPainted(false);
            botaoCadastrar.addActionListener(this::abrirTelaCadastro);
            buttonPanel.add(botaoCadastrar);
        }

        if (usuarioLogado.getTipo().equalsIgnoreCase("administrador")) {
            botaoExcluir = new JButton("Excluir");
            botaoExcluir.setFont(new Font("Arial", Font.BOLD, 16));
            botaoExcluir.setBackground(Color.GRAY);
            botaoExcluir.setFocusPainted(false);
            botaoExcluir.addActionListener(this::excluirPodcast);
            buttonPanel.add(botaoExcluir);
        }

        mainPanel.add(buttonPanel);

        // Adicionar painel principal
        add(mainPanel, BorderLayout.CENTER);
        listarTodos();
    }

    private void listarTodos() {
        List<Podcast> podcasts = podcastDAO.buscarTodos();
        preencherTabela(podcasts);
    }

    private void filtrarPorProdutor(ActionEvent e) {
        String produtor = campoFiltroProdutor.getText().trim();
        List<Podcast> podcasts = podcastDAO.buscarPorProdutor(produtor);
        preencherTabela(podcasts);
    }

    private void preencherTabela(List<Podcast> podcasts) {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
        for (Podcast podcast : podcasts) {
            model.addRow(new Object[]{
                    podcast.getId(),
                    podcast.getProdutor(),
                    podcast.getNomeEpisodio(),
                    podcast.getNumEpisodio(),
                    podcast.getDuracao(),
                    podcast.getUrl()
            });
        }
    }

    private void abrirTelaCadastro(ActionEvent e) {
        new TelaCadastro(usuarioLogado).setVisible(true);
        dispose();
    }

    private void excluirPodcast(ActionEvent e) {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow != -1) {
            int podcastId = (int) tabela.getValueAt(selectedRow, 0);

            // Personalização da janela de confirmação
            UIManager.put("OptionPane.background", Color.LIGHT_GRAY);
            UIManager.put("Panel.background", Color.LIGHT_GRAY);
            UIManager.put("Button.background", Color.GRAY);
            UIManager.put("Button.foreground", Color.BLACK);

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir este podcast?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    podcastDAO.excluir(podcastId);
                    listarTodos();
                    JOptionPane.showMessageDialog(this, "Podcast excluído com sucesso.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir o podcast: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Restaura o padrão do JOptionPane após a exibição
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);

        } else {
            JOptionPane.showMessageDialog(this, "Selecione um podcast para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}