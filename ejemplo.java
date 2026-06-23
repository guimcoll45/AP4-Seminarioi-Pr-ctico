// Captura de valores no numéricos
try {
    System.out.print("Ingrese voltaje: ");
    double voltaje = scanner.nextDouble();
} catch (InputMismatchException e) {
    System.out.println("❌ Valor inválido: debe ingresar un número.");
    scanner.next();
}

// Captura de errores en la base de datos
try {
    PreparedStatement ps = conexion.prepareStatement(sql);
    ps.executeUpdate();
} catch (SQLException e) {
    System.out.println("❌ Error: Verifique que los datos asociados existan.");
}
