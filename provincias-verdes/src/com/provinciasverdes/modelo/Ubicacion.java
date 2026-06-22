package com.provinciasverdes.modelo;

public class Ubicacion extends EntidadBase {
    private int idUsuario;
    private String provincia;
    private String direccion;
    private double latitud;
    private double longitud;

    public Ubicacion() {}

    public Ubicacion(int id, int idUsuario, String provincia, String direccion, double latitud, double longitud) {
        super(id);
        this.idUsuario = idUsuario;
        this.provincia = provincia;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "id=" + id +
                ", provincia='" + provincia + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
