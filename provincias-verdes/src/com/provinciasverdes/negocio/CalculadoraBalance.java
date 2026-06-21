package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;

/**
 * Contiene la lógica matemática y de cálculo del sistema.
 * Cumple RF5: Cálculo de balance y estadísticas.
 */
public class CalculadoraEnergetica {

    // Calcular balance total de una lista de registros
    public static double calcularBalanceTotal(List<RegistroEnergia> registros) {
        double totalGen = 0;
        double totalCon = 0;
        for(RegistroEnergia r : registros) {
            totalGen += r.getEnergiaGenerada();
            totalCon += r.getEnergiaConsumida();
        }
        return totalGen - totalCon;
    }

    // Calcular eficiencia promedio
    public static double calcularEficienciaPromedio(List<RegistroEnergia> registros) {
        if(registros.isEmpty()) return 0;
        double suma = 0;
        for(RegistroEnergia r : registros) {
            if(r.getEnergiaGenerada() > 0) {
                suma += (r.getEnergiaGenerada() / (r.getEnergiaConsumida() + 0.001)) * 100;
            }
        }
        return suma / registros.size();
    }

    // Estimar ahorro económico (Ej: $150 por kWh)
    public static double calcularAhorroEstimado(double excedenteKwh) {
        final double VALOR_KWH = 150.0;
        return excedenteKwh * VALOR_KWH;
    }
}
