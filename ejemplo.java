try {
    // Operación de conexión o consulta
    Connection conn = ConexionDB.getInstancia().getConexion();
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.executeQuery();

} catch (ClassNotFoundException e) {
    System.err.println("❌ Controlador JDBC no encontrado en la biblioteca");
} catch (SQLException e) {
    System.err.println("❌ Error en la base de datos: " + e.getMessage());
} catch (Exception e) {
    System.err.println("❌ Error inesperado: " + e.getMessage());
}
