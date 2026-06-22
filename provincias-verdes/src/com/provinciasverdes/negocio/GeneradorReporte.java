package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.archivos.GestorArchivos;
import java.util.List;

public class GeneradorReporte {

    public static String generarReporteEficiencia(List<RegistroEnergia> registros, String periodo) {
        if (registros.isEmpty()) return "❌ No hay datos para generar el reporte.";

        double totalGen = 0, totalCon = 0, balance = 0;
        for (RegistroEnergia r : registros) {
            totalGen += r.getEnergiaGenerada();
            totalCon += r.getEnergiaConsumida();
            balance += r.getBalance();
        }

        double eficiencia = CalculadoraEnergetica.calcularEficienciaPorcentual(registros);
        String estado = CalculadoraEnergetica.clasificarEficiencia(eficiencia);

        return """
==================================================
📊 REPORTE DE EFICIENCIA ENERGÉTICA - %s
==================================================
✅ Total Energía Generada:  %.2f kWh
🔌 Total Energía Consumida: %.2f kWh
⚖️ Balance Energético:      %.2f kWh
📈 Eficiencia del Sistema:  %.2f %%
🔍 Estado del Sistema:      %s
📋 Cantidad de mediciones:  %d
==================================================
""".formatted(periodo, totalGen, totalCon, balance, eficiencia, estado, registros.size());
    }

    public static void generarYGuardarReporte(List<RegistroEnergia> registros, String nombreArchivo, String ruta) {
        String contenido = generarReporteEficiencia(registros, nombreArchivo);
        GestorArchivos.escribirArchivo(ruta, contenido);
        System.out.println("💾 Reporte guardado en: " + ruta);
    }
}
