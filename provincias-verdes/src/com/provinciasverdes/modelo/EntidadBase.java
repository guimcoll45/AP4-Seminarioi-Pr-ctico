package com.provinciasverdes.modelo;

public abstract class EntidadBase {
    protected int id;

    // Constructores
    public EntidadBase() {}
    public EntidadBase(int id) {
        this.id = id;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Métodos abstractos (Obligatorios para todas las hijas)
    public abstract void mostrarDatos();
    public abstract String paraArchivo(); // Nuevo para TP4
}
