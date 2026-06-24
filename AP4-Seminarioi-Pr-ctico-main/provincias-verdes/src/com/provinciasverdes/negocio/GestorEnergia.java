package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorEnergia {

    public static RegistroEnergia crearRegistro(int id, int idEquipo, String fecha, double voltaje, double corriente, double generada, double consumida) {
        LocalDateTime fechaConvertida = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        return new RegistroEnergia(id, idEquipo, fechaConvertida, voltaje, corriente, generada, consumida);
    }
}