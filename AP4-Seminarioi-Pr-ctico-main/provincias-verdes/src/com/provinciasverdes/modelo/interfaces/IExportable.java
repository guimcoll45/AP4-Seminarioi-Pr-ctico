package com.provinciasverdes.modelo.interfaces;

/**
 * Interfaz: Define comportamiento para exportar datos a archivos.
 */
public interface IExportable {
    String generarEncabezado();
    String generarCuerpo();
    void exportarCSV(String ruta);
    void exportarTXT(String ruta);
}
