try {
    conexion = ConexionDB.getConexion();
    // Operaciones SQL
} catch (SQLIntegrityConstraintViolationException e) {
    System.out.println("Error: Violación de integridad. Dato duplicado o referencia inválida.");
} catch (SQLException e) {
    System.out.println("Error en la base de datos: " + e.getMessage());
} finally {
    ConexionDB.cerrarConexion(conexion); // Siempre se cierra
}
