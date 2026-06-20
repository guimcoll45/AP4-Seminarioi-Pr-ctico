package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Usuario;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class UsuarioDAO {

    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, perfil) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getCorreo());
            // ✅ CORREGIDO: sin tilde
            pst.setString(3, usuario.getContrasena());
            pst.setString(4, usuario.getPerfil());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario buscarPorCorreoYClave(String correo, String clave) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, correo);
            pst.setString(2, clave);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                // ✅ CORREGIDO: sin tilde
                u.setContrasena(rs.getString("contrasena"));
                u.setPerfil(rs.getString("perfil"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                // ✅ CORREGIDO: sin tilde
                u.setContrasena(rs.getString("contrasena"));
                u.setPerfil(rs.getString("perfil"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
}