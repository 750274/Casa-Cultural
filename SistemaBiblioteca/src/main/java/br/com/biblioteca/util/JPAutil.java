package br.com.biblioteca.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAutil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");

    // Método para obter o EntityManager
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para obter o EntityManagerFactory, se necessário
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // Método para fechar o EntityManagerFactory ao finalizar a aplicação
    public static void closeEntityManagerFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
