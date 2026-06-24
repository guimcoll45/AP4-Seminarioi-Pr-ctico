package com.provinciasverdes.modelo;

import java.time.LocalDate;

public class EquipoSolar extends EntidadBase {
    private String modelo;
    private double potenciaNominal;
    private String tipo;
    private LocalDate fechaInstalacion;
    private String estado;
    private Integer idUbicacion;

    public EquipoSolar() {}

    public EquipoSolar(int id, String modelo, double potenciaNominal, String tipo, LocalDate fechaInstalacion, String estado, Integer idUbicacion) {
        super(id);
        this.modelo = modelo;
        this.potenciaNominal = potenciaNominal;
        this.tipo = tipo;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = estado;
        this.idUbicacion = idUbicacion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPotenciaNominal() {
        return potenciaNominal;
    }

    public void setPotenciaNominal(double potenciaNominal) {
        this.potenciaNominal = potenciaNominal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(LocalDate fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    @Override
    public void mostrarDatos() {
        System.out.printf("ID: %d | Modelo: %s | Potencia: %.2f kW | Tipo: %s | Estado: %s | Ubicación ID: %s%n",
                getId(), modelo, potenciaNominal, tipo, estado, idUbicacion != null ? idUbicacion : "Sin asignar");
    }

    @Override
    public String paraArchivo() {
        return String.format("%d;%s;%.2f;%s;%s;%s;%s",
                getId(), modelo, potenciaNominal, tipo, fechaInstalacion, estado, idUbicacion);
    }
}