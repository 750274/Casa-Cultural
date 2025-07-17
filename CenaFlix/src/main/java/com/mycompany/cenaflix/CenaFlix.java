package com.mycompany.cenaflix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CenaFlix {
    
    // Metodo para informar dados da conexao do BD
    public static Connection conexao() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cenaflix"; // Url do banco
        String usuario = "root"; // Usuário
        String senha = "Joao8"; // Senha 
        return DriverManager.getConnection(url, usuario, senha); // Retorna a conexao
    }

    //Metodo para garantir que a conexão foi executada corretamente
    public static void main(String[] args) {
        try (Connection conexao = conexao()) {
            if (conexao != null) {
                System.out.println("Conexão estabelecida com sucesso ao banco de dados: " + conexao.getCatalog());
            } else {
                System.out.println("Não foi possível estabelecer a conexão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}

