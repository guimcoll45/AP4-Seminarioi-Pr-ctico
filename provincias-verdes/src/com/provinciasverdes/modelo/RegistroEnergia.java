package com.provinciasverdes.modelo;

import java.time.LocalDateTime;

public class RegistroEnergia extends EntidadBase {
    private int idEquipo;
    private LocalDateTime fechaHora;
    private double energiaGenerada;
    private double energiaConsumida;
    private double voltaje;
    private double corriente;
    private double balance;

    public RegistroEnergia() {}

    // ✅ Lógica de negocio: cálculo automático
    public void calcularBalance() {
        this.balance = this.energiaGenerada - this.energiaConsumida;
    }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public double getEnergiaGenerada() { return energiaGenerada; }
    public void setEnergiaGenerada(double energiaGenerada) { this.energiaGenerada = energiaGenerada; }

    public double getEnergiaConsumida() { return energiaConsumida; }
    public void setEnergiaConsumida(double energiaConsumida) { this.energiaConsumida = energiaConsumida; }

    public double getVoltaje() { return voltaje; }
    public void setVoltaje(double voltaje) { this.voltaje = voltaje; }

    public double getCorriente() { return corriente; }
    public void setCorriente(double corriente) { this.corriente = corriente; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public void mostrarDatos() {
        System.out.println("Fecha: " + fechaHora + " | Gen: " + energiaGenerada + " | Con: " + energiaConsumida + " | Bal: " + balance);
    }

    @Override
    public String paraArchivo() {
        return id + ";" + idEquipo + ";" + fechaHora + ";" + energiaGenerada + ";" + energiaConsumida + ";" + voltaje + ";" + corriente + ";" + balance;
    }
}
