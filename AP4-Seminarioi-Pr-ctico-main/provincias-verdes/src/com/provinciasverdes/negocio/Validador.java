package com.provinciasverdes.negocio;

import java.time.LocalDateTime;

/**
 * Clase de utilidad para validaciones de negocio.
 * Cumple RNF4: Integridad y validación de datos.
 */
public class Validador {

    // Validar que un texto no esté vacío
    public static boolean textoNoVacio(String texto) {
        return texto != null && !texto.isBlank();
    }

    // Validar rango de coordenadas
    public static boolean coordenadaValida(double valor, boolean esLatitud) {
        if(esLatitud) return valor >= -90 && valor <= 90;
        else return valor >= -180 && valor <= 180;
    }

    // Validar que un número sea positivo
    public static boolean numeroPositivo(double numero) {
        return numero > 0;
    }

    // Validar que fecha no sea futura
    public static boolean fechaNoFutura(LocalDateTime fecha) {
        return fecha != null && fecha.isBefore(LocalDateTime.now().plusSeconds(1));
    }

    // Validar formato de email simple
    public static boolean emailValido(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
