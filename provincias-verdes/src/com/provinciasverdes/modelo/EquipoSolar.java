package com.provinciasverdes.modelo;

import com.provinciasverdes.enums.EstadoEquipo;
import com.provinciasverdes.enums.TipoEquipo;
import com.provinciasverdes.interfaces.IMedible;
import java.time.LocalDateTime;

public class EquipoSolar extends EntidadBase implements IMedible {
    private int idUbicacion;
    private TipoEquipo tipo;
    private double potenciaNominal;
    private String modelo;
    private LocalDateTime fechaInstalacion;
    private EstadoEquipo estado;

    public EquipoSolar() {}

    public EquipoSolar(int id, int idUbicacion, TipoEquipo tipo, double potenciaNominal, String modelo, LocalDateTime fechaInstalacion, EstadoEquipo estado) {
        super(id);
        this.idUbicacion = idUbicacion;
        this.tipo = tipo;
        this.potenciaNominal = potenciaNominal;
        this.modelo = modelo;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = estado;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public TipoEquipo getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipo tipo) {
        this.tipo = tipo;
    }

    public double getPotenciaNominal() {
        return potenciaNominal;
    }

    public void setPotenciaNominal(double potenciaNominal) {
        this.potenciaNominal = potenciaNominal;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDateTime getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(LocalDateTime fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }

    public void setEstado(EstadoEquipo estado) {
        this.estado = estado;
    }

    @Override
    public double obtenerMedicion() {
        return potenciaNominal;
    }

    @Override
    public String obtenerUnidadMedida() {
        return "kW";
    }

    @Override
    public String toString() {
        return "EquipoSolar{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", potencia=" + potenciaNominal + " kW" +
                ", modelo='" + modelo + '\'' +
                ", estado=" + estado +
                '}';
    }
}
