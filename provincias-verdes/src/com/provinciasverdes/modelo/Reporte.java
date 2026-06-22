package com.provinciasverdes.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Reporte extends EntidadBase {
    private LocalDateTime fechaGeneracion;
    private Usuario usuario;
    private List<RegistroEnergia> registros;
    private double totalGenerado;
    private double totalConsumido;
    private double saldoEnergia;

    public Reporte() {}

    public Reporte(int id, LocalDateTime fechaGeneracion, Usuario usuario, List<RegistroEnergia> registros, double totalGenerado, double totalConsumido, double saldoEnergia) {
        super(id);
        this.fechaGeneracion = fechaGeneracion;
        this.usuario = usuario;
        this.registros = registros;
        this.totalGenerado = totalGenerado;
        this.totalConsumido = totalConsumido;
        this.saldoEnergia = saldoEnergia;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<RegistroEnergia> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroEnergia> registros) {
        this.registros = registros;
    }

    public double getTotalGenerado() {
        return totalGenerado;
    }

    public void setTotalGenerado(double totalGenerado) {
        this.totalGenerado = totalGenerado;
    }

    public double getTotalConsumido() {
        return totalConsumido;
    }

    public void setTotalConsumido(double totalConsumido) {
        this.totalConsumido = totalConsumido;
    }

    public double getSaldoEnergia() {
        return saldoEnergia;
    }

    public void setSaldoEnergia(double saldoEnergia) {
        this.saldoEnergia = saldoEnergia;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "fecha=" + fechaGeneracion +
                ", totalGenerado=" + totalGenerado + " kWh" +
                ", totalConsumido=" + totalConsumido + " kWh" +
                ", saldo=" + saldoEnergia + " kWh" +
                '}';
    }
}
