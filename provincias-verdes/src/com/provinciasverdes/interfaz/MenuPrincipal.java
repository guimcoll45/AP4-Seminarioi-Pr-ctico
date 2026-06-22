package com.provinciasverdes.interfaz;

import com.provinciasverdes.enums.EstadoEquipo;
import com.provinciasverdes.enums.EstadoUsuario;
import com.provinciasverdes.enums.TipoEquipo;
import com.provinciasverdes.enums.TipoUsuario;
import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.Perfil;
import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.modelo.Ubicacion;
import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.negocio.GeneradorReporte;
import com.provinciasverdes.negocio.GestorEnergia;
import com.provinciasverdes.negocio.Validador;
import com.provinciasverdes.persistencia.EquipoSolarDAO;
import com.provinciasverdes.persistencia.RegistroEnergiaDAO;
import com.provinciasverdes.persistencia.UbicacionDAO;
import com.provinciasverdes.persistencia.UsuarioDAO;
import java.time.LocalDateTime;
import java.util.List;

public class MenuPrincipal {
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final UbicacionDAO ubicacionDAO = new UbicacionDAO();
    private static final EquipoSolarDAO equipoDAO = new EquipoSolarDAO();
    private static final RegistroEnergiaDAO registroDAO = new RegistroEnergiaDAO();
    private static Usuario usuarioActual;

    public static void iniciar() {
        int opcion;
        do {
            System.out.println("\n===== PROVINCIAS VERDES =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
            opcion = EntradaDatos.leerEntero("Seleccione una opción");

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> registrarUsuario();
                case 0 -> System.out.println("👋 Hasta luego");
                default -> System.out.println("⚠️ Opción inválida");
            }
        } while (opcion != 0);
    }

    private static void iniciarSesion() {
        String correo = EntradaDatos.leerCadena("Correo");
        String clave = EntradaDatos.leerCadena("Contraseña");

        usuarioActual = usuarioDAO.buscarPorCredenciales(correo, clave);
        if (usuarioActual != null) {
            System.out.println("✅ Bienvenido " + usuarioActual.getNombre());
            mostrarMenuUsuario();
        } else {
            System.out.println("❌ Datos incorrectos");
        }
    }

    private static void registrarUsuario() {
        String nombre = EntradaDatos.leerCadena("Nombre completo");
        String correo = EntradaDatos.leerCadena("Correo electrónico");
        String clave = EntradaDatos.leerCadena("Contraseña");

        if (!Validador.validarCorreo(correo)) {
            System.out.println("❌ Correo inválido");
            return;
        }
        if (!Validador.validarContrasena(clave)) {
            System.out.println("❌ Contraseña muy corta");
            return;
        }

        Perfil perfil = new Perfil(0, TipoUsuario.RESIDENCIAL, "Usuario estándar");
        Usuario nuevo = new Usuario(0, nombre, correo, clave, perfil, EstadoUsuario.ACTIVO);

        if (usuarioDAO.agregar(nuevo)) {
            System.out.println("✅ Registro exitoso");
        } else {
            System.out.println("❌ No se pudo registrar");
        }
    }

    private static void mostrarMenuUsuario() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Mis ubicaciones");
            System.out.println("2. Agregar ubicación");
            System.out.println("3. Agregar equipo solar");
            System.out.println("4. Registrar medición de energía");
            System.out.println("5. Ver reporte energético");
            if (usuarioActual.getPerfil().getTipo() == TipoUsuario.ADMIN) {
                System.out.println("6. Administrar usuarios");
            }
            System.out.println("0. Cerrar sesión");
            opcion = EntradaDatos.leerEntero("Opción");

            switch (opcion) {
                case 1 -> verUbicaciones();
                case 2 -> agregarUbicacion();
                case 3 -> agregarEquipo();
                case 4 -> agregarRegistro();
                case 5 -> verReporte();
                case 6 -> {
                    if (usuarioActual.getPerfil().getTipo() == TipoUsuario.ADMIN) administrarUsuarios();
                }
                case 0 -> {
                    usuarioActual = null;
                    System.out.println("🔑 Sesión cerrada");
                }
                default -> System.out.println("⚠️ Opción inválida");
            }
        } while (opcion != 0);
    }

    private static void verUbicaciones() {
        List<Ubicacion> lista = ubicacionDAO.obtenerPorUsuario(usuarioActual.getId());
        if (lista.isEmpty()) {
            System.out.println("ℹ️ No tiene ubicaciones");
            return;
        }
        lista.forEach(System.out::println);
    }

    private static void agregarUbicacion() {
        String prov = EntradaDatos.leerCadena("Provincia");
        String dir = EntradaDatos.leerCadena("Dirección");
        double lat = EntradaDatos.leerDoble("Latitud");
        double lon = EntradaDatos.leerDoble("Longitud");

        Ubicacion ubi = new Ubicacion(0, usuarioActual.getId(), prov, dir, lat, lon);
        if (ubicacionDAO.agregar(ubi)) {
            System.out.println("✅ Ubicación guardada");
        } else {
            System.out.println("❌ Error al guardar");
        }
    }

    private static void agregarEquipo() {
        List<Ubicacion> ubicaciones = ubicacionDAO.obtenerPorUsuario(usuarioActual.getId());
        if (ubicaciones.isEmpty()) {
            System.out.println("ℹ️ Primero agregue una ubicación");
            return;
        }
        ubicaciones.forEach(System.out::println);
        int idUbi = EntradaDatos.leerEntero("ID de ubicación");

        TipoEquipo tipo = TipoEquipo.valueOf(EntradaDatos.leerCadena("Tipo (PANEL_SOLAR, INVERSOR, BATERIA, REGULADOR)").toUpperCase());
        double potencia = EntradaDatos.leerDoble("Potencia nominal (kW)");
        String modelo = EntradaDatos.leerCadena("Modelo");

        EquipoSolar equipo = new EquipoSolar(0, idUbi, tipo, potencia, modelo, LocalDateTime.now(), EstadoEquipo.ACTIVO);
        if (equipoDAO.agregar(equipo)) {
            System.out.println("✅ Equipo agregado");
        } else {
            System.out.println("❌ Error al agregar equipo");
        }
    }

    private static void agregarRegistro() {
        int idEquipo = EntradaDatos.leerEntero("ID del equipo");
        double voltaje = EntradaDatos.leerDoble("Voltaje (V)");
        double corriente = EntradaDatos.leerDoble("Corriente (A)");
        double generada = EntradaDatos.leerDoble("Energía generada (kWh)");
        double consumida = EntradaDatos.leerDoble("Energía consumida (kWh)");

        RegistroEnergia reg = new RegistroEnergia(0, idEquipo, LocalDateTime.now(), voltaje, corriente, generada, consumida);
        if (registroDAO.agregar(reg)) {
            System.out.println("✅ Reg
