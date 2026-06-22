package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.enums.TipoEquipo;
import com.provinciasverdes.modelo.enums.EstadoEquipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoSolarDAO extends DAOBase<EquipoSolar> {

    @Override
    public List<EquipoSolar> listarTodos() {
        List<EquipoSolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo_solar";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar equipos: " + e.getMessage());
        }
        return lista;
    }

    public List<EquipoSolar> listarPorUbicacion(int idUbicacion) {
        List<EquipoSolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo_solar WHERE id_ubicacion = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUbicacion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar por ubicación: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public EquipoSolar leer(int id) {
        EquipoSolar eq = null;
        String sql = "SELECT * FROM equipo_solar WHERE id = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                eq = mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error leer equipo: " + e.getMessage());
        }
        return eq;
    }

    @Override
    public boolean crear(EquipoSolar eq) {
        String sql = "INSERT INTO equipo_solar (tipo, potencia_nominal, capacidad, fecha_instalacion, estado, id_ubicacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eq.getTipo().name());
            ps.setDouble(2, eq.getPotenciaNominal());
            ps.setDouble(3, eq.getCapacidad());
            ps.setTimestamp(4, Timestamp.valueOf(eq.getFechaInstalacion()));
            ps.setString(5, eq.getEstado().name());
            ps.setInt(6, eq.getIdUbicacion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error crear equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(EquipoSolar eq) {
        String sql = "UPDATE equipo_solar SET tipo=?, potencia_nominal=?, capacidad=?, estado=? WHERE id=?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eq.getTipo().name());
            ps.setDouble(2, eq.getPotenciaNominal());
            ps.setDouble(3, eq.getCapacidad());
            ps.setString(4, eq.getEstado().name());
            ps.setInt(5, eq.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM equipo_solar WHERE id = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error borrar equipo: " + e.getMessage());
            return false;
        }
    }

    private EquipoSolar mapear(ResultSet rs) throws SQLException {
        EquipoSolar eq = new EquipoSolar();
        eq.setId(rs.getInt("id"));
        eq.setTipo(TipoEquipo.valueOf(rs.getString("tipo")));
        eq.setPotenciaNominal(rs.getDouble("potencia_nominal"));
        eq.setCapacidad(rs.getDouble("capacidad"));
        eq.setFechaInstalacion(rs.getTimestamp("fecha_instalacion").toLocalDateTime());
        eq.setEstado(EstadoEquipo.valueOf(rs.getString("estado")));
        eq.setIdUbicacion(rs.getInt("id_ubicacion"));
        return eq;
    }
}
