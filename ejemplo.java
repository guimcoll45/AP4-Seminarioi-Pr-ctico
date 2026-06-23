String sql = "SELECT * FROM usuarios WHERE id = ?";
try (Connection conn = getConexion();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setCorreo(rs.getString("correo"));
    }

} catch (SQLException e) {
    System.err.println("❌ Error al consultar: " + e.getMessage());
}
