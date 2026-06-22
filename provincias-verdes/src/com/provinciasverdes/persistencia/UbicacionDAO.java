package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Ubicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO extends DAOBase<Ubicacion> {

    @Override
    public List<Ubicacion> listarTodos() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar ubicaciones: " + e.getMessage());
        }
        return lista;
    }

    public List<Ubicacion> listarPorUsuario(int idUsuario) {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion WHERE id_usuario = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar por usuario: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Ubicacion leer(int id) {
        Ubicacion u = null;
        String sql = "SELECT * FROM ubicacion WHERE id = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error leer ubicación: " + e.getMessage());
        }
        return u;
    }

    @Override
    public boolean crear(Ubicacion u) {
        String sql = "INSERT INTO ubicacion (provincia, direccion, latitud, longitud, id_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getProvincia());
            ps.setString(2, u.getDireccion());
            ps.setDouble(3, u.getLatitud());
            ps.setDouble(4, u.getLongitud());
            ps.setInt(5, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error crear ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Ubicacion u) {
        String sql = "UPDATE ubicacion SET provincia=?, direccion=?, latitud=?, longitud=? WHERE id=?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getProvincia());
            ps.setString(2, u.getDireccion());
            ps.setDouble(3, u.getLatitud());
            ps.setDouble(4, u.getLongitud());
            ps.setInt(5, u.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM ubicacion WHERE id = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error borrar ubicación: " + e.getMessage());
            return false;
        }
    }

    private Ubicacion mapear(ResultSet rs) throws SQLException {
        Ubicacion u = new Ubicacion();
        u.setId(rs.getInt("id"));
        u.setProvincia(rs.getString("provincia"));
        u.setDireccion(rs.getString("direccion"));
        u.setLatitud(rs.getDouble("latitud"));
        u.setLongitud(rs.getDouble("longitud"));
        u.setIdUsuario(rs.getInt("id_usuario"));
        return u;
    }
}
