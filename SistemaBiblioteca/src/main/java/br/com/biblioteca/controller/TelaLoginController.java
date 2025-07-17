package br.com.biblioteca.controller;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.modelo.Usuario;

public class TelaLoginController {
    private final UsuarioDAO usuarioDAO;

    public TelaLoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticarUsuario(String login, String senha) {
        return usuarioDAO.buscarPorLoginESenha(login, senha);
    }
}
