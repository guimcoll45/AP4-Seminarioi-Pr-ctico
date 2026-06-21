package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.enums.TipoEquipo;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EquipoSolarDAO extends DAOBase<EquipoSolar> {

    @Override
    public boolean crear(EquipoSolar equipo) {
        String sql = "INSERT INTO equipo_solar (tipo, potencia_nominal, capacidad, fecha_instalacion, id_ubicacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, equipo.getTipo().name());
            pst.setDouble(2, equipo.getPotenciaNominal());
            pst.setDouble(3, equipo.getCapacidad());
            pst.setTimestamp(4, Timestamp.valueOf(equipo.getFechaInstalacion()));
            pst.setInt(5, equipo.getIdUbicacion());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error EquipoSolar.crear: Ubicación no existe o dato inválido.");
            return false;
        }
    }

    @Override
    public EquipoSolar leer(int id) {
        String sql = "SELECT * FROM equipo_solar WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return new EquipoSolar(
                    rs.getInt("id"),
                    TipoEquipo.valueOf(rs.getString("tipo")),
                    rs.getDouble("potencia_nominal"),
                    rs.getDouble("capacidad"),
                    rs.getTimestamp("fecha_instalacion").toLocalDateTime(),
                    rs.getInt("id_ubicacion")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error EquipoSolar.leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(EquipoSolar equipo) {
        String sql = "UPDATE equipo_solar SET tipo=?, potencia_nominal=?, capacidad=? WHERE id=?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, equipo.getTipo().name());
            pst.setDouble(2, equipo.getPotenciaNominal());
            pst.setDouble(3, equipo.getCapacidad());
            pst.setInt(4, equipo.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error EquipoSolar.actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM equipo_solar WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: Equipo tiene registros asociados.");
            return false;
        }
    }

    @Override
    public List<EquipoSolar> listarTodos() {
        List<EquipoSolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo_solar";
        try (Connection conn = conexionDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                lista.add(new EquipoSolar(
                    rs.getInt("id"),
                    TipoEquipo.valueOf(rs.getString("tipo")),
                    rs.getDouble("potencia_nominal"),
                    rs.getDouble("capacidad"),
                    rs.getTimestamp("fecha_instalacion").toLocalDateTime(),
                    rs.getInt("id_ubicacion")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error EquipoSolar.listarTodos: " + e.getMessage());
        }
        return lista;
    }
}
