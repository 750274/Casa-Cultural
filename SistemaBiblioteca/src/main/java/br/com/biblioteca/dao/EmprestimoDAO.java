package br.com.biblioteca.dao;

import br.com.biblioteca.modelo.Emprestimo;
import br.com.biblioteca.util.JPAutil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class EmprestimoDAO {

    public void salvar(Emprestimo emprestimo) {
    EntityManager em = JPAutil.getEntityManager();
    try {
        em.getTransaction().begin();
        
        // Garantir que Livro e Usuario estão gerenciados pelo EntityManager
        emprestimo.setLivro(em.merge(emprestimo.getLivro()));
        emprestimo.setUsuario(em.merge(emprestimo.getUsuario()));
        
        em.persist(emprestimo);
        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        throw new RuntimeException("Erro ao salvar o empréstimo", e);
    } finally {
        em.close();
    }
}


    public void atualizar(Emprestimo emprestimo) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(emprestimo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar o empréstimo", e);
        } finally {
            em.close();
        }
    }

    public void deletar(Emprestimo emprestimo) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            emprestimo = em.find(Emprestimo.class, emprestimo.getId());
            em.remove(emprestimo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar o empréstimo", e);
        } finally {
            em.close();
        }
    }

    public List<Emprestimo> buscarTodos() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Emprestimo e", Emprestimo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Emprestimo buscarPorId(int id) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.find(Emprestimo.class, id);
        } finally {
            em.close();
        }
    }

    public Emprestimo buscarPorLivroTitulo(String titulo) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Emprestimo e WHERE e.livro.titulo = :titulo AND e.status = 'Em Andamento'", Emprestimo.class)
                    .setParameter("titulo", titulo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum empréstimo for encontrado
        } finally {
            em.close();
        }
    }
}
