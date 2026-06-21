package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Ubicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO extends DAOBase<Ubicacion> {

    @Override
    public boolean crear(Ubicacion ubicacion) {
        String sql = "INSERT INTO ubicacion (provincia, direccion, latitud, longitud, id_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, ubicacion.getProvincia());
            pst.setString(2, ubicacion.getDireccion());
            pst.setDouble(3, ubicacion.getLatitud());
            pst.setDouble(4, ubicacion.getLongitud());
            pst.setInt(5, ubicacion.getIdUsuario());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error UbicacionDAO.crear: Verifique que el Usuario exista. " + e.getMessage());
            return false;
        }
    }

    @Override
    public Ubicacion leer(int id) {
        String sql = "SELECT * FROM ubicacion WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return new Ubicacion(
                    rs.getInt("id"),
                    rs.getString("provincia"),
                    rs.getString("direccion"),
                    rs.getDouble("latitud"),
                    rs.getDouble("longitud"),
                    rs.getInt("id_usuario")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error UbicacionDAO.leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Ubicacion ubicacion) {
        String sql = "UPDATE ubicacion SET provincia=?, direccion=?, latitud=?, longitud=? WHERE id=?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, ubicacion.getProvincia());
            pst.setString(2, ubicacion.getDireccion());
            pst.setDouble(3, ubicacion.getLatitud());
            pst.setDouble(4, ubicacion.getLongitud());
            pst.setInt(5, ubicacion.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error UbicacionDAO.actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM ubicacion WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: No se puede borrar, tiene equipos asociados.");
            return false;
        }
    }

    @Override
    public List<Ubicacion> listarTodos() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion";
        try (Connection conn = conexionDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                lista.add(new Ubicacion(
                    rs.getInt("id"),
                    rs.getString("provincia"),
                    rs.getString("direccion"),
                    rs.getDouble("latitud"),
                    rs.getDouble("longitud"),
                    rs.getInt("id_usuario")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error UbicacionDAO.listarTodos: " + e.getMessage());
        }
        return lista;
    }

    // Listar ubicaciones de un usuario específico
    public List<Ubicacion> listarPorUsuario(int idUsuario) {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion WHERE id_usuario = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Ubicacion(rs.getInt("id"), rs.getString("provincia"), rs.getString("direccion"), rs.getDouble("latitud"), rs.getDouble("longitud"), rs.getInt("id_usuario")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error listarPorUsuario: " + e.getMessage());
        }
        return lista;
    }
}
