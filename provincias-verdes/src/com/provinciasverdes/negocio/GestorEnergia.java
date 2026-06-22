package com.provinciasverdes.negocio;

import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.persistencia.RegistroEnergiaDAO;
import java.util.List;

public class GestorEnergia {
    private final RegistroEnergiaDAO registroDAO = new RegistroEnergiaDAO();

    public boolean agregarRegistro(RegistroEnergia registro) {
        if (!Validador.validarRegistro(registro)) {
            System.out.println("⚠️ Registro inválido");
            return false;
        }
        return registroDAO.agregar(registro);
    }

    public List<RegistroEnergia> obtenerRegistrosPorEquipo(int idEquipo) {
        return registroDAO.listarPorEquipo(idEquipo);
    }
}
