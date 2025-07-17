package br.com.cflix.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A classe JPAutil é uma utilitária responsável por gerenciar a criação e fechamento
 * de instâncias do EntityManager e EntityManagerFactory no contexto da aplicação.
 * 
 * Principais funcionalidades:
 * - Configura o EntityManagerFactory com base nas definições do arquivo persistence.xml.
 * - Fornece métodos para obter instâncias do EntityManager.
 * - Garante o fechamento do EntityManagerFactory quando necessário, evitando vazamentos de recursos.
 */
public class JPAutil {

    private static final Logger LOGGER = Logger.getLogger(JPAutil.class.getName());
    private static final EntityManagerFactory emf;

    static {
        EntityManagerFactory tempEmf = null;
        try {
            tempEmf = Persistence.createEntityManagerFactory("CflixPodCastPU");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar EntityManagerFactory", e);
            throw new ExceptionInInitializerError("Erro ao inicializar EntityManagerFactory: " + e.getMessage());
        }
        emf = tempEmf;
    }

    /**
     * Retorna uma nova instância de EntityManager conectada ao banco de dados.
     * 
     * @return Uma instância de {@link EntityManager}.
     * @throws IllegalStateException Se o EntityManagerFactory não estiver disponível ou estiver fechado.
     */
    public static EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory não está disponível ou foi fechado.");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            try {
                emf.close();
                LOGGER.info("EntityManagerFactory fechado com sucesso.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Erro ao fechar EntityManagerFactory", e);
            }
        }
    }
}
