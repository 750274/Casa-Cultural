package br.com.cflix.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
/**
 * A classe Usuario representa a entidade de um usuário no sistema.
 * 
 * Cada usuário possui atributos como login, senha e tipo de permissão.
 * Essa classe é usada para mapear a tabela "usuarios" no banco de dados,
 * facilitando a manipulação de dados e o controle de permissões no sistema.
 * 
 * Principais atributos:
 * - {@code id}: Identificador único do usuário.
 * - {@code login}: Nome de login do usuário.
 * - {@code senha}: Senha associada ao login do usuário.
 * - {@code tipo}: Tipo de permissão do usuário (ex.: Administrador, Operador, Usuário).
 */
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método para obter o nome de usuário.
     * Retorna o valor da propriedade 'login'.
     * 
     * @return Nome do usuário (login).
     */
    public String getUsuario() {
        return this.login;
    }

    /**
     * Método para representar o objeto como String.
     * Útil para logs e depuração.
     * 
     * @return String representando o objeto Usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
