package com.provinciasverdes.modelo.interfaces;

import java.time.LocalDateTime;

/**
 * Interfaz: Define comportamiento para todo aquello que se puede medir.
 * Cumple requisito TP4: Uso de Interfaces.
 */
public interface IMedible {
    void medir();
    double obtenerPotenciaActual();
    LocalDateTime obtenerUltimaLectura();
}
