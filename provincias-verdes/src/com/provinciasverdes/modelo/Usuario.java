package com.provinciasverdes.modelo;

import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Usuario: Hereda de EntidadBase.
 * Aplica ENCAPSULAMIENTO: Atributos privados.
 */
public class Usuario extends EntidadBase {
    private String nombre;
    private String email;
    private String contrasena;
    private TipoUsuario tipo;
    private EstadoUsuario estado;
    private Perfil perfil; // Composición
    // Relación 1 : N
    private List<Ubicacion> ubicaciones;

    public Usuario() {
        super();
        this.ubicaciones = new ArrayList<>(); // Uso de ArrayList (TP3 y TP4)
    }

    public Usuario(int id, String nombre, String email, String contrasena, TipoUsuario tipo, EstadoUsuario estado, Perfil perfil) {
        super(id);
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.tipo = tipo;
        this.estado = estado;
        this.perfil = perfil;
        this.ubicaciones = new ArrayList<>();
    }

    // Getters y Setters con validaciones
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        if(nombre != null && !nombre.isBlank()) this.nombre = nombre; 
    }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public List<Ubicacion> getUbicaciones() { return ubicaciones; }

    // Métodos de Negocio
    public boolean cambiarContrasena(String nueva, String actual) {
        if(this.contrasena.equals(actual)) {
            this.contrasena = nueva;
            return true;
        }
        return false;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("👤 Usuario ID: " + id + " | Nombre: " + nombre + " | Tipo: " + tipo + " | Estado: " + estado);
    }

    @Override
    public String paraArchivo() {
        return id + ";" + nombre + ";" + email + ";" + tipo + ";" + estado;
    }
}
