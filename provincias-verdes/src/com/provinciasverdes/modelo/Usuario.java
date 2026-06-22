package com.provinciasverdes.modelo;

import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;

public class Usuario extends EntidadBase {
    private String nombre;
    private String email;
    private String contrasena;
    private TipoUsuario tipo;
    private EstadoUsuario estado;

    public Usuario() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }

    @Override
    public void mostrarDatos() {
        System.out.println("ID: " + id + " | Nombre: " + nombre + " | Email: " + email + " | Tipo: " + tipo);
    }

    @Override
    public String paraArchivo() {
        return id + ";" + nombre + ";" + email + ";" + tipo + ";" + estado;
    }
}
