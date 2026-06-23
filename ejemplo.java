String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena, perfil) VALUES (?, ?, ?, ?, ?)";
try (Connection conn = getConexion();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, usuario.getNombre());
    ps.setString(2, usuario.getApellido());
    ps.setString(3, usuario.getCorreo());
    ps.setString(4, usuario.getContrasena());
    ps.setString(5, usuario.getPerfil());
    ps.executeUpdate();
    System.out.println("✅ Usuario agregado correctamente");

} catch (SQLException e) {
    System.err.println("❌ Error al insertar: " + e.getMessage());
}
