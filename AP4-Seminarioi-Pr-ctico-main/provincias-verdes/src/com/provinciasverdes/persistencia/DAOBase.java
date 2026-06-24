package com.provinciasverdes.persistencia;

import java.util.List;

public abstract class DAOBase<T> {
    public abstract List<T> listarTodos();
    public abstract T leer(int id); // ⚠️ OBLIGATORIO implementar
    public abstract boolean crear(T entidad);
    public abstract boolean actualizar(T entidad);
    public abstract boolean borrar(int id);
}