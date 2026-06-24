package com.provinciasverdes.interfaz;

import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.persistencia.ConexionDB;
import com.provinciasverdes.persistencia.UsuarioDAO;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== PROVINCIAS VERDES - GESTIÓN ENERGÉTICA ===");
        
        ConexionDB.getConexion();

        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL DE ENTRADA =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse como nuevo usuario");
            System.out.println("0. Salir del sistema");
            System.out.print("Elija una opción: ");

            opcion = EntradaDatos.leerEntero("");

            switch (opcion) {
                case 1:
                    if (iniciarSesion()) {
                        MenuPrincipal.mostrarMenu();
                    }
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 0:
                    System.out.println("👋 Hasta luego. Programa finalizado.");
                    break;
                default:
                    System.out.println("⚠️ Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static boolean iniciarSesion() {
        System.out.println("\n--- Iniciar Sesión ---");
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String clave = scanner.nextLine().trim();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorCorreoYClave(correo, clave);

        if (usuario != null) {
            MenuPrincipal.usuarioLogueado = usuario;
            System.out.println("\n✅ Bienvenido: " + usuario.getNombre() + " | Perfil: " + usuario.getPerfil());
            return true;
        } else {
            System.out.println("❌ Correo o contraseña incorrectos, o usuario inactivo.");
            return false;
        }
    }

    private static void registrarUsuario() {
        System.out.println("\n--- Registro de Nuevo Usuario ---");

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Ingrese su apellido: ");
        String apellido = scanner.nextLine().trim();

        System.out.print("Ingrese su correo electrónico: ");
        String correo = scanner.nextLine().trim();

        System.out.print("Ingrese una contraseña: ");
        String clave = scanner.nextLine().trim();

        // ✅ Usamos los valores que SÍ existen en tu enum
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setCorreo(correo);
        nuevo.setContrasena(clave);
        nuevo.setTipoUsuario(TipoUsuario.RESIDENCIAL); // Tipo por defecto para usuarios nuevos
        nuevo.setEstado(EstadoUsuario.ACTIVO);
        nuevo.setPerfil("Usuario Residencial");

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.crear(nuevo)) {
            System.out.println("✅ Registro exitoso! Ya puede iniciar sesión con sus datos.");
        } else {
            System.out.println("❌ No se pudo registrar. Verifique que el correo no esté ya en uso.");
        }
    }
}