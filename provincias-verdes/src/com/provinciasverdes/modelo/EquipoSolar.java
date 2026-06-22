package com.provinciasverdes.modelo;

import com.provinciasverdes.modelo.enums.TipoEquipo;
import com.provinciasverdes.modelo.enums.EstadoEquipo;
import java.time.LocalDateTime;

public class EquipoSolar extends EntidadBase {
    private TipoEquipo tipo;
    private double potenciaNominal;
    private double capacidad;
    private LocalDateTime fechaInstalacion;
    private EstadoEquipo estado;
    private int idUbicacion;

    public EquipoSolar() {}

    public TipoEquipo getTipo() { return tipo; }
    public void setTipo(TipoEquipo tipo) { this.tipo = tipo; }

    public double getPotenciaNominal() { return potenciaNominal; }
    public void setPotenciaNominal(double potenciaNominal) { this.potenciaNominal = potenciaNominal; }

    public double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }

    public LocalDateTime getFechaInstalacion() { return fechaInstalacion; }
    public void setFechaInstalacion(LocalDateTime fechaInstalacion) { this.fechaInstalacion = fechaInstalacion; }

    public EstadoEquipo getEstado() { return estado; }
    public void setEstado(EstadoEquipo estado) { this.estado = estado; }

    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }

    @Override
    public void mostrarDatos() {
        System.out.println("ID: " + id + " | Tipo: " + tipo + " | Potencia: " + potenciaNominal + " | Estado: " + estado);
    }

    @Override
    public String paraArchivo() {
        return id + ";" + tipo + ";" + potenciaNominal + ";" + capacidad + ";" + fechaInstalacion + ";" + estado + ";" + idUbicacion;
    }
}
