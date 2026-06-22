package com.provinciasverdes.modelo;

import com.provinciasverdes.enums.EstadoUsuario;

public class Usuario extends EntidadBase {
    private String nombre;
    private String correo;
    private String contrasena;
    private Perfil perfil;
    private EstadoUsuario estado;

    public Usuario() {}

    public Usuario(int id, String nombre, String correo, String contrasena, Perfil perfil, EstadoUsuario estado) {
        super(id);
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.perfil = perfil;
        this.estado = estado;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", perfil=" + perfil.getTipo() +
                ", estado=" + estado +
                '}';
    }
}
