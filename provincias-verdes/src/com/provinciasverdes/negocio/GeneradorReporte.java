package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;

public class GeneradorReporte {

    public static String generarReporteEficiencia(List<RegistroEnergia> registros, String periodo) {
        double totalGen = 0;
        double totalCon = 0;
        int cantidad = registros.size();

        for (RegistroEnergia r : registros) {
            totalGen += r.getEnergiaGenerada();
            totalCon += r.getEnergiaConsumida();
        }

        double eficiencia = (totalGen > 0) ? ((totalGen - totalCon) / totalGen) * 100 : 0;

        return "📊 REPORTE DE EFICIENCIA - PERIODO: " + periodo + "\n" +
               "----------------------------------------\n" +
               "Total Energía Generada: " + String.format("%.2f", totalGen) + " kWh\n" +
               "Total Energía Consumida: " + String.format("%.2f", totalCon) + " kWh\n" +
               "Eficiencia del Sistema: " + String.format("%.1f", eficiencia) + " %\n" +
               "Cantidad de mediciones: " + cantidad;
    }
}
