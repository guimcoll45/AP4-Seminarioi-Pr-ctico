package com.provinciasverdes.archivos;

import com.provinciasverdes.modelo.interfaces.IExportable;
import java.io.*;
import java.util.List;

/**
 * Manejo completo de archivos de texto y CSV.
 * Cumple requisito TP4: Emplear archivos para guardar y recuperar información.
 */
public class GestorArchivos {

    // Guardar contenido simple
    public static boolean guardarArchivo(String ruta, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write(contenido);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar archivo: " + e.getMessage());
            return false;
        }
    }

    // Exportar lista de objetos que implementan IExportable
    public static void exportarLista(List<? extends IExportable> lista, String ruta) {
        StringBuilder sb = new StringBuilder();

        if(!lista.isEmpty()) {
            sb.append(lista.get(0).generarEncabezado()).append("\n");
            for(IExportable item : lista) {
                sb.append(item.generarCuerpo()).append("\n");
            }
        } else {
            sb.append("Sin datos para exportar");
        }

        guardarArchivo(ruta, sb.toString());
        System.out.println("📁 Reporte exportado correctamente: " + ruta);
    }

    // Leer archivo de texto
    public static String leerArchivo(String ruta) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("❌ Error al leer archivo: " + e.getMessage());
        }
        return contenido.toString();
    }
}
