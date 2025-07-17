package br.com.cflix.dao;

import br.com.cflix.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 * A classe UsuarioDAO é responsável por realizar operações de persistência relacionadas à entidade {@link Usuario}.
 * Utiliza JPA para interagir com o banco de dados, fornecendo métodos para buscar usuários com base em seus
 * atributos, como nome e senha, e para gerenciar a conexão com o banco.
 */
public class UsuarioDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CflixPodCastPU");

    /**
     * Método para buscar usuário pelo nome de usuário.
     *
     * @param usuario Nome do usuário a ser buscado.
     * @return Instância de Usuario se encontrado, ou null se não encontrado.
     */
    public Usuario buscarPorUsuario(String usuario) {
        EntityManager em = emf.createEntityManager();
        Usuario usuarioEncontrado = null;

        try {
            System.out.println("Buscando usuário com Login: " + usuario); // Log para depuração
            usuarioEncontrado = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :usuario", Usuario.class)
                                  .setParameter("usuario", usuario)
                                  .getSingleResult();
            System.out.println("Usuário encontrado: " + usuarioEncontrado.getLogin());
        } catch (Exception e) {
            e.printStackTrace(); // Log da exceção para depuração
            System.err.println("Erro ao buscar usuário por nome: " + e.getMessage());
        } finally {
            em.close();
        }

        return usuarioEncontrado;
    }

    /**
     * Método para buscar usuário pelo nome e senha.
     *
     * @param nome Nome do usuário.
     * @param senha Senha do usuário.
     * @return Instância de Usuario se encontrado, ou null se não encontrado.
     */
    public Usuario buscarPorNomeESenha(String nome, String senha) {
        EntityManager em = emf.createEntityManager();
        Usuario usuarioEncontrado = null;

        try {
            System.out.println("Buscando usuário com Login: " + nome + " e Senha: " + senha); // Log para depuração
            usuarioEncontrado = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :nome AND u.senha = :senha", Usuario.class)
                                  .setParameter("nome", nome)
                                  .setParameter("senha", senha)
                                  .getSingleResult();
            System.out.println("Usuário encontrado: " + usuarioEncontrado.getLogin());
        } catch (Exception e) {
            e.printStackTrace(); // Log da exceção para depuração
            System.err.println("Erro ao buscar usuário por nome e senha: " + e.getMessage());
        } finally {
            em.close();
        }

        return usuarioEncontrado;
    }

    /**
     * Fecha a EntityManagerFactory ao encerrar a aplicação.
     */
    public static void fecharConexao() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
