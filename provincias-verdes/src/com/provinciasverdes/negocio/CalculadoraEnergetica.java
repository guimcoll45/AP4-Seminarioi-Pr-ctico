package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;

public class CalculadoraEnergetica {

    public static double calcularTotalGenerado(List<RegistroEnergia> registros) {
        return registros.stream().mapToDouble(RegistroEnergia::getEnergiaGenerada).sum();
    }

    public static double calcularTotalConsumido(List<RegistroEnergia> registros) {
        return registros.stream().mapToDouble(RegistroEnergia::getEnergiaConsumida).sum();
    }

    public static double calcularSaldoEnergia(List<RegistroEnergia> registros) {
        return calcularTotalGenerado(registros) - calcularTotalConsumido(registros);
    }

    public static double calcularEficiencia(double generada, double potenciaMaxima) {
        if (potenciaMaxima <= 0) return 0;
        return Math.min((generada / potenciaMaxima) * 100, 100);
    }
}
