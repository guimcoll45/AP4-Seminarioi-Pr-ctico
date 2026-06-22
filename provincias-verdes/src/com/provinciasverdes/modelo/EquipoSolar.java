package com.provinciasverdes.modelo;

import java.time.LocalDateTime;

public class EquipoSolar {
    private int id;
    private int idUbicacion;
    private String tipo;
    private double potencia;
    private String modelo;
    private LocalDateTime fechaInstalacion;
    private String estado;

    public EquipoSolar() {}

    public EquipoSolar(int id, int idUbicacion, String tipo, double potencia, String modelo, LocalDateTime fechaInstalacion, String estado) {
        this.id = id;
        this.idUbicacion = idUbicacion;
        this.tipo = tipo;
        this.potencia = potencia;
        this.modelo = modelo;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) { this.potencia = potencia; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public LocalDateTime getFechaInstalacion() { return fechaInstalacion; }
    public void setFechaInstalacion(LocalDateTime fechaInstalacion) { this.fechaInstalacion = fechaInstalacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "ID: " + id + " | Tipo: " + tipo + " | Potencia: " + potencia + "kW | Modelo: " + modelo + " | Estado: " + estado;
    }
}
