/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.cflix.cflixpodcast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
A classe principal da aplicação CflixPodCast.
 * Esta classe tem como propósito inicial testar a conexão com o banco de dados
 * do sistema e garantir que a conexão está funcional antes de prosseguir com
 * outras operações.
 * 
 * Funcionalidades principais:
 * - Testar a conexão com o banco de dados MySQL.
 * - Exibir mensagens indicando o sucesso ou falha da conexão.
 * 
 */
public class CflixPodCast {

    public static void main(String[] args) {
        System.out.println("Testando");

        // Testar a conexão com o banco
        if (testarConexao()) {
            System.out.println("Conexão com o banco de dados foi bem-sucedida!");
        } else {
            System.out.println("Falha na conexão com o banco de dados.");
        }
    }

    /**
     * Método para testar a conexão com o banco de dados.
     *
     * @return true se a conexão for bem-sucedida, false caso contrário.
     */
    public static boolean testarConexao() {
        String url = "jdbc:mysql://localhost:3306/CflixPodCast";
        String usuario = "root";
        String senha = "12345678";

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
            if (conexao != null) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return false;
    }
}
