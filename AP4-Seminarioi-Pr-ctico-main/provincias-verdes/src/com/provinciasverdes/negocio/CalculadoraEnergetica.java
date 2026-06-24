package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;

public class CalculadoraEnergetica {

    public static double calcularBalanceTotal(List<RegistroEnergia> registros) {
        double total = 0;
        for (RegistroEnergia r : registros) {
            total += r.getEnergiaGenerada() - r.getEnergiaConsumida();
        }
        return total;
    }

    public static double calcularTotalGenerado(List<RegistroEnergia> registros) {
        double total = 0;
        for (RegistroEnergia r : registros) {
            total += r.getEnergiaGenerada();
        }
        return total;
    }

    public static double calcularTotalConsumido(List<RegistroEnergia> registros) {
        double total = 0;
        for (RegistroEnergia r : registros) {
            total += r.getEnergiaConsumida();
        }
        return total;
    }

    public static double calcularEficienciaPorcentual(List<RegistroEnergia> registros) {
        double generado = calcularTotalGenerado(registros);
        double consumido = calcularTotalConsumido(registros);
        if (generado == 0) return 0;
        return Math.min(100, Math.max(0, (generado / (generado + consumido)) * 100));
    }

    public static String clasificarEficiencia(double porcentaje) {
        if (porcentaje >= 80) return "ALTA";
        if (porcentaje >= 50) return "MEDIA";
        return "BAJA";
    }
}