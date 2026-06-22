package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.Reporte;
import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.modelo.Usuario;
import java.time.LocalDateTime;
import java.util.List;

public class GeneradorReporte {

    public static Reporte generarReporte(Usuario usuario, List<RegistroEnergia> registros) {
        double totalGenerado = CalculadoraEnergetica.calcularTotalGenerado(registros);
        double totalConsumido = CalculadoraEnergetica.calcularTotalConsumido(registros);
        double saldo = CalculadoraEnergetica.calcularSaldoEnergia(registros);

        return new Reporte(
                0,
                LocalDateTime.now(),
                usuario,
                registros,
                totalGenerado,
                totalConsumido,
                saldo
        );
    }

    public static String exportarReporteTexto(Reporte reporte) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE ENERGÉTICO ===\n");
        sb.append("Fecha: ").append(reporte.getFechaGeneracion()).append("\n");
        sb.append("Usuario: ").append(reporte.getUsuario().getNombre()).append("\n");
        sb.append("Total generado: ").append(String.format("%.2f", reporte.getTotalGenerado())).append(" kWh\n");
        sb.append("Total consumido: ").append(String.format("%.2f", reporte.getTotalConsumido())).append(" kWh\n");
        sb.append("Saldo: ").append(String.format("%.2f", reporte.getSaldoEnergia())).append(" kWh\n");
        return sb.toString();
    }
}
