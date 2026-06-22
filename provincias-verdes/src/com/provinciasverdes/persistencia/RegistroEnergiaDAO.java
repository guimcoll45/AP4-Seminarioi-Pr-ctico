package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroEnergiaDAO extends DAOBase<RegistroEnergia> {

    @Override
    public boolean agregar(RegistroEnergia registro) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "INSERT INTO registros_energia (id_equipo, fecha_hora, voltaje, corriente, energia_generada, energia_consumida) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, registro.getIdEquipo());
            ps.setTimestamp(2, Timestamp.valueOf(registro.getFechaHora()));
            ps.setDouble(3, registro.getVoltaje());
            ps.setDouble(4, registro.getCorriente());
            ps.setDouble(5, registro.getEnergiaGenerada());
            ps.setDouble(6, registro.getEnergiaConsumida());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar registro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(RegistroEnergia registro) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public RegistroEnergia obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<RegistroEnergia> obtenerTodos() {
        return null;
    }

    public List<RegistroEnergia> listarPorEquipo(int idEquipo) {
        List<RegistroEnergia> lista = new ArrayList<>();
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return lista;

        String sql = "SELECT * FROM registros_energia WHERE id_equipo=? ORDER BY fecha_hora DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEquipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("❌ Error al listar registros: " + e.getMessage());
        }
        return lista;
    }

    private RegistroEnergia mapear(ResultSet rs) throws SQLException {
        return new RegistroEnergia(
                rs.getInt("id"),
                rs.getInt("id_equipo"),
                rs.getTimestamp("fecha_hora").toLocalDateTime(),
                rs.getDouble("voltaje"),
                rs.getDouble("corriente"),
                rs.getDouble("energia_generada"),
                rs.getDouble("energia_consumida")
        );
    }
}
