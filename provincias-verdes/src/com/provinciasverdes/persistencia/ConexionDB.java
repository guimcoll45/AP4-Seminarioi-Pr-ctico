package com.provinciasverdes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Patrón de Diseño SINGLETON: Asegura una única conexión activa.
 * Centraliza acceso a MySQL.
 */
public class ConexionDB {
    // ÚNICA instancia de la clase
    private static ConexionDB instancia;
    private Connection conexion;

    // Datos de conexión (Configuración)
    private final String URL = "jdbc:mysql://localhost:3306/provincias_verdes_db?useSSL=false&serverTimezone=UTC";
    private final String USUARIO = "root";
    private final String CLAVE = ""; // Tu contraseña de MySQL

    // CONSTRUCTOR PRIVADO: No se puede instanciar desde fuera
    private ConexionDB() {
        try {
            // Cargar Driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer conexión
            this.conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("✅ Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Error de Conexión: " + e.getMessage());
        }
    }

    // Método estático para obtener la única instancia
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    // Obtener el objeto Connection para las consultas
    public Connection getConexion() {
        return conexion;
    }

    // Cerrar conexión
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔌 Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error al cerrar conexión: " + e.getMessage());
        }
    }
}
