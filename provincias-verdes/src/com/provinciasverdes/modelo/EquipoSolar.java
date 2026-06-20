package com.provinciasverdes.modelo;

import com.provinciasverdes.modelo.enums.TipoEquipo;
import com.provinciasverdes.modelo.interfaces.IMedible;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase EquipoSolar: Paneles, inversores, baterías.
 * Implementa Interfaz IMedible.
 */
public class EquipoSolar extends EntidadBase implements IMedible {
    private TipoEquipo tipo;
    private double potenciaNominal;
    private double capacidad;
    private LocalDateTime fechaInstalacion;
    private int idUbicacion; // FK
    private double potenciaTotalCalculada; // Atributo calculado

    // ✅ ARREGLO de rangos para clasificar eficiencia
    private final double[] RANGOS_EFICIENCIA = {0.0, 0.6, 0.75, 0.9, 1.0};

    // Relación 1:N
    private List<RegistroEnergia> registros;

    public EquipoSolar() {
        super();
        this.registros = new ArrayList<>();
    }

    public EquipoSolar(int id, TipoEquipo tipo, double potenciaNominal, double capacidad, LocalDateTime fechaInstalacion, int idUbicacion) {
        super(id);
        this.tipo = tipo;
        setPotenciaNominal(potenciaNominal);
        this.capacidad = capacidad;
        this.fechaInstalacion = fechaInstalacion;
        this.idUbicacion = idUbicacion;
        calcularCapacidadTotal();
        this.registros = new ArrayList<>();
    }

    // Getters y Setters
    public TipoEquipo getTipo() { return tipo; }
    public void setTipo(TipoEquipo tipo) { this.tipo = tipo; }
    public double getPotenciaNominal() { return potenciaNominal; }
    public void setPotenciaNominal(double potenciaNominal) {
        if(potenciaNominal > 0) this.potenciaNominal = potenciaNominal;
        else throw new IllegalArgumentException("Potencia debe ser mayor a 0");
    }
    public double getCapacidad() { return capacidad; }
    public void setCapacidad(double capacidad) { this.capacidad = capacidad; }
    public LocalDateTime getFechaInstalacion() { return fechaInstalacion; }
    public void setFechaInstalacion(LocalDateTime fechaInstalacion) { this.fechaInstalacion = fechaInstalacion; }
    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }
    public double getPotenciaTotalCalculada() { return potenciaTotalCalculada; }
    public List<RegistroEnergia> getRegistros() { return registros; }

    // Lógica de Negocio
    public void calcularCapacidadTotal() {
        this.potenciaTotalCalculada = this.potenciaNominal * this.capacidad;
    }

    public String clasificarEficiencia(double valor) {
        if(valor >= RANGOS_EFICIENCIA[3]) return "ALTA";
        if(valor >= RANGOS_EFICIENCIA[2]) return "MEDIA";
        if(valor >= RANGOS_EFICIENCIA[1]) return "BAJA";
        return "MUY BAJA";
    }

    // Implementación de Interfaz
    @Override
    public void medir() {
        System.out.println("Midiendo rendimiento del equipo: " + this.tipo);
    }
    @Override
    public double obtenerPotenciaActual() {
        return this.potenciaNominal * 0.85; // Simulación de rendimiento real
    }
    @Override
    public LocalDateTime obtenerUltimaLectura() {
        return !registros.isEmpty() ? registros.get(registros.size()-1).getFechaHora() : LocalDateTime.now();
    }

    @Override
    public void mostrarDatos() {
        System.out.println("🔌 Equipo ID: " + id + " | Tipo: " + tipo + " | Potencia: " + potenciaNominal + "kW | Total: " + potenciaTotalCalculada + "kW");
    }

    @Override
    public String paraArchivo() {
        return id + ";" + tipo + ";" + potenciaNominal + ";" + capacidad + ";" + fechaInstalacion + ";" + idUbicacion;
    }
}
