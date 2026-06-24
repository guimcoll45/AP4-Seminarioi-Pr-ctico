package com.provinciasverdes.modelo;

public abstract class EntidadBase {
    private int id;

    // Constructor vacío
    public EntidadBase() {}

    // Constructor con ID
    public EntidadBase(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos obligatorios para todas las entidades
    public abstract String paraArchivo();
    public abstract void mostrarDatos();
}