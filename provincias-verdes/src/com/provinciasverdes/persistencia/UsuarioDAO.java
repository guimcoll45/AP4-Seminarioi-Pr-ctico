package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAOBase<Usuario> {

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY id";
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Usuario leer(int id) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al leer usuario: " + e.getMessage());
        }
        return u;
    }

    public Usuario buscarPorCorreoYClave(String correo, String clave) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE email = ? AND contrasena = ? AND estado = 'ACTIVO'";
        
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, correo.trim());
            ps.setString(2, clave.trim());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = mapearUsuario(rs);
                System.out.println("✅ Usuario encontrado: " + u.getNombre() + " | Tipo: " + u.getTipo());
            } else {
                System.out.println("❌ Datos incorrectos o usuario no existe.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return u;
    }

    @Override
    public boolean crear(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, email, contrasena, tipo, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getTipo().name());
            ps.setString(5, u.getEstado().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, email=?, contrasena=?, tipo=?, estado=? WHERE id=?";
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getTipo().name());
            ps.setString(5, u.getEstado().name());
            ps.setInt(6, u.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al borrar: " + e.getMessage());
            return false;
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setContrasena(rs.getString("contrasena"));
        u.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
        u.setEstado(EstadoUsuario.valueOf(rs.getString("estado")));
        return u;
    }
}
