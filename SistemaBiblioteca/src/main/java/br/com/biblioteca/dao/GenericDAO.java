package br.com.biblioteca.dao;

import java.util.List;

public interface GenericDAO<T> {
    void salvar(T entity);
    void atualizar(T entity);
    void deletar(T entity);
    T buscarPorId(int id);
    List<T> listarTodos();
}
