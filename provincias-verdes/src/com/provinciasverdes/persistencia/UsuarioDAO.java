package com.provinciasverdes.persistencia;

import com.provinciasverdes.enums.EstadoUsuario;
import com.provinciasverdes.enums.TipoUsuario;
import com.provinciasverdes.modelo.Perfil;
import com.provinciasverdes.modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAOBase<Usuario> {

    @Override
    public boolean agregar(Usuario usuario) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, tipo_perfil, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getPerfil().getTipo().name());
            ps.setString(5, usuario.getEstado().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "UPDATE usuarios SET nombre=?, correo=?, contrasena=?, tipo_perfil=?, estado=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getPerfil().getTipo().name());
            ps.setString(5, usuario.getEstado().name());
            ps.setInt(6, usuario.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return false;

        String sql = "DELETE FROM usuarios WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario obtenerPorId(int id) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return null;

        String sql = "SELECT * FROM usuarios WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return lista;

        String sql = "SELECT * FROM usuarios ORDER BY nombre";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public Usuario buscarPorCredenciales(String correo, String contrasena) {
        Connection con = conexionDB.obtenerConexion();
        if (con == null) return null;

        String sql = "SELECT * FROM usuarios WHERE correo=? AND contrasena=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("❌ Error al iniciar sesión: " + e.getMessage());
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        TipoUsuario tipo = TipoUsuario.valueOf(rs.getString("tipo_perfil"));
        EstadoUsuario estado = EstadoUsuario.valueOf(rs.getString("estado"));
        Perfil perfil = new Perfil(0, tipo, "Perfil de " + tipo);

        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("contrasena"),
                perfil,
                estado
        );
    }
}
