package com.provinciasverdes.modelo.enums;

public enum TipoEquipo {
    PANEL,
    INVERSOR,
    BATERIA,
    REGULADOR;

    // ✅ Validación inteligente: acepta "Panel Solar", "batería", etc.
    public static TipoEquipo fromString(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El tipo no puede estar vacío");
        }
        
        String limpio = texto.trim().toUpperCase().replace(" ", "").replace("Í", "I");

        return switch (limpio) {
            case "PANEL", "PANELSOLAR" -> PANEL;
            case "INVERSOR" -> INVERSOR;
            case "BATERIA", "BATERIASOLAR", "BATERÍA" -> BATERIA;
            case "REGULADOR" -> REGULADOR;
            default -> throw new IllegalArgumentException("Tipo de equipo no válido: '" + texto + "'. Usa: PANEL, INVERSOR, BATERIA o REGULADOR");
        };
    }
}
