package br.com.biblioteca.dao;

import br.com.biblioteca.modelo.Livro;
import br.com.biblioteca.util.JPAutil;

import jakarta.persistence.EntityManager;
import java.util.List;

public class LivroDAO {

    public void salvar(Livro livro) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(livro);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void atualizar(Livro livro) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(livro);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deletar(Livro livro) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            Livro livroEncontrado = em.find(Livro.class, livro.getId());
            if (livroEncontrado != null) {
                em.remove(livroEncontrado);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Livro> buscarTodos() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Livro buscarPorId(int id) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.find(Livro.class, id);
        } finally {
            em.close();
        }
    }

    public Livro buscarPorTitulo(String titulo) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Livro l WHERE l.titulo = :titulo", Livro.class)
                     .setParameter("titulo", titulo)
                     .getSingleResult();
        } catch (Exception e) {
            return null; 
        } finally {
            em.close();
        }
    }
}
