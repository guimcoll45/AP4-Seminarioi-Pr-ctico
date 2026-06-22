package com.provinciasverdes.interfaz;

import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.modelo.Ubicacion;
import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import com.provinciasverdes.modelo.enums.TipoEquipo;
import com.provinciasverdes.persistencia.UsuarioDAO;
import com.provinciasverdes.persistencia.UbicacionDAO;
import com.provinciasverdes.persistencia.EquipoSolarDAO;
import com.provinciasverdes.persistencia.RegistroEnergiaDAO;
import com.provinciasverdes.negocio.CalculadoraEnergetica;
import com.provinciasverdes.negocio.GeneradorReporte;
import java.time.LocalDateTime;
import java.util.List;

public class MenuPrincipal {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static UbicacionDAO ubiDAO = new UbicacionDAO();
    private static EquipoSolarDAO eqDAO = new EquipoSolarDAO();
    private static RegistroEnergiaDAO regDAO = new RegistroEnergiaDAO();
    private static Usuario usuarioLogueado = null;

    public static void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== 🌱 PROVINCIAS VERDES - SISTEMA DE GESTIÓN ENERGÉTICA =====");
            if (usuarioLogueado == null) {
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Registrarse");
                System.out.println("0. Salir");
                opcion = EntradaDatos.leerEntero("Seleccione una opción: ");
                manejarAcceso(opcion);
            } else {
                System.out.println("👤 Usuario: " + usuarioLogueado.getNombre() + " | Tipo: " + usuarioLogueado.getTipo());
                System.out.println("1. Gestión de Ubicaciones");
                System.out.println("2. Gestión de Equipos Solares");
                System.out.println("3. Registrar Medición Energética");
                System.out.println("4. Ver Reportes y Estadísticas");
                System.out.println("5. Buscar y Consultar Datos");
                if (usuarioLogueado.getTipo() == TipoUsuario.ADMIN) {
                    System.out.println("6. Administrar Usuarios");
                }
                System.out.println("9. Cerrar Sesión");
                System.out.println("0. Salir");
                opcion = EntradaDatos.leerEntero("Seleccione una opción: ");
                manejarOpciones(opcion);
            }
        } while (true);
    }

    private static void manejarAcceso(int opcion) {
        switch (opcion) {
            case 1:
                login();
                break;
            case 2:
                registrarUsuario();
                break;
            case 0:
                System.out.println("👋 Saliendo del sistema...");
                System.exit(0);
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }

    private static void login() {
        System.out.println("\n--- 🔑 INICIO DE SESIÓN ---");
        String correo = EntradaDatos.leerTexto("Correo: ");
        String clave = EntradaDatos.leerTexto("Contraseña: ");
        usuarioLogueado = usuarioDAO.buscarPorCorreoYClave(correo, clave);
    }

    private static void registrarUsuario() {
        System.out.println("\n--- 📝 NUEVO USUARIO ---");
        String nombre = EntradaDatos.leerTexto("Nombre completo: ");
        String correo = EntradaDatos.leerTexto("Correo electrónico: ");
        String clave = EntradaDatos.leerTexto("Contraseña: ");
        
        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(correo);
        nuevo.setContrasena(clave);
        nuevo.setTipo(TipoUsuario.RESIDENCIAL);
        nuevo.setEstado(EstadoUsuario.ACTIVO);

        if (usuarioDAO.crear(nuevo)) {
            System.out.println("✅ Usuario registrado con éxito. Ya puedes iniciar sesión.");
        } else {
            System.out.println("❌ No se pudo registrar. El correo ya está en uso.");
        }
    }

    private static void manejarOpciones(int opcion) {
        switch (opcion) {
            case 1:
                menuUbicaciones();
                break;
            case 2:
                menuEquipos();
                break;
            case 3:
                registrarMedicion();
                break;
            case 4:
                mostrarReportes();
                break;
            case 5:
                buscarDatos();
                break;
            case 6:
                if (usuarioLogueado.getTipo() == TipoUsuario.ADMIN) menuAdmin();
                break;
            case 9:
                usuarioLogueado = null;
                System.out.println("🔓 Sesión cerrada.");
                break;
            case 0:
                System.out.println("👋 Saliendo del sistema...");
                System.exit(0);
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }

    private static void menuUbicaciones() {
        int op;
        do {
            System.out.println("\n--- 📍 GESTIÓN DE UBICACIONES ---");
            System.out.println("1. Agregar Ubicación");
            System.out.println("2. Ver mis Ubicaciones");
            System.out.println("0. Volver");
            op = EntradaDatos.leerEntero("Opción: ");
            if (op == 1) {
                Ubicacion u = new Ubicacion();
                u.setIdUsuario(usuarioLogueado.getId());
                u.setProvincia(EntradaDatos.leerTexto("Provincia: "));
                u.setDireccion(EntradaDatos.leerTexto("Dirección: "));
                u.setLatitud(EntradaDatos.leerDouble("Latitud: "));
                u.setLongitud(EntradaDatos.leerDouble("Longitud: "));
                if (ubiDAO.crear(u)) System.out.println("✅ Ubicación guardada.");
            } else if (op == 2) {
                List<Ubicacion> lista = ubiDAO.listarPorUsuario(usuarioLogueado.getId());
                if (lista.isEmpty()) {
                    System.out.println("ℹ️ No tienes ubicaciones registradas.");
                } else {
                    lista.forEach(System.out::println);
                }
            }
        } while (op != 0);
    }

    private static void menuEquipos() {
        int op;
        do {
            System.out.println("\n--- 🔌 GESTIÓN DE EQUIPOS ---");
            System.out.println("1. Agregar Equipo");
            System.out.println("2. Ver Equipos de una Ubicación");
            System.out.println("0. Volver");
            op = EntradaDatos.leerEntero("Opción: ");
            if (op == 1) {
                int idUbi = EntradaDatos.leerEntero("ID Ubicación: ");
                String tipoStr = EntradaDatos.leerTexto("Tipo (PANEL/INVERSOR/BATERIA/REGULADOR): ");
                double pot = EntradaDatos.leerDouble("Potencia nominal: ");
                double cap = EntradaDatos.leerDouble("Capacidad: ");

                try {
                    EquipoSolar eq = new EquipoSolar();
                    eq.setIdUbicacion(idUbi);
                    eq.setTipo(TipoEquipo.fromString(tipoStr));
                    eq.setPotenciaNominal(pot);
                    eq.setCapacidad(cap);
                    eq.setFechaInstalacion(LocalDateTime.now());
                    eq.setEstado(com.provinciasverdes.modelo.enums.EstadoEquipo.ACTIVO);

                    if (eqDAO.crear(eq)) {
                        System.out.println("✅ Equipo agregado correctamente.");
                    } else {
                        System.out.println("❌ No se pudo guardar el equipo.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Error: " + e.getMessage());
                }

            } else if (op == 2) {
                int idUbi = EntradaDatos.leerEntero("ID Ubicación: ");
                List<EquipoSolar> lista = eqDAO.listarPorUbicacion(idUbi);
                if (lista.isEmpty()) {
                    System.out.println("ℹ️ No hay equipos en esta ubicación.");
                } else {
                    lista.forEach(e -> System.out.println("ID:"+e.getId()+" | Tipo:"+e.getTipo()+" | Potencia:"+e.getPotenciaNominal()));
                }
            }
        } while (op != 0);
    }

    private static void registrarMedicion() {
        System.out.println("\n--- 📊 NUEVA MEDICIÓN ---");
        int idEq = EntradaDatos.leerEntero("ID Equipo: ");
        double gen = EntradaDatos.leerDouble("Energía Generada (kWh): ");
        double con = EntradaDatos.leerDouble("Energía Consumida (kWh): ");
        double vol = EntradaDatos.leerDouble("Voltaje: ");
        double cor = EntradaDatos.leerDouble("Corriente: ");

        RegistroEnergia r = new RegistroEnergia();
        r.setIdEquipo(idEq);
        r.setFechaHora(LocalDateTime.now());
        r.setEnergiaGenerada(gen);
        r.setEnergiaConsumida(con);
        r.setVoltaje(vol);
        r.setCorriente(cor);
        r.calcularBalance();

        if (regDAO.crear(r)) System.out.println("✅ Medición guardada. Balance: " + r.getBalance() + " kWh");
    }

    private static void mostrarReportes() {
        System.out.println("\n--- 📈 REPORTES ---");
        List<RegistroEnergia> todos = regDAO.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("ℹ️ No hay registros para generar reportes.");
            return;
        }
        String informe = GeneradorReporte.generarReporteEficiencia(todos, "Todo el periodo");
        System.out.println(informe);
        GeneradorReporte.generarYGuardarReporte(todos, "reporte_general", "reporte_provincias_verdes.txt");
        
        double balance = CalculadoraEnergetica.calcularBalanceTotal(todos);
        System.out.println("⚖️ Balance Global: " + String.format("%.2f", balance) + " kWh");
    }

    private static void buscarDatos() {
        System.out.println("\n--- 🔍 BUSCAR ---");
        int id = EntradaDatos.leerEntero("Ingrese ID a buscar: ");
        Ubicacion u = ubiDAO.leer(id);
        if (u != null) u.mostrarDatos();
        else System.out.println("❌ No encontrado.");
    }

    private static void menuAdmin() {
        System.out.println("\n--- ⚙️ ADMINISTRACIÓN DE USUARIOS ---");
        List<Usuario> todos = usuarioDAO.listarTodos();
        todos.forEach(u -> System.out.println("ID:"+u.getId()+" | Nombre:"+u.getNombre()+" | Tipo:"+u.getTipo()+" | Estado:"+u.getEstado()));
    }
}
