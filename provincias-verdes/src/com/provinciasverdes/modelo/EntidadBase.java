package com.provinciasverdes.modelo;

public abstract class EntidadBase {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void mostrarDatos();
    public abstract String paraArchivo();
}
