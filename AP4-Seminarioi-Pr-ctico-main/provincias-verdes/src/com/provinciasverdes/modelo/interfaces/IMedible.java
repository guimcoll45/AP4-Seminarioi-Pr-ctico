package com.provinciasverdes.modelo.interfaces;

public interface IMedible {
    double obtenerRendimiento();
    boolean necesitaMantenimiento();
    // ✅ Agregamos el método que falta
    double obtenerUltimaLectura();
}