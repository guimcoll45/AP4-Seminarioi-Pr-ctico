String sql = "DELETE FROM usuarios WHERE id = ?";
try (Connection conn = getConexion();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setInt(1, id);
    ps.executeUpdate();

} catch (SQLException e) {
    System.err.println("❌ Error al eliminar: " + e.getMessage());
}
