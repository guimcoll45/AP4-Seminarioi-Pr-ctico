package com.provinciasverdes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // ⚠️ CONFIGURACIÓN DE BASE DE DATOS - SIN DATOS PRIVADOS
    // Instrucciones para quien lo descargue: 
    // Reemplazar por tus datos locales: usuario="root", contraseña="" (o la tuya)
    private static final String URL = "jdbc:mysql://localhost:3306/provincias_verdes?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: Driver no encontrado -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: Verifique usuario, contraseña y que la BD exista -> " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println("⚠️ Error al cerrar conexión -> " + e.getMessage());
            }
        }
    }
}
