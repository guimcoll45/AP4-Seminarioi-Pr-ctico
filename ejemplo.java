public Connection getConexion() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/provincias_verdes?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires",
            "root",
            ""
        );
    } catch (Exception e) {
        System.err.println("Error al conectar: " + e.getMessage());
        return null;
    }
}
