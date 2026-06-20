package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Ubicacion;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class UbicacionDAO {

    public boolean crear(Ubicacion ubicacion) {
        String sql = "INSERT INTO ubicaciones (id_usuario, provincia, direccion, latitud, longitud) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, ubicacion.getIdUsuario());
            pst.setString(2, ubicacion.getProvincia());
            pst.setString(3, ubicacion.getDireccion());
            pst.setDouble(4, ubicacion.getLatitud());
            pst.setDouble(5, ubicacion.getLongitud());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al crear ubicación: " + e.getMessage());
            return false;
        }
    }

    public List<Ubicacion> listarTodas() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicaciones";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Ubicacion u = new Ubicacion();
                u.setId(rs.getInt("id"));
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setProvincia(rs.getString("provincia"));
                u.setDireccion(rs.getString("direccion"));
                u.setLatitud(rs.getDouble("latitud"));
                u.setLongitud(rs.getDouble("longitud"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar ubicaciones: " + e.getMessage());
        }
        return lista;
    }

    public Ubicacion buscarPorId(int id) {
        Ubicacion u = null;
        String sql = "SELECT * FROM ubicaciones WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                u = new Ubicacion();
                u.setId(rs.getInt("id"));
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setProvincia(rs.getString("provincia"));
                u.setDireccion(rs.getString("direccion"));
                u.setLatitud(rs.getDouble("latitud"));
                u.setLongitud(rs.getDouble("longitud"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar ubicación: " + e.getMessage());
        }
        return u;
    }
}