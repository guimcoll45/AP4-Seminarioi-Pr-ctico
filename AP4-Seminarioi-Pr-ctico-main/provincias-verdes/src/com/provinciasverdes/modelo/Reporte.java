package com.provinciasverdes.modelo;

public class Reporte extends EntidadBase {
    private String titulo;
    private String contenido;

    public Reporte() {}

    public Reporte(int id, String titulo, String contenido) {
        super(id);
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Reporte ID: " + getId() + " | Título: " + titulo);
    }

    @Override
    public String paraArchivo() {
        return getId() + ";" + titulo + ";" + contenido;
    }
}