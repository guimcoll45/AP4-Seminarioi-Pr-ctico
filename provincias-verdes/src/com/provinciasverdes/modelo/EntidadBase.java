package com.provinciasverdes.modelo;

/**
 * Clase Abstracta: Superclase de todas las entidades del sistema.
 * Aplica ABSTRACCIÓN: Define estructura común sin detalles internos.
 */
public abstract class EntidadBase {
    protected int id; // Clave primaria común

    // Constructores
    public EntidadBase() {}
    public EntidadBase(int id) {
        this.id = id;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Métodos abstractos: OBLIGAN a las clases hijas a implementarlos
    public abstract void mostrarDatos(); // POLIMORFISMO
    public abstract String paraArchivo(); // Para exportar información
}
