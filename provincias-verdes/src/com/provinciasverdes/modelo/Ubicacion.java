package com.provinciasverdes.modelo;

public class Ubicacion extends EntidadBase {
    private String provincia;
    private String direccion;
    private double latitud;
    private double longitud;
    private int idUsuario;

    public Ubicacion() {}

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public void mostrarDatos() {
        System.out.println("ID: " + id + " | Prov: " + provincia + " | Dir: " + direccion + " | Usuario ID: " + idUsuario);
    }

    @Override
    public String paraArchivo() {
        return id + ";" + provincia + ";" + direccion + ";" + latitud + ";" + longitud + ";" + idUsuario;
    }
}
