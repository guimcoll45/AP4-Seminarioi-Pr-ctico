package com.provinciasverdes.modelo;

import com.provinciasverdes.enums.TipoUsuario;

public class Perfil extends EntidadBase {
    private TipoUsuario tipo;
    private String descripcion;

    public Perfil() {}

    public Perfil(int id, TipoUsuario tipo, String descripcion) {
        super(id);
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
