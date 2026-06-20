package com.provinciasverdes.modelo;

import com.provinciasverdes.modelo.interfaces.IExportable;
import java.time.LocalDateTime;

/**
 * Clase RegistroEnergia: Almacena mediciones (RF4).
 * Calcula Balance Energético (RF5).
 */
public class RegistroEnergia extends EntidadBase implements IExportable {
    private LocalDateTime fechaHora;
    private double energiaGenerada;
    private double energiaConsumida;
    private double voltaje;
    private double corriente;
    private int idEquipo; // FK
    private double balance; // Calculado: Generada - Consumida

    public RegistroEnergia() { super(); }

    public RegistroEnergia(int id, LocalDateTime fechaHora, double energiaGenerada, double energiaConsumida, double voltaje, double corriente, int idEquipo) {
        super(id);
        this.fechaHora = fechaHora;
        this.energiaGenerada = energiaGenerada;
        this.energiaConsumida = energiaConsumida;
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.idEquipo = idEquipo;
        calcularBalance(); // Cálculo automático al crear
    }

    // Getters
    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getEnergiaGenerada() { return energiaGenerada; }
    public double getEnergiaConsumida() { return energiaConsumida; }
    public double getVoltaje() { return voltaje; }
    public double getCorriente() { return corriente; }
    public int getIdEquipo() { return idEquipo; }
    public double getBalance() { return balance; }

    // Lógica de Negocio
    public void calcularBalance() {
        this.balance = this.energiaGenerada - this.energiaConsumida;
    }

    public String obtenerEstadoBalance() {
        if(balance > 0) return "EXCEDENTE (+)";
        if(balance < 0) return "DÉFICIT (-)";
        return "NEUTRO (=)";
    }

    // Implementación Interfaz IExportable
    @Override
    public String generarEncabezado() {
        return "ID;FECHA;GENERADA_kWh;CONSUMIDA_kWh;BALANCE;ESTADO";
    }
    @Override
    public String generarCuerpo() {
        return id + ";" + fechaHora + ";" + energiaGenerada + ";" + energiaConsumida + ";" + balance + ";" + obtenerEstadoBalance();
    }
    @Override
    public void exportarCSV(String ruta) { /* Llamada a GestorArchivos */ }
    @Override
    public void exportarTXT(String ruta) { /* Llamada a GestorArchivos */ }

    @Override
    public void mostrarDatos() {
        System.out.println("📊 Registro [" + fechaHora + "]: Gen: " + energiaGenerada + " | Con: " + energiaConsumida + " | Balance: " + balance + " " + obtenerEstadoBalance());
    }

    @Override
    public String paraArchivo() {
        return generarCuerpo();
    }
}
