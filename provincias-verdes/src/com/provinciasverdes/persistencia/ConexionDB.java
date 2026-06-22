package com.provinciasverdes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/provincias_verdes?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; // Poné tu contraseña si tenés
    private static Connection conexion;
    private static ConexionDB instancia;

    private ConexionDB() {}

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("✅ Conexión exitosa a la base de datos");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}
