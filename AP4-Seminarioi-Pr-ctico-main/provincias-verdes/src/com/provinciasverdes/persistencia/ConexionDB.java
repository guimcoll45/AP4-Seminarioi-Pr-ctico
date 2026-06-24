package com.provinciasverdes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Configuración para XAMPP
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/provincias_verdes?"
            + "useSSL=false"
            + "&serverTimezone=America/Argentina/Buenos_Aires"
            + "&allowPublicKeyRetrieval=true"
            + "&connectTimeout=3000"
            + "&socketTimeout=3000"
            + "&useUnicode=true"
            + "&characterEncoding=utf8";

    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static ConexionDB instancia;
    private static Connection conexion;

    private ConexionDB() {}

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public static Connection getConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
            System.out.println("🔍 Intentando cargar el controlador MySQL...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Controlador cargado correctamente");

            System.out.println("🔍 Conectando a la base...");
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("✅ CONEXIÓN EXITOSA\n");
            return conexion;

        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: No se encontró el controlador MySQL");
            System.err.println("Detalle: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ ERROR DE CONEXIÓN A LA BASE");
            System.err.println("Detalle: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ ERROR INESPERADO");
            System.err.println("Detalle: " + e.getMessage());
        }
        conexion = null;
        return null;
    }
}