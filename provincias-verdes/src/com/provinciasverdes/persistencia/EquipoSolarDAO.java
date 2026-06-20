package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.EquipoSolar;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class EquipoSolarDAO {

    public boolean crear(EquipoSolar equipo) {
        String sql = "INSERT INTO equipos_solares (id_ubicacion, tipo, potencia, modelo) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, equipo.getIdUbicacion());
            pst.setString(2, equipo.getTipo());
            pst.setDouble(3, equipo.getPotencia());
            pst.setString(4, equipo.getModelo());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al crear equipo: " + e.getMessage());
            return false;
        }
    }

    public List<EquipoSolar> listarTodos() {
        List<EquipoSolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos_solares";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                EquipoSolar e = new EquipoSolar();
                e.setId(rs.getInt("id"));
                e.setIdUbicacion(rs.getInt("id_ubicacion"));
                e.setTipo(rs.getString("tipo"));
                e.setPotencia(rs.getDouble("potencia"));
                e.setModelo(rs.getString("modelo"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    public List<EquipoSolar> listarPorUbicacion(int idUbicacion) {
        List<EquipoSolar> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos_solares WHERE id_ubicacion = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idUbicacion);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                EquipoSolar e = new EquipoSolar();
                e.setId(rs.getInt("id"));
                e.setIdUbicacion(rs.getInt("id_ubicacion"));
                e.setTipo(rs.getString("tipo"));
                e.setPotencia(rs.getDouble("potencia"));
                e.setModelo(rs.getString("modelo"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al filtrar equipos: " + e.getMessage());
        }
        return lista;
    }
}