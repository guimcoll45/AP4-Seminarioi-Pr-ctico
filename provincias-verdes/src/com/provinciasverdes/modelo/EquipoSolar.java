package com.provinciasverdes.modelo;

public class EquipoSolar extends EntidadBase {
    private int idUbicacion;
    private String tipo; // PANEL, INVERSOR, BATERIA
    private double potencia; // kW
    private String modelo;

    public EquipoSolar() { super(); }

    public EquipoSolar(int id, int idUbicacion, String tipo, double potencia, String modelo) {
        super(id);
        this.idUbicacion = idUbicacion;
        this.tipo = tipo;
        this.potencia = potencia;
        this.modelo = modelo;
    }

    // Getters y Setters
    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) {
        if (potencia > 0) this.potencia = potencia;
        else throw new IllegalArgumentException("La potencia debe ser mayor a 0");
    }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== EQUIPO SOLAR ===");
        System.out.println("ID: " + id);
        System.out.println("Tipo: " + tipo);
        System.out.println("Modelo: " + modelo);
        System.out.println("Potencia: " + potencia + " kW");
    }
}
