package com.provinciasverdes.modelo;

import com.provinciasverdes.interfaces.IExportable;
import java.time.LocalDateTime;

public class RegistroEnergia extends EntidadBase implements IExportable {
    private int idEquipo;
    private LocalDateTime fechaHora;
    private double voltaje;
    private double corriente;
    private double energiaGenerada;
    private double energiaConsumida;

    public RegistroEnergia() {}

    public RegistroEnergia(int id, int idEquipo, LocalDateTime fechaHora, double voltaje, double corriente, double energiaGenerada, double energiaConsumida) {
        super(id);
        this.idEquipo = idEquipo;
        this.fechaHora = fechaHora;
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.energiaGenerada = energiaGenerada;
        this.energiaConsumida = energiaConsumida;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
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

    public double getEnergiaGenerada() {
        return energiaGenerada;
    }

    public void setEnergiaGenerada(double energiaGenerada) {
        this.energiaGenerada = energiaGenerada;
    }

    public double getEnergiaConsumida() {
        return energiaConsumida;
    }

    public void setEnergiaConsumida(double energiaConsumida) {
        this.energiaConsumida = energiaConsumida;
    }

    @Override
    public String aTextoCSV() {
        return id + "," + idEquipo + "," + fechaHora + "," + voltaje + "," + corriente + "," + energiaGenerada + "," + energiaConsumida;
    }

    @Override
    public String aTextoJSON() {
        return "{" +
                "\"id\":" + id + "," +
                "\"idEquipo\":" + idEquipo + "," +
                "\"fechaHora\":\"" + fechaHora + "\"," +
                "\"voltaje\":" + voltaje + "," +
                "\"corriente\":" + corriente + "," +
                "\"energiaGenerada\":" + energiaGenerada + "," +
                "\"energiaConsumida\":" + energiaConsumida +
                "}";
    }

    @Override
    public String toString() {
        return "RegistroEnergia{" +
                "fecha=" + fechaHora +
                ", generada=" + energiaGenerada + " kWh" +
                ", consumida=" + energiaConsumida + " kWh" +
                '}';
    }
}
