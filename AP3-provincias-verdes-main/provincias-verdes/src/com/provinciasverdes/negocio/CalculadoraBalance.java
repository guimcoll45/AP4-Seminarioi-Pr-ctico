package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;

public class CalculadoraBalance {

    public static double calcularBalanceTotal(List<RegistroEnergia> registros) {
        double totalGenerado = 0;
        double totalConsumido = 0;

        for (RegistroEnergia r : registros) {
            totalGenerado += r.getEnergiaGenerada();
            totalConsumido += r.getEnergiaConsumida();
        }
        return totalGenerado - totalConsumido;
    }

    public static String interpretarBalance(double balance) {
        if (balance > 0) return "✅ BALANCE POSITIVO: Excedente de " + String.format("%.2f", balance) + " kWh";
        else if (balance < 0) return "⚠️ BALANCE NEGATIVO: Déficit de " + String.format("%.2f", Math.abs(balance)) + " kWh";
        else return "⚖️ BALANCE NEUTRO: Generación igual al consumo";
    }
}
