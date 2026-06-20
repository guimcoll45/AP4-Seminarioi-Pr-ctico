package com.provinciasverdes.modelo;

/**
 * Clase Perfil: Composición 1 a 1 con Usuario.
 * Encapsula datos personales.
 */
public class Perfil extends EntidadBase {
    private String telefono;
    private String direccionEmail;
    private String descripcion;

    public Perfil() { super(); }
    public Perfil(int id, String telefono, String direccionEmail, String descripcion) {
        super(id);
        this.telefono = telefono;
        this.direccionEmail = direccionEmail;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccionEmail() { return direccionEmail; }
    public void setDireccionEmail(String direccionEmail) { this.direccionEmail = direccionEmail; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public void mostrarDatos() {
        System.out.println("Perfil: Tel: " + telefono + " | Mail: " + direccionEmail);
    }

    @Override
    public String paraArchivo() {
        return id + ";" + telefono + ";" + direccionEmail + ";" + descripcion;
    }
}
