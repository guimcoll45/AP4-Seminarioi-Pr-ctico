package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.EquipoSolar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoSolarDAO extends DAOBase<EquipoSolar> {

    @Override
    public boolean crear(EquipoSolar equipo) {
        String sql = "INSERT INTO equipos_solares (modelo, potencia_nominal, tipo, fecha_instalacion, id_ubicacion, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, equipo.getModelo());
            ps.setDouble(2, equipo.getPotenciaNominal());
            ps.setString(3, equipo.getTipo());
            ps.setDate(4, Date.valueOf(equipo.getFechaInstalacion()));
            if (equipo.getIdUbicacion() != null) {
                ps.setInt(5, equipo.getIdUbicacion());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setString(6, equipo.getEstado());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        equipo.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al crear equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public EquipoSolar leer(int id) {
        String sql = "SELECT * FROM equipos_solares WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al leer equipo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(EquipoSolar equipo) {
        String sql = "UPDATE equipos_solares SET modelo=?, potencia_nominal=?, tipo=?, fecha_instalacion=?, id_ubicacion=?, estado=? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, equipo.getModelo());
            ps.setDouble(2, equipo.getPotenciaNominal());
            ps.setString(3, equipo.getTipo());
            ps.setDate(4, Date.valueOf(equipo.getFechaInstalacion()));
            if (equipo.getIdUbicacion() != null) {
                ps.setInt(5, equipo.getIdUbicacion());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setString(6, equipo.getEstado());
            ps.setInt(7, equipo.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM equipos_solares WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al borrar equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<EquipoSolar> listarTodos() {
        List<EquipoSolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos_solares ORDER BY id";
        try (Connection conn = ConexionDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    private EquipoSolar mapear(ResultSet rs) throws SQLException {
        EquipoSolar e = new EquipoSolar();
        e.setId(rs.getInt("id"));
        e.setModelo(rs.getString("modelo"));
        e.setPotenciaNominal(rs.getDouble("potencia_nominal"));
        e.setTipo(rs.getString("tipo"));
        e.setFechaInstalacion(rs.getDate("fecha_instalacion").toLocalDate());
        int idUbi = rs.getInt("id_ubicacion");
        if (!rs.wasNull()) {
            e.setIdUbicacion(idUbi);
        }
        e.setEstado(rs.getString("estado"));
        return e;
    }
}