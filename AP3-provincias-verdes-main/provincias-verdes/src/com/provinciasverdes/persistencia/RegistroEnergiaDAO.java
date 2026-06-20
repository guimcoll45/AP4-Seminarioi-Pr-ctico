package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class RegistroEnergiaDAO {

    public boolean crear(RegistroEnergia registro) {
        String sql = "INSERT INTO registros_energia (id_equipo, fecha_hora, voltaje, corriente, energia_generada, energia_consumida) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, registro.getIdEquipo());
            pst.setTimestamp(2, new Timestamp(registro.getFechaHora().getTime()));
            pst.setDouble(3, registro.getVoltaje());
            pst.setDouble(4, registro.getCorriente());
            pst.setDouble(5, registro.getEnergiaGenerada());
            pst.setDouble(6, registro.getEnergiaConsumida());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al registrar medición: " + e.getMessage());
            return false;
        }
    }

    public List<RegistroEnergia> listarTodos() {
        List<RegistroEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM registros_energia ORDER BY fecha_hora DESC";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                RegistroEnergia r = new RegistroEnergia();
                r.setId(rs.getInt("id"));
                r.setIdEquipo(rs.getInt("id_equipo"));
                r.setFechaHora(rs.getTimestamp("fecha_hora"));
                r.setVoltaje(rs.getDouble("voltaje"));
                r.setCorriente(rs.getDouble("corriente"));
                r.setEnergiaGenerada(rs.getDouble("energia_generada"));
                r.setEnergiaConsumida(rs.getDouble("energia_consumida"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar registros: " + e.getMessage());
        }
        return lista;
    }
}