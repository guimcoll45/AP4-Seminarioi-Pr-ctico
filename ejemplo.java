try {
    PreparedStatement ps = conexion.prepareStatement(sql);
    ps.setInt(1, idEquipo);
    ps.executeUpdate();
} catch (SQLException e) {
    System.out.println("❌ Error en la operación: " + e.getMessage());
}
