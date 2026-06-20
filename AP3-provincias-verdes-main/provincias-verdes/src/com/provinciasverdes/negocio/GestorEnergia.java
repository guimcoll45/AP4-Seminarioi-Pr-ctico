package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.Date;

public class GestorEnergia {

    public static double calcularPotencia(double voltaje, double corriente) {
        return voltaje * corriente; // Watts
    }

    public static RegistroEnergia crearRegistroCalculado(int idEquipo, double voltaje, double corriente, double horasUso) {
        double potencia = calcularPotencia(voltaje, corriente);
        double energiaGenerada = (potencia * horasUso) / 1000; // kWh
        return new RegistroEnergia(0, idEquipo, new Date(), voltaje, corriente, energiaGenerada, 0.0);
    }
}
