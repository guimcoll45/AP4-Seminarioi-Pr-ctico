package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.EntidadBase;
import java.util.List;

public abstract class DAOBase<T extends EntidadBase> {
    protected ConexionDB conexionDB = ConexionDB.getInstancia();

    public abstract boolean agregar(T entidad);
    public abstract boolean actualizar(T entidad);
    public abstract boolean eliminar(int id);
    public abstract T obtenerPorId(int id);
    public abstract List<T> obtenerTodos();
}
