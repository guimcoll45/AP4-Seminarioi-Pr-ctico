package com.provinciasverdes.persistencia;

import java.sql.*;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/provincias_verdes?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&useUnicode=true&characterEncoding=utf8";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static ConexionDB instancia;
    private Connection conexion;

    private ConexionDB() {}

    public static ConexionDB getInstancia() {
        if (instancia == null) instancia = new ConexionDB();
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) conexion.close();
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            return conexion;
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
