package com.provinciasverdes.modelo;

public class Usuario extends EntidadBase {
    private String nombre;
    private String correo;
    private String contrasena;
    private String perfil; // ADMIN o USUARIO

    public Usuario() {
        super();
    }

    public Usuario(int id, String nombre, String correo, String contrasena, String perfil) {
        super(id);
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.perfil = perfil;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== USUARIO ===");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Perfil: " + perfil);
    }
}
