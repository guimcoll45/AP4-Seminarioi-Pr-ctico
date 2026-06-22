package com.provinciasverdes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/provincias_verdes?"
            + "useSSL=false"
            + "&serverTimezone=America/Argentina/Buenos_Aires"
            + "&allowPublicKeyRetrieval=true"
            + "&useUnicode=true"
            + "&characterEncoding=utf8mb4";

    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static ConexionDB instancia;
    private static Connection conexion;

    private ConexionDB() {}

    public static ConexionDB getInstancia() {
        if (instancia == null) instancia = new ConexionDB();
        return instancia;
    }

    public Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            }
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            conexion = null;
        }
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
        }
    }
}
