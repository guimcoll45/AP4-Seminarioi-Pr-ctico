package com.provinciasverdes.modelo;

public class Ubicacion extends EntidadBase {
    private String direccion;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private Integer idUsuario;

    public Ubicacion() {}

    // Getters y Setters
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public void mostrarDatos() {
        System.out.printf("ID: %d | %s, %s, %s | CP: %s%n",
                getId(), provincia, ciudad, direccion, codigoPostal != null ? codigoPostal : "Sin CP");
    }

    @Override
    public String paraArchivo() {
        return getId() + ";" + provincia + ";" + ciudad + ";" + direccion + ";" + (codigoPostal != null ? codigoPostal : "");
    }
}
