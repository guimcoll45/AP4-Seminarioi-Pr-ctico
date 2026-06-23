String sql = "UPDATE usuarios SET correo = ?, perfil = ? WHERE id = ?";
try (Connection conn = getConexion();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, nuevoCorreo);
    ps.setString(2, nuevoPerfil);
    ps.setInt(3, id);
    ps.executeUpdate();

} catch (SQLException e) {
    System.err.println("❌ Error al actualizar: " + e.getMessage());
}
