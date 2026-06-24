package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import com.provinciasverdes.modelo.enums.TipoUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAOBase<Usuario> {

    @Override
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (correo, contrasena, nombre, apellido, tipo_usuario, estado, perfil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, usuario.getCorreo());
                ps.setString(2, usuario.getContrasena());
                ps.setString(3, usuario.getNombre());
                ps.setString(4, usuario.getApellido());
                ps.setString(5, usuario.getTipoUsuario().name());
                ps.setString(6, usuario.getEstado().name());
                ps.setString(7, usuario.getPerfil());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error crear: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Usuario leer(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn == null) return lista;
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET correo=?, contrasena=?, nombre=?, apellido=?, tipo_usuario=?, estado=?, perfil=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, usuario.getCorreo());
                ps.setString(2, usuario.getContrasena());
                ps.setString(3, usuario.getNombre());
                ps.setString(4, usuario.getApellido());
                ps.setString(5, usuario.getTipoUsuario().name());
                ps.setString(6, usuario.getEstado().name());
                ps.setString(7, usuario.getPerfil());
                ps.setInt(8, usuario.getId());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error actualizar: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error borrar: " + e.getMessage());
        }
        return false;
    }

    public Usuario buscarPorCorreoYClave(String correo, String clave) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ? AND estado = 'ACTIVO'";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, correo.trim());
                ps.setString(2, clave.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error login: " + e.getMessage());
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setCorreo(rs.getString("correo"));
        u.setContrasena(rs.getString("contrasena"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
        u.setEstado(EstadoUsuario.valueOf(rs.getString("estado")));
        u.setPerfil(rs.getString("perfil"));
        return u;
    }
}