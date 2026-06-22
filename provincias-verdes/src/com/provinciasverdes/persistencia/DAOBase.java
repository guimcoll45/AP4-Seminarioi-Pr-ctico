package com.provinciasverdes.persistencia;

import java.util.List;

public abstract class DAOBase<T> {
    public abstract boolean crear(T objeto);
    public abstract T leer(int id);
    public abstract boolean actualizar(T objeto);
    public abstract boolean borrar(int id);
    public abstract List<T> listarTodos();
}
