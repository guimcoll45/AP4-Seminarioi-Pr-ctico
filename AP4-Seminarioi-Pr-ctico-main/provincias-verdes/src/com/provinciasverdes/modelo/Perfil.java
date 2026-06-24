package com.provinciasverdes.modelo;

public class Perfil extends EntidadBase {
    private String nombre;
    private String correo;

    public Perfil() {}

    public Perfil(int id, String nombre, String correo) {
        super(id);
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("ID: " + getId() + " | Nombre: " + nombre + " | Correo: " + correo);
    }

    @Override
    public String paraArchivo() {
        return getId() + ";" + nombre + ";" + correo;
    }
}