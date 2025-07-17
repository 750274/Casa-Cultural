package br.com.cflix.dao;

import br.com.cflix.modelo.Podcast;
import br.com.cflix.util.JPAutil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * A classe PodcastDAO realiza operações de persistência relacionadas à entidade {@link Podcast}.
 * Utiliza o JPA para interagir com o banco de dados e permite ações como salvar, buscar e excluir podcasts.
 * 
 * Métodos principais:
 * - {@code buscarPorProdutor}: Busca podcasts com base no nome do produtor.
 * - {@code buscarTodos}: Retorna uma lista com todos os podcasts cadastrados.
 * - {@code salvar}: Salva um novo podcast no banco de dados.
 * - {@code excluir}: Remove um podcast do banco de dados com base no ID.
 */
public class PodcastDAO {

    private static final Logger LOGGER = Logger.getLogger(PodcastDAO.class.getName());

    public List<Podcast> buscarPorProdutor(String produtor) {
        EntityManager em = JPAutil.getEntityManager();
        List<Podcast> podcasts;

        try {
            podcasts = em.createQuery("SELECT p FROM Podcast p WHERE p.produtor = :produtor", Podcast.class)
                    .setParameter("produtor", produtor)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar podcasts por produtor: " + produtor, e);
            throw new RuntimeException("Erro ao buscar podcasts por produtor.", e);
        } finally {
            em.close();
        }

        return podcasts;
    }

    public List<Podcast> buscarTodos() {
        EntityManager em = JPAutil.getEntityManager();
        List<Podcast> podcasts;

        try {
            podcasts = em.createQuery("SELECT p FROM Podcast p", Podcast.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar todos os podcasts.", e);
            throw new RuntimeException("Erro ao buscar todos os podcasts.", e);
        } finally {
            em.close();
        }

        return podcasts;
    }

    public void salvar(Podcast podcast) {
        EntityManager em = JPAutil.getEntityManager();

        try {
            System.out.println("Iniciando a transação para salvar o podcast: " + podcast); // Log adicional
            em.getTransaction().begin();
            em.persist(podcast);
            em.getTransaction().commit();
            System.out.println("Podcast salvo com sucesso: " + podcast); // Log adicional
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o podcast: " + podcast, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transação desfeita devido a erro."); // Log adicional
            }
            throw new RuntimeException("Erro ao salvar o podcast.", e);
        } finally {
            em.close();
        }
    }

    public void excluir(int id) {
        EntityManager em = JPAutil.getEntityManager();

        try {
            System.out.println("Iniciando a exclusão do podcast com ID: " + id); // Log adicional
            em.getTransaction().begin();
            Podcast podcast = em.find(Podcast.class, id);
            if (podcast != null) {
                em.remove(podcast);
                System.out.println("Podcast excluído com sucesso: " + podcast); // Log adicional
            } else {
                LOGGER.log(Level.WARNING, "Podcast com ID {0} não encontrado.", id);
                System.out.println("Podcast com ID " + id + " não encontrado."); // Log adicional
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao excluir o podcast com ID: " + id, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transação desfeita devido a erro."); // Log adicional
            }
            throw new RuntimeException("Erro ao excluir o podcast.", e);
        } finally {
            em.close();
        }
    }
}
