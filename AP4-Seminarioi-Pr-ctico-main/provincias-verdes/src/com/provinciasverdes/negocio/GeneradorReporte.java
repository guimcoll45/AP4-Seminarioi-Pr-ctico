package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.archivos.GestorArchivos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorReporte {

    // Genera el texto completo del reporte
    public static String generarReporteEficiencia(List<RegistroEnergia> registros, String periodo) {
        if (registros.isEmpty()) {
            return "❌ No hay mediciones registradas para generar el reporte.";
        }

        double totalGen = CalculadoraEnergetica.calcularTotalGenerado(registros);
        double totalCon = CalculadoraEnergetica.calcularTotalConsumido(registros);
        double balance = CalculadoraEnergetica.calcularBalanceTotal(registros);
        double eficiencia = CalculadoraEnergetica.calcularEficienciaPorcentual(registros);
        String clasificacion = CalculadoraEnergetica.clasificarEficiencia(eficiencia);

        return String.format("""
==================================================
📊 REPORTE DE EFICIENCIA ENERGÉTICA
Período: %s
Fecha de generación: %s
==================================================
✅ Energía total generada:   %.2f kWh
🔌 Energía total consumida:  %.2f kWh
⚖️ Balance neto:             %.2f kWh
📈 Eficiencia del sistema:   %.2f %%
🏷️ Clasificación:            %s
📋 Cantidad de mediciones:   %d
==================================================
""",
                periodo,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                totalGen,
                totalCon,
                balance,
                eficiencia,
                clasificacion,
                registros.size()
        );
    }

    // Genera y guarda el reporte en archivo .txt
    public static void generarYGuardarReporte(List<RegistroEnergia> registros, String nombreArchivo) {
        String contenido = generarReporteEficiencia(registros, "Todo el período");
        GestorArchivos.escribirArchivoTexto(nombreArchivo + ".txt", contenido);
        GestorArchivos.escribirArchivoCSV(nombreArchivo + ".csv", registros);
    }
}