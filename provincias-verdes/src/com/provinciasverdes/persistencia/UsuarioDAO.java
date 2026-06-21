package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO específico para Usuario. Contiene consultas SQL.
 */
public class UsuarioDAO extends DAOBase<Usuario> {

    @Override
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, contrasena, tipo, estado, id_perfil) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getContrasena());
            pst.setString(4, usuario.getTipo().name());
            pst.setString(5, usuario.getEstado().name());
            pst.setInt(6, usuario.getPerfil() != null ? usuario.getPerfil().getId() : 0);

            return pst.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("❌ Error: El correo ya está registrado o dato inválido.");
            return false;
        } catch (SQLException e) {
            System.err.println("❌ Error UsuarioDAO.crear: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario leer(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    TipoUsuario.valueOf(rs.getString("tipo")),
                    EstadoUsuario.valueOf(rs.getString("estado")),
                    null // Aquí cargarías PerfilDAO.leer(rs.getInt("id_perfil"))
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error UsuarioDAO.leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre=?, email=?, contrasena=?, tipo=?, estado=? WHERE id=?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getContrasena());
            pst.setString(4, usuario.getTipo().name());
            pst.setString(5, usuario.getEstado().name());
            pst.setInt(6, usuario.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error UsuarioDAO.actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error UsuarioDAO.borrar: " + e.getMessage() + " | Probablemente tiene ubicaciones asociadas.");
            return false;
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nombre";
        try (Connection conn = conexionDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    TipoUsuario.valueOf(rs.getString("tipo")),
                    EstadoUsuario.valueOf(rs.getString("estado")),
                    null
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error UsuarioDAO.listarTodos: " + e.getMessage());
        }
        return lista;
    }

    // Método específico para Login
    public Usuario buscarPorCredenciales(String email, String pass) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND contrasena = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    TipoUsuario.valueOf(rs.getString("tipo")),
                    EstadoUsuario.valueOf(rs.getString("estado")),
                    null
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error Login: " + e.getMessage());
        }
        return null;
    }
}
