package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Ubicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO extends DAOBase {

    // ✅ Conexión adaptada EXACTAMENTE a tu ConexionDB
    private Connection getConexion() throws SQLException {
        return ConexionDB.getInstancia().getConexion();
    }

    // ✅ Método obligatorio de DAOBase: leer por ID
    @Override
    public Object leer(int id) {
        String sql = "SELECT id, direccion, ciudad, provincia, codigo_postal, id_usuario FROM ubicaciones WHERE id = ?";
        Ubicacion u = null;

        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                u = new Ubicacion();
                u.setId(rs.getInt("id"));
                u.setDireccion(rs.getString("direccion"));
                u.setCiudad(rs.getString("ciudad"));
                u.setProvincia(rs.getString("provincia"));
                u.setCodigoPostal(rs.getString("codigo_postal"));
                u.setIdUsuario(rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al leer ubicación: " + e.getMessage());
        }
        return u;
    }

    // ✅ Método obligatorio de DAOBase: crear genérico
    @Override
    public boolean crear(Object objeto) {
        if (!(objeto instanceof Ubicacion)) return false;
        Ubicacion u = (Ubicacion) objeto;

        String sql = "INSERT INTO ubicaciones (direccion, ciudad, provincia, codigo_postal, id_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, u.getDireccion());
            pstmt.setString(2, u.getCiudad());
            pstmt.setString(3, u.getProvincia());
            pstmt.setString(4, u.getCodigoPostal());
            pstmt.setObject(5, u.getIdUsuario());

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) u.setId(rs.getInt(1));
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al agregar ubicación: " + e.getMessage());
        }
        return false;
    }

    // ✅ Método obligatorio de DAOBase: actualizar genérico
    @Override
    public boolean actualizar(Object objeto) {
        if (!(objeto instanceof Ubicacion)) return false;
        Ubicacion u = (Ubicacion) objeto;

        String sql = "UPDATE ubicaciones SET direccion = ?, ciudad = ?, provincia = ?, codigo_postal = ?, id_usuario = ? WHERE id = ?";

        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, u.getDireccion());
            pstmt.setString(2, u.getCiudad());
            pstmt.setString(3, u.getProvincia());
            pstmt.setString(4, u.getCodigoPostal());
            pstmt.setObject(5, u.getIdUsuario());
            pstmt.setInt(6, u.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al modificar ubicación: " + e.getMessage());
        }
        return false;
    }

    // ✅ Método obligatorio de DAOBase: borrar
    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM ubicaciones WHERE id = ?";

        try (Connection conn = getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar ubicación: " + e.getMessage());
        }
        return false;
    }

    // Métodos auxiliares para usar en el menú
    public Ubicacion buscarPorId(int id) {
        return (Ubicacion) leer(id);
    }

    public List<Ubicacion> listarTodos() {
        List<Ubicacion> lista = new ArrayList<>();
        String sql = "SELECT id, direccion, ciudad, provincia, codigo_postal, id_usuario FROM ubicaciones";

        try (Connection conn = getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ubicacion u = new Ubicacion();
                u.setId(rs.getInt("id"));
                u.setDireccion(rs.getString("direccion"));
                u.setCiudad(rs.getString("ciudad"));
                u.setProvincia(rs.getString("provincia"));
                u.setCodigoPostal(rs.getString("codigo_postal"));
                u.setIdUsuario(rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null);
                lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar ubicaciones: " + e.getMessage());
        }
        return lista;
    }
}