package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Ubicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO extends DAOBase<Ubicacion> {

    @Override
    public boolean agregar(Ubicacion ubicacion) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "INSERT INTO ubicaciones (id_usuario, provincia, direccion, latitud, longitud) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ubicacion.getIdUsuario());
            ps.setString(2, ubicacion.getProvincia());
            ps.setString(3, ubicacion.getDireccion());
            ps.setDouble(4, ubicacion.getLatitud());
            ps.setDouble(5, ubicacion.getLongitud());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Ubicacion ubicacion) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "DELETE FROM ubicaciones WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Ubicacion obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Ubicacion> obtenerTodos() {
        return null;
    }

    public List<Ubicacion> obtenerPorUsuario(int idUsuario) {
        List<Ubicacion> lista = new ArrayList<>();
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return lista;

        String sql = "SELECT * FROM ubicaciones WHERE id_usuario=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("❌ Error al listar ubicaciones: " + e.getMessage());
        }
        return lista;
    }

    private Ubicacion mapear(ResultSet rs) throws SQLException {
        return new Ubicacion(
                rs.getInt("id"),
                rs.getInt("id_usuario"),
                rs.getString("provincia"),
                rs.getString("direccion"),
                rs.getDouble("latitud"),
                rs.getDouble("longitud")
        );
    }
}
