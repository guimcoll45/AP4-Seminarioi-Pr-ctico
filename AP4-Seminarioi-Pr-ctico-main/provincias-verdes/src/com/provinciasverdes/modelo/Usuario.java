package com.provinciasverdes.modelo;

import com.provinciasverdes.modelo.enums.EstadoUsuario;
import com.provinciasverdes.modelo.enums.TipoUsuario;

public class Usuario extends EntidadBase {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private TipoUsuario tipoUsuario;
    private EstadoUsuario estado;
    private String perfil;

    public Usuario() {}

    public Usuario(int id, String nombre, String apellido, String correo, String contrasena, TipoUsuario tipoUsuario, EstadoUsuario estado, String perfil) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
        this.perfil = perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public void mostrarDatos() {
        System.out.printf("ID: %d | %s %s | Correo: %s | Tipo: %s | Estado: %s%n",
                getId(), nombre, apellido, correo, tipoUsuario, estado);
    }

    @Override
    public String paraArchivo() {
        return String.format("%d;%s;%s;%s;%s;%s;%s",
                getId(), nombre, apellido, correo, tipoUsuario, estado, perfil);
    }
}