package com.provinciasverdes.archivos;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GestorArchivos {

    public static boolean crearCarpeta(String ruta) {
        try {
            Files.createDirectories(Paths.get(ruta));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean escribirArchivo(String ruta, String contenido) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(ruta))) {
            bw.write(contenido);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean escribirArchivoTexto(String ruta, String contenido) {
        return escribirArchivo(ruta, contenido);
    }

    public static boolean escribirArchivoCSV(String ruta, List<RegistroEnergia> registros) {
        if (registros == null || registros.isEmpty()) {
            return escribirArchivo(ruta, "Sin registros\n");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID;ID_EQUIPO;FECHA;VOLTAJE;CORRIENTE;GENERADA;CONSUMIDA;BALANCE\n");
        for (RegistroEnergia r : registros) {
            sb.append(r.paraArchivo()).append("\n");
        }
        return escribirArchivo(ruta, sb.toString());
    }

    public static boolean exportarRegistros(List<RegistroEnergia> lista, String ruta) {
        return escribirArchivoCSV(ruta, lista);
    }
}