package com.provinciasverdes.archivos;

import com.provinciasverdes.modelo.Reporte;
import java.io.*;

public class GestorArchivos {

    public static boolean guardarReporteComoTexto(Reporte reporte, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write(reporte.toString());
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar archivo: " + e.getMessage());
            return false;
        }
    }
}
