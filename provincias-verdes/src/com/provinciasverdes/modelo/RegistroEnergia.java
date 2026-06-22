package com.provinciasverdes.modelo;

import java.time.LocalDateTime;

public class RegistroEnergia {
    private int id;
    private int idEquipo;
    private LocalDateTime fechaHora;
    private double voltaje;
    private double corriente;
    private double energiaGenerada;
    private double energiaConsumida;

    public RegistroEnergia() {}

    public RegistroEnergia(int id, int idEquipo, LocalDateTime fechaHora, double voltaje, double corriente, double energiaGenerada, double energiaConsumida) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.fechaHora = fechaHora;
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.energiaGenerada = energiaGenerada;
        this.energiaConsumida = energiaConsumida;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public double getVoltaje() { return voltaje; }
    public void setVoltaje(double voltaje) { this.voltaje = voltaje; }

    public double getCorriente() { return corriente; }
    public void setCorriente(double corriente) { this.corriente = corriente; }

    public double getEnergiaGenerada() { return energiaGenerada; }
    public void setEnergiaGenerada(double energiaGenerada) { this.energiaGenerada = energiaGenerada; }

    public double getEnergiaConsumida() { return energiaConsumida; }
    public void setEnergiaConsumida(double energiaConsumida) { this.energiaConsumida = energiaConsumida; }

    @Override
    public String toString() {
        return "Fecha: " + fechaHora + " | Generada: " + energiaGenerada + "kWh | Consumida: " + energiaConsumida + "kWh";
    }
}
