package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.archivos.GestorArchivos; // ✅ Agregado para poder guardar el reporte
import java.util.List;

/**
 * Clase encargada de generar informes y reportes de rendimiento energético.
 * Pertenece a la capa de Negocio.
 */
public class GeneradorReporte {

    /**
     * Genera un reporte de eficiencia energética en formato texto.
     * @param registros Lista de mediciones a analizar
     * @param periodo Descripción del periodo (ej: "Junio 2026", "Semana 1")
     * @return Texto formateado con el informe
     */
    public static String generarReporteEficiencia(List<RegistroEnergia> registros, String periodo) {
        double totalGen = 0;
        double totalCon = 0;
        int cantidad = registros.size();

        // Recorrer todos los registros para sumar valores
        for (RegistroEnergia r : registros) {
            totalGen += r.getEnergiaGenerada();
            totalCon += r.getEnergiaConsumida();
        }

        // Cálculo de eficiencia: (Excedente / Generada) * 100
        // Si generó 0, eficiencia es 0
        double eficiencia = (totalGen > 0) ? ((totalGen - totalCon) / totalGen) * 100 : 0;
        
        // Determinar estado del sistema
        String estado = (eficiencia >= 75) ? "✅ ÓPTIMO" : 
                        (eficiencia >= 50) ? "⚠️ ACEPTABLE" : 
                        "🔴 BAJO RENDIMIENTO";

        // Construir el texto del reporte
        return "==================================================\n" +
               "📊 REPORTE DE EFICIENCIA ENERGÉTICA - PERIODO: " + periodo + "\n" +
               "==================================================\n" +
               "✅ Total Energía Generada:  " + String.format("%.2f", totalGen) + " kWh\n" +
               "🔌 Total Energía Consumida: " + String.format("%.2f", totalCon) + " kWh\n" +
               "⚖️ Balance Energético:      " + String.format("%.2f", (totalGen - totalCon)) + " kWh\n" +
               "📈 Eficiencia del Sistema:  " + String.format("%.1f", eficiencia) + " %\n" +
               "🔍 Estado del Sistema:      " + estado + "\n" +
               "📋 Cantidad de mediciones:  " + cantidad + "\n" +
               "==================================================";
    }

    // ✅ MÉTODO NUEVO: Para guardar el reporte automáticamente
    public static void generarYGuardarReporte(List<RegistroEnergia> registros, String periodo, String rutaArchivo) {
        String contenido = generarReporteEficiencia(registros, periodo);
        GestorArchivos.guardarArchivo(rutaArchivo, contenido);
        System.out.println("📂 Reporte guardado exitosamente en: " + rutaArchivo);
    }
}
