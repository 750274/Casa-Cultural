package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {

    // Dados para a conexão com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/Biblioteca";
    private static final String USUARIO = "root";
    private static final String SENHA = "Joao8";

    /**
     * Método para obter uma conexão com o banco de dados.
     *
     */
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     * Método principal para testar a conexão com o banco.
     */
    public static void main(String[] args) {
        try (Connection conexao = getConexao()) {
            if (conexao != null) {
                System.out.println("Conexão com o banco de dados foi bem-sucedida!");
            } else {
                System.out.println("Falha na conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
