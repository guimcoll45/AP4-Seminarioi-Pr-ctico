package com.provinciasverdes.modelo;

import java.util.Date;

public class RegistroEnergia extends EntidadBase {
    private int idEquipo;
    private Date fechaHora;
    private double voltaje;
    private double corriente;
    private double energiaGenerada;
    private double energiaConsumida;

    public RegistroEnergia() { super(); }

    public RegistroEnergia(int id, int idEquipo, Date fechaHora, double voltaje, double corriente, double energiaGenerada, double energiaConsumida) {
        super(id);
        this.idEquipo = idEquipo;
        this.fechaHora = fechaHora;
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.energiaGenerada = energiaGenerada;
        this.energiaConsumida = energiaConsumida;
    }

    // Getters y Setters
    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }
    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }
    public double getVoltaje() { return voltaje; }
    public void setVoltaje(double voltaje) { this.voltaje = voltaje; }
    public double getCorriente() { return corriente; }
    public void setCorriente(double corriente) { this.corriente = corriente; }
    public double getEnergiaGenerada() { return energiaGenerada; }
    public void setEnergiaGenerada(double energiaGenerada) { this.energiaGenerada = energiaGenerada; }
    public double getEnergiaConsumida() { return energiaConsumida; }
    public void setEnergiaConsumida(double energiaConsumida) { this.energiaConsumida = energiaConsumida; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== REGISTRO ENERGÉTICO ===");
        System.out.println("Fecha: " + fechaHora);
        System.out.println("Generado: " + energiaGenerada + " kWh | Consumido: " + energiaConsumida + " kWh");
    }
}
