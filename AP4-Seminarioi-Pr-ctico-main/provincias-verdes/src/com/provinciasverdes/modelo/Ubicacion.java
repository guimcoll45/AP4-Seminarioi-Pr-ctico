package com.provinciasverdes.modelo;

public class Ubicacion extends EntidadBase {
    private String provincia;
    private String ciudad;
    private String direccion;
    private String codigoPostal;
    private Integer idUsuario;

    public Ubicacion() {}

    public Ubicacion(int id, String provincia, String ciudad, String direccion, String codigoPostal, Integer idUsuario) {
        super(id);
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.idUsuario = idUsuario;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public void mostrarDatos() {
        System.out.printf("ID: %d | %s, %s | Dirección: %s | CP: %s | ID Usuario: %d%n",
                getId(), provincia, ciudad, direccion, codigoPostal, idUsuario);
    }

    @Override
    public String paraArchivo() {
        return String.format("%d;%s;%s;%s;%s;%d",
                getId(), provincia, ciudad, direccion, codigoPostal, idUsuario);
    }
}