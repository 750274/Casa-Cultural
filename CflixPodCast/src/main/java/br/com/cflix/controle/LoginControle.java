package br.com.cflix.controle;

import br.com.cflix.dao.UsuarioDAO;
import br.com.cflix.modelo.Usuario;
import javax.swing.JOptionPane;

/**
 * A classe LoginControle é responsável por gerenciar a lógica de autenticação e autorização do sistema.
 * Realiza a validação de login, exibe mensagens ao usuário e configura as permissões de acesso com base no tipo de usuário.
 */
public class LoginControle {

    private final UsuarioDAO usuarioDAO;

    public LoginControle() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método para validar login do usuário.
     *
     * @param usuario Nome de usuário fornecido.
     * @param senha   Senha fornecida.
     * @return true se o login for válido, false caso contrário.
     */
    public boolean validarLogin(String usuario, String senha) {
        try {
            // Busca usuário no banco de dados
            Usuario usuarioValidado = usuarioDAO.buscarPorNomeESenha(usuario, senha);

            // Validação do usuário encontrado
            if (usuarioValidado != null) {
                String tipoUsuario = usuarioValidado.getTipo();

                // Exibe mensagem personalizada ao usuário
                JOptionPane.showMessageDialog(
                    null,
                    "Olá " + usuario + ", sua permissão é de " + tipoUsuario + ". Seja bem-vindo!",
                    "Login Bem-sucedido",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Configuração de permissões (simulada aqui, mas pode ser integrada à interface)
                configurarPermissoes(tipoUsuario);

                return true;
            } else {
                // Mensagem de erro caso usuário não seja encontrado
                JOptionPane.showMessageDialog(
                    null,
                    "Usuário ou senha inválidos",
                    "Erro de Login",
                    JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        } catch (Exception e) {
            // Tratamento genérico de exceções
            JOptionPane.showMessageDialog(
                null,
                "Ocorreu um erro ao tentar realizar o login: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    /**
     * Configura permissões de acordo com o tipo de usuário.
     *
     * @param tipoUsuario Tipo do usuário (Administrador, Operador, Usuário).
     */
    private void configurarPermissoes(String tipoUsuario) {
        // Aqui você pode implementar a lógica de habilitar/desabilitar botões ou menus.
        switch (tipoUsuario) {
            case "Administrador":
                // Permissões completas
                habilitarCadastro(true);
                habilitarExclusao(true);
                habilitarListagem(true);
                break;

            case "Operador":
                // Permissões de cadastro e listagem
                habilitarCadastro(true);
                habilitarExclusao(false);
                habilitarListagem(true);
                break;

            case "Usuário":
                // Apenas permissão de listagem
                habilitarCadastro(false);
                habilitarExclusao(false);
                habilitarListagem(true);
                break;

            default:
                JOptionPane.showMessageDialog(
                    null,
                    "Tipo de usuário desconhecido. Contate o administrador do sistema.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
        }
    }

    /**
     * Métodos para habilitar ou desabilitar funcionalidades (simulação).
     */
    private void habilitarCadastro(boolean status) {
        // Implementar lógica de habilitação de botões de cadastro
        System.out.println("Cadastro habilitado: " + status);
    }

    private void habilitarExclusao(boolean status) {
        // Implementar lógica de habilitação de botões de exclusão
        System.out.println("Exclusão habilitada: " + status);
    }

    private void habilitarListagem(boolean status) {
        // Implementar lógica de habilitação de botões de listagem
        System.out.println("Listagem habilitada: " + status);
    }
}
