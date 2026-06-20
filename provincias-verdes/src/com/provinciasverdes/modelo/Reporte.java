package com.provinciasverdes.modelo;

public class Reporte extends EntidadBase {
    private String titulo;
    private String contenido;
    private String periodo;

    public Reporte() { super(); }

    public Reporte(int id, String titulo, String contenido, String periodo) {
        super(id);
        this.titulo = titulo;
        this.contenido = contenido;
        this.periodo = periodo;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== REPORTE ===");
        System.out.println("Título: " + titulo);
        System.out.println("Periodo: " + periodo);
        System.out.println("Resumen: " + contenido);
    }
}
