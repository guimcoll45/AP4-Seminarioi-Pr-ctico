package com.provinciasverdes.modelo;

import java.time.LocalDateTime;

public class RegistroEnergia extends EntidadBase {

    private int idEquipo;
    private LocalDateTime fecha_hora;
    private double energia_generada;
    private double energia_consumida;
    private double voltaje;
    private double corriente;
    private double balance;

    // Constructor vacío
    public RegistroEnergia() {}

    // Constructor con parámetros
    public RegistroEnergia(int id, int idEquipo, LocalDateTime fecha_hora, double voltaje, double corriente, double energia_generada, double energia_consumida) {
        super(id);
        this.idEquipo = idEquipo;
        this.fecha_hora = fecha_hora;
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.energia_generada = energia_generada;
        this.energia_consumida = energia_consumida;
        this.balance = energia_generada - energia_consumida;
    }

    // Getters y Setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public double getEnergia_generada() {
        return energia_generada;
    }

    public void setEnergia_generada(double energia_generada) {
        this.energia_generada = energia_generada;
        this.balance = this.energia_generada - this.energia_consumida;
    }

    public double getEnergia_consumida() {
        return energia_consumida;
    }

    public void setEnergia_consumida(double energia_consumida) {
        this.energia_consumida = energia_consumida;
        this.balance = this.energia_generada - this.energia_consumida;
    }

    public double getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(double voltaje) {
        this.voltaje = voltaje;
    }

    public double getCorriente() {
        return corriente;
    }

    public void setCorriente(double corriente) {
        this.corriente = corriente;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Métodos camelCase usados en otras clases
    public double getEnergiaGenerada() {
        return energia_generada;
    }

    public double getEnergiaConsumida() {
        return energia_consumida;
    }

    // ✅ Implementación obligatoria de EntidadBase
    @Override
    public void mostrarDatos() {
        System.out.printf(
            "Registro ID: %d | Equipo: %d | Fecha: %s | Generada: %.2f kWh | Consumida: %.2f kWh | Balance: %.2f kWh%n",
            getId(),
            idEquipo,
            fecha_hora.toString(),
            energia_generada,
            energia_consumida,
            balance
        );
    }

    @Override
    public String paraArchivo() {
        return String.format(
            "%d;%d;%s;%.2f;%.2f;%.2f;%.2f;%.2f",
            getId(),
            idEquipo,
            fecha_hora.toString(),
            voltaje,
            corriente,
            energia_generada,
            energia_consumida,
            balance
        );
    }

    @Override
    public String toString() {
        return "RegistroEnergia{" +
                "id=" + getId() +
                ", idEquipo=" + idEquipo +
                ", fecha_hora=" + fecha_hora +
                ", energia_generada=" + energia_generada +
                ", energia_consumida=" + energia_consumida +
                ", voltaje=" + voltaje +
                ", corriente=" + corriente +
                ", balance=" + balance +
                '}';
    }
}