package com.provinciasverdes.archivos;

import java.io.*;
import java.util.List;

public class GestorArchivos {

    public static void escribirArchivo(String ruta, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write(contenido);
        } catch (IOException e) {
            System.out.println("❌ Error al escribir archivo: " + e.getMessage());
        }
    }

    public static void agregarLinea(String ruta, String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("❌ Error al agregar línea: " + e.getMessage());
        }
    }

    public static List<String> leerArchivo(String ruta) {
        return null;
    }
}
