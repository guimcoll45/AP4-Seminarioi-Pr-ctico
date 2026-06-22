package com.provinciasverdes.persistencia;

import com.provinciasverdes.enums.EstadoEquipo;
import com.provinciasverdes.enums.TipoEquipo;
import com.provinciasverdes.modelo.EquipoSolar;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EquipoSolarDAO extends DAOBase<EquipoSolar> {

    @Override
    public boolean agregar(EquipoSolar equipo) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "INSERT INTO equipos_solares (id_ubicacion, tipo, potencia, modelo, fecha_instalacion, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, equipo.getIdUbicacion());
            ps.setString(2, equipo.getTipo().name());
            ps.setDouble(3, equipo.getPotenciaNominal());
            ps.setString(4, equipo.getModelo());
            ps.setTimestamp(5, Timestamp.valueOf(equipo.getFechaInstalacion()));
            ps.setString(6, equipo.getEstado().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(EquipoSolar equipo) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public EquipoSolar obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<EquipoSolar> obtenerTodos() {
        return null;
    }

    public List<EquipoSolar> obtenerPorUbicacion(int idUbicacion) {
        List<EquipoSolar> lista = new ArrayList<>();
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return lista;

        String sql = "SELECT * FROM equipos_solares WHERE id_ubicacion=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUbicacion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("❌ Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    private EquipoSolar mapear(ResultSet rs) throws SQLException {
        return new EquipoSolar(
                rs.getInt("id"),
                rs.getInt("id_ubicacion"),
                TipoEquipo.valueOf(rs.getString("tipo")),
                rs.getDouble("potencia"),
                rs.getString("modelo"),
                rs.getTimestamp("fecha_instalacion").toLocalDateTime(),
                EstadoEquipo.valueOf(rs.getString("estado"))
        );
    }
}
