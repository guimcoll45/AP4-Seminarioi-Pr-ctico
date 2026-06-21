package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.EntidadBase;
import java.util.List;

/**
 * Patrón de Diseño DAO: Objeto de Acceso a Datos.
 * Clase Abstracta: Define operaciones CRUD genéricas.
 * Desacopla la lógica de negocio de la base de datos.
 */
public abstract class DAOBase<T extends EntidadBase> {
    protected ConexionDB conexionDB;

    public DAOBase() {
        this.conexionDB = ConexionDB.getInstancia();
    }

    // Operaciones obligatorias para todas las entidades
    public abstract boolean crear(T entidad);
    public abstract T leer(int id);
    public abstract boolean actualizar(T entidad);
    public abstract boolean borrar(int id);
    public abstract List<T> listarTodos();
}
