package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;

public class CalculadoraEnergetica {

    public static double calcularBalanceTotal(List<RegistroEnergia> registros) {
        double total = 0;
        for (RegistroEnergia r : registros) {
            total += r.getBalance();
        }
        return total;
    }

    public static double calcularEficienciaPorcentual(List<RegistroEnergia> registros) {
        double totalGen = 0;
        double totalCon = 0;
        for (RegistroEnergia r : registros) {
            totalGen += r.getEnergiaGenerada();
            totalCon += r.getEnergiaConsumida();
        }
        if (totalGen == 0) return 0;
        return ((totalGen - totalCon) / totalGen) * 100;
    }

    public static String clasificarEficiencia(double porcentaje) {
        if (porcentaje >= 75) return "✅ ÓPTIMO";
        if (porcentaje >= 50) return "⚠️ ACEPTABLE";
        return "🔴 BAJO RENDIMIENTO";
    }
}
