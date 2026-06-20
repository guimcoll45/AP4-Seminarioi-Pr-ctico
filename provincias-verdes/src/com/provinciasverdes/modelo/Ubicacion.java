package com.provinciasverdes.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Ubicacion: Geolocalización de instalaciones.
 * Cumple RF2: Gestión de ubicaciones geográficas.
 */
public class Ubicacion extends EntidadBase {
    private String provincia;
    private String direccion;
    private double latitud;
    private double longitud;
    private int idUsuario; // Clave Foránea

    // ✅ USO DE ARREGLO (REQUISITO TP4) - Datos fijos, no cambian
    public static final String[] PROVINCIAS = {"Formosa", "Chaco", "Corrientes", "Misiones", "Santa Fe", "Entre Ríos"};
    
    // Relación 1:N
    private List<EquipoSolar> equipos;

    public Ubicacion() {
        super();
        this.equipos = new ArrayList<>(); // ✅ USO DE ARRAYLIST
    }

    public Ubicacion(int id, String provincia, String direccion, double latitud, double longitud, int idUsuario) {
        super(id);
        this.provincia = provincia;
        this.direccion = direccion;
        setLatitud(latitud); // Uso de set con validación
        setLongitud(longitud);
        this.idUsuario = idUsuario;
        this.equipos = new ArrayList<>();
    }

    // Getters y Setters con validación de coordenadas (RNF4 Integridad)
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) {
        if(latitud >= -90 && latitud <= 90) this.latitud = latitud;
        else throw new IllegalArgumentException("Latitud fuera de rango (-90 a 90)");
    }
    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) {
        if(longitud >= -180 && longitud <= 180) this.longitud = longitud;
        else throw new IllegalArgumentException("Longitud fuera de rango (-180 a 180)");
    }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public List<EquipoSolar> getEquipos() { return equipos; }

    // Método de negocio
    public double calcularDistancia(double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat2 - this.latitud, 2) + Math.pow(lon2 - this.longitud, 2)) * 111;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("📍 Ubicación ID: " + id + " | " + direccion + ", " + provincia + " | Coords: (" + latitud + ", " + longitud + ")");
    }

    @Override
    public String paraArchivo() {
        return id + ";" + provincia + ";" + direccion + ";" + latitud + ";" + longitud + ";" + idUsuario;
    }
}
