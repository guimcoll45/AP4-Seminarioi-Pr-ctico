package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.Ubicacion;
import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.RegistroEnergia;

public class Validador {

    public static boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean validarContrasena(String contrasena) {
        return contrasena != null && contrasena.length() >= 4;
    }

    public static boolean validarUbicacion(Ubicacion ubicacion) {
        return ubicacion != null &&
                !ubicacion.getProvincia().isBlank() &&
                !ubicacion.getDireccion().isBlank() &&
                ubicacion.getLatitud() >= -90 && ubicacion.getLatitud() <= 90 &&
                ubicacion.getLongitud() >= -180 && ubicacion.getLongitud() <= 180;
    }

    public static boolean validarEquipo(EquipoSolar equipo) {
        return equipo != null &&
                equipo.getTipo() != null &&
                equipo.getPotenciaNominal() > 0 &&
                !equipo.getModelo().isBlank();
    }

    public static boolean validarRegistro(RegistroEnergia registro) {
        return registro != null &&
                registro.getFechaHora() != null &&
                registro.getEnergiaGenerada() >= 0 &&
                registro.getEnergiaConsumida() >= 0;
    }
}
