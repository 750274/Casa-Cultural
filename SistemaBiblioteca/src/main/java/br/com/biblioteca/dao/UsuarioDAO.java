package br.com.biblioteca.dao;

import br.com.biblioteca.modelo.Usuario;
import br.com.biblioteca.util.JPAutil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class UsuarioDAO implements GenericDAO<Usuario> {

    
    @Override
    public void salvar(Usuario usuario) {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar usuário", e);
        } finally {
            em.close();
        }
    }

    
    @Override
    public void atualizar(Usuario usuario) {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar usuário", e);
        } finally {
            em.close();
        }
    }

    
    @Override
    public void deletar(Usuario usuario) {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            usuario = em.find(Usuario.class, usuario.getId());
            if (usuario != null) {
                em.remove(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar usuário", e);
        } finally {
            em.close();
        }
    }

    
    @Override
    public Usuario buscarPorId(int id) {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

   
    @Override
    public List<Usuario> listarTodos() {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("FROM Usuario", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

  
    public Usuario buscarPorLoginESenha(String login, String senha) {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", Usuario.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se o usuário não for encontrado
        } finally {
            em.close();
        }
    }

    // Buscar usuário por login (adicional)
    public Usuario buscarPorLogin(String login) {
        EntityManager em = JPAutil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } finally {
            em.close();
        }
    }
}
