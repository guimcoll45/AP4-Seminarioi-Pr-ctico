package com.provinciasverdes.interfaz;

import com.provinciasverdes.modelo.Usuario;
import com.provinciasverdes.modelo.Ubicacion;
import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.modelo.enums.EstadoUsuario;
import com.provinciasverdes.modelo.enums.TipoUsuario;
import com.provinciasverdes.persistencia.UsuarioDAO;
import com.provinciasverdes.persistencia.UbicacionDAO;
import com.provinciasverdes.persistencia.EquipoSolarDAO;
import com.provinciasverdes.persistencia.RegistroEnergiaDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    public static Usuario usuarioLogueado = null;

    public static void mostrarMenu() {
        if (usuarioLogueado == null) {
            System.out.println("❌ Error: No hay sesión iniciada.");
            return;
        }

        int opcion;
        do {
            System.out.println("\n===== PROVINCIAS VERDES - GESTIÓN ENERGÉTICA =====");
            System.out.println("Usuario: " + usuarioLogueado.getNombre() + " | Perfil: " + usuarioLogueado.getPerfil());
            System.out.println("--------------------------------------------------");
            System.out.println("1. Gestión de Usuarios");
            System.out.println("2. Gestión de Ubicaciones");
            System.out.println("3. Gestión de Equipos Solares");
            System.out.println("4. Registrar Medición Energética");
            System.out.println("5. Ver Balance Energético");
            System.out.println("6. Ver y Exportar Reportes");
            System.out.println("7. Respaldar Datos");
            System.out.println("8. Configuración del Sistema");
            System.out.println("9. Cerrar Sesión");
            System.out.println("0. Volver al menú de inicio");
            System.out.print("Seleccione una opción: ");

            opcion = EntradaDatos.leerEntero("");

            switch (opcion) {
                case 1:
                    if (esAdmin()) gestionarUsuarios();
                    else System.out.println("❌ No tiene permisos para esta opción.");
                    break;
                case 2:
                    gestionarUbicaciones();
                    break;
                case 3:
                    gestionarEquipos();
                    break;
                case 4:
                    registrarMedicionEnergetica();
                    break;
                case 5:
                    verBalanceEnergetico();
                    break;
                case 6:
                    verYExportarReportes();
                    break;
                case 7:
                    respaldarDatos();
                    break;
                case 8:
                    configuracionSistema();
                    break;
                case 9:
                    usuarioLogueado = null;
                    System.out.println("🔒 Sesión cerrada correctamente.");
                    opcion = 0;
                    break;
                case 0:
                    usuarioLogueado = null;
                    System.out.println("↩️ Volviendo al menú de inicio...");
                    break;
                default:
                    System.out.println("⚠️ Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 0);
    }

    private static boolean esAdmin() {
        return usuarioLogueado != null && "ADMIN".equalsIgnoreCase(usuarioLogueado.getTipoUsuario().name());
    }

    // ======================================
    // 1. GESTIÓN DE USUARIOS
    // ======================================
    private static void gestionarUsuarios() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Usuarios ---");
            System.out.println("1. Ver todos los usuarios");
            System.out.println("2. Agregar nuevo usuario");
            System.out.println("3. Modificar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            opcion = EntradaDatos.leerEntero("");

            switch (opcion) {
                case 1: listarUsuarios(); break;
                case 2: agregarUsuario(); break;
                case 3: modificarUsuario(); break;
                case 4: eliminarUsuario(); break;
                case 0: break;
                default: System.out.println("⚠️ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void listarUsuarios() {
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("ℹ️ No hay usuarios registrados.");
            return;
        }
        for (Usuario u : lista) System.out.printf("ID: %d | Nombre: %s %s | Correo: %s | Tipo: %s | Estado: %s%n",
                u.getId(), u.getNombre(), u.getApellido(), u.getCorreo(), u.getTipoUsuario(), u.getEstado());
    }

    private static void agregarUsuario() {
        System.out.println("\n--- Agregar Usuario ---");
        String nombre = EntradaDatos.leerTexto("Nombre: ");
        String apellido = EntradaDatos.leerTexto("Apellido: ");
        String correo = EntradaDatos.leerTexto("Correo: ");
        String clave = EntradaDatos.leerTexto("Contraseña: ");
        System.out.println("Tipos disponibles: ADMIN | TECNICO | RESIDENCIAL | COMERCIAL");
        String tipoStr = EntradaDatos.leerTexto("Tipo: ").toUpperCase();

        try {
            TipoUsuario tipo = TipoUsuario.valueOf(tipoStr);
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setApellido(apellido);
            nuevo.setCorreo(correo);
            nuevo.setContrasena(clave);
            nuevo.setTipoUsuario(tipo);
            nuevo.setEstado(EstadoUsuario.ACTIVO);
            nuevo.setPerfil("Usuario " + tipo);

            UsuarioDAO dao = new UsuarioDAO();
            if (dao.crear(nuevo)) System.out.println("✅ Usuario agregado correctamente.");
            else System.out.println("❌ No se pudo agregar. ¿El correo ya existe?");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Tipo de usuario inválido.");
        }
    }

    private static void modificarUsuario() {
        System.out.println("\n--- Modificar Usuario ---");
        int id = EntradaDatos.leerEntero("ID del usuario: ");
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.leer(id);
        if (usuario == null) { System.out.println("❌ Usuario no encontrado."); return; }

        System.out.println("Deje vacío el campo para mantener el valor actual:");
        String nombre = EntradaDatos.leerTexto("Nuevo nombre (" + usuario.getNombre() + "): ");
        String apellido = EntradaDatos.leerTexto("Nuevo apellido (" + usuario.getApellido() + "): ");
        String correo = EntradaDatos.leerTexto("Nuevo correo (" + usuario.getCorreo() + "): ");
        String clave = EntradaDatos.leerTexto("Nueva contraseña: ");
        String tipoStr = EntradaDatos.leerTexto("Nuevo tipo (" + usuario.getTipoUsuario() + "): ").toUpperCase();
        String estadoStr = EntradaDatos.leerTexto("Nuevo estado (ACTIVO / INACTIVO): ").toUpperCase();

        if (!nombre.isBlank()) usuario.setNombre(nombre);
        if (!apellido.isBlank()) usuario.setApellido(apellido);
        if (!correo.isBlank()) usuario.setCorreo(correo);
        if (!clave.isBlank()) usuario.setContrasena(clave);
        if (!tipoStr.isBlank()) {
            try { usuario.setTipoUsuario(TipoUsuario.valueOf(tipoStr)); }
            catch (Exception e) { System.out.println("⚠️ Tipo inválido, se mantiene el actual."); }
        }
        if (!estadoStr.isBlank()) {
            try { usuario.setEstado(EstadoUsuario.valueOf(estadoStr)); }
            catch (Exception e) { System.out.println("⚠️ Estado inválido, se mantiene el actual."); }
        }

        if (dao.actualizar(usuario)) System.out.println("✅ Usuario actualizado.");
        else System.out.println("❌ Error al guardar cambios.");
    }

    private static void eliminarUsuario() {
        System.out.println("\n--- Eliminar Usuario ---");
        int id = EntradaDatos.leerEntero("ID del usuario: ");
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.borrar(id)) System.out.println("✅ Usuario eliminado correctamente.");
        else System.out.println("❌ No se pudo eliminar.");
    }

    // ======================================
    // 2. GESTIÓN DE UBICACIONES
    // ======================================
    private static void gestionarUbicaciones() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Ubicaciones ---");
            System.out.println("1. Ver todas las ubicaciones");
            System.out.println("2. Agregar ubicación");
            System.out.println("3. Modificar ubicación");
            System.out.println("4. Eliminar ubicación");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            opcion = EntradaDatos.leerEntero("");

            switch (opcion) {
                case 1: listarUbicaciones(); break;
                case 2: agregarUbicacion(); break;
                case 3: modificarUbicacion(); break;
                case 4: eliminarUbicacion(); break;
                case 0: break;
                default: System.out.println("⚠️ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void listarUbicaciones() {
        UbicacionDAO dao = new UbicacionDAO();
        List<Ubicacion> lista = dao.listarTodos();
        if (lista.isEmpty()) { System.out.println("ℹ️ No hay ubicaciones registradas."); return; }
        for (Ubicacion u : lista) System.out.printf("ID: %d | Provincia: %s | Ciudad: %s | Dirección: %s | CP: %s | ID Usuario: %d%n",
                u.getId(), u.getProvincia(), u.getCiudad(), u.getDireccion(), u.getCodigoPostal(), u.getIdUsuario());
    }

    private static void agregarUbicacion() {
        System.out.println("\n--- Agregar Ubicación ---");
        String provincia = EntradaDatos.leerTexto("Provincia: ");
        String ciudad = EntradaDatos.leerTexto("Ciudad: ");
        String direccion = EntradaDatos.leerTexto("Dirección: ");
        String cp = EntradaDatos.leerTexto("Código Postal: ");
        int idUsuario = EntradaDatos.leerEntero("ID del usuario responsable: ");

        Ubicacion ubi = new Ubicacion();
        ubi.setProvincia(provincia);
        ubi.setCiudad(ciudad);
        ubi.setDireccion(direccion);
        ubi.setCodigoPostal(cp);
        ubi.setIdUsuario(idUsuario);

        UbicacionDAO dao = new UbicacionDAO();
        if (dao.crear(ubi)) System.out.println("✅ Ubicación agregada.");
        else System.out.println("❌ No se pudo guardar.");
    }

    private static void modificarUbicacion() {
        System.out.println("\n--- Modificar Ubicación ---");
        int id = EntradaDatos.leerEntero("ID de ubicación: ");
        UbicacionDAO dao = new UbicacionDAO();
        Ubicacion ubi = (Ubicacion) dao.leer(id);
        if (ubi == null) { System.out.println("❌ Ubicación no encontrada."); return; }

        String prov = EntradaDatos.leerTexto("Provincia (" + ubi.getProvincia() + "): ");
        String ciu = EntradaDatos.leerTexto("Ciudad (" + ubi.getCiudad() + "): ");
        String dir = EntradaDatos.leerTexto("Dirección (" + ubi.getDireccion() + "): ");
        String cp = EntradaDatos.leerTexto("Código Postal (" + ubi.getCodigoPostal() + "): ");
        int idUsu = EntradaDatos.leerEntero("ID Usuario (" + ubi.getIdUsuario() + "): ");

        if (!prov.isBlank()) ubi.setProvincia(prov);
        if (!ciu.isBlank()) ubi.setCiudad(ciu);
        if (!dir.isBlank()) ubi.setDireccion(dir);
        if (!cp.isBlank()) ubi.setCodigoPostal(cp);
        if (idUsu > 0) ubi.setIdUsuario(idUsu);

        if (dao.actualizar(ubi)) System.out.println("✅ Ubicación actualizada.");
        else System.out.println("❌ Error al guardar cambios.");
    }

    private static void eliminarUbicacion() {
        System.out.println("\n--- Eliminar Ubicación ---");
        int id = EntradaDatos.leerEntero("ID de ubicación: ");
        UbicacionDAO dao = new UbicacionDAO();
        if (dao.borrar(id)) System.out.println("✅ Ubicación eliminada.");
        else System.out.println("❌ No se pudo eliminar.");
    }

    // ======================================
    // 3. GESTIÓN DE EQUIPOS SOLARES
    // ======================================
    private static void gestionarEquipos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Equipos Solares ---");
            System.out.println("1. Agregar nuevo equipo");
            System.out.println("2. Ver todos los equipos");
            System.out.println("3. Modificar equipo");
            System.out.println("4. Eliminar equipo");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            opcion = EntradaDatos.leerEntero("");

            switch (opcion) {
                case 1: agregarEquipo(); break;
                case 2: listarEquipos(); break;
                case 3: modificarEquipo(); break;
                case 4: eliminarEquipo(); break;
                case 0: break;
                default: System.out.println("⚠️ Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void listarEquipos() {
        EquipoSolarDAO dao = new EquipoSolarDAO();
        List<EquipoSolar> lista = dao.listarTodos();
        if (lista.isEmpty()) { System.out.println("ℹ️ No hay equipos registrados."); return; }
        for (EquipoSolar e : lista) System.out.printf("ID: %d | Modelo: %s | Potencia: %.2f kW | Tipo: %s | Estado: %s | Ubicación ID: %s%n",
                e.getId(), e.getModelo(), e.getPotenciaNominal(), e.getTipo(), e.getEstado(),
                e.getIdUbicacion() != null ? e.getIdUbicacion() : "Sin asignar");
    }

    private static void agregarEquipo() {
        System.out.println("\n--- Agregar Nuevo Equipo Solar ---");
        String modelo = EntradaDatos.leerTexto("Modelo: ");
        double potencia = EntradaDatos.leerDouble("Potencia nominal (kW): ");
        String tipo = EntradaDatos.leerTexto("Tipo (MONOCRISTALINO / POLICRISTALINO / PELICULA_FINA): ");
        String fechaStr = EntradaDatos.leerTexto("Fecha de instalación (AAAA-MM-DD): ");
        int idUbicacion = EntradaDatos.leerEntero("ID de ubicación (0 = sin asignar): ");

        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            EquipoSolar equipo = new EquipoSolar();
            equipo.setModelo(modelo);
            equipo.setPotenciaNominal(potencia);
            equipo.setTipo(tipo.toUpperCase());
            equipo.setFechaInstalacion(fecha);
            equipo.setIdUbicacion(idUbicacion > 0 ? idUbicacion : null);
            equipo.setEstado("ACTIVO");

            EquipoSolarDAO dao = new EquipoSolarDAO();
            if (dao.crear(equipo)) System.out.println("✅ Equipo agregado correctamente.");
            else System.out.println("❌ No se pudo guardar.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de fecha incorrecto. Use AAAA-MM-DD.");
        }
    }

    private static void modificarEquipo() {
        System.out.println("\n--- Modificar Equipo ---");
        int id = EntradaDatos.leerEntero("ID del equipo: ");
        EquipoSolarDAO dao = new EquipoSolarDAO();
        EquipoSolar eq = (EquipoSolar) dao.leer(id);
        if (eq == null) { System.out.println("❌ Equipo no encontrado."); return; }

        String modelo = EntradaDatos.leerTexto("Modelo (" + eq.getModelo() + "): ");
        double potencia = EntradaDatos.leerDouble("Potencia (" + eq.getPotenciaNominal() + " kW): ");
        String tipo = EntradaDatos.leerTexto("Tipo (" + eq.getTipo() + "): ");
        String fechaStr = EntradaDatos.leerTexto("Fecha instalación (" + eq.getFechaInstalacion() + "): ");
        int idUbi = EntradaDatos.leerEntero("ID Ubicación (" + (eq.getIdUbicacion() != null ? eq.getIdUbicacion() : "Sin asignar") + "): ");
        String estado = EntradaDatos.leerTexto("Estado (ACTIVO / INACTIVO): ");

        if (!modelo.isBlank()) eq.setModelo(modelo);
        if (potencia > 0) eq.setPotenciaNominal(potencia);
        if (!tipo.isBlank()) eq.setTipo(tipo.toUpperCase());
        if (!fechaStr.isBlank()) {
            try { eq.setFechaInstalacion(LocalDate.parse(fechaStr)); }
            catch (Exception e) { System.out.println("⚠️ Fecha inválida, se mantiene la actual."); }
        }
        if (idUbi >= 0) eq.setIdUbicacion(idUbi > 0 ? idUbi : null);
        if (!estado.isBlank()) eq.setEstado(estado.toUpperCase());

        if (dao.actualizar(eq)) System.out.println("✅ Equipo actualizado.");
        else System.out.println("❌ Error al guardar cambios.");
    }

    private static void eliminarEquipo() {
        System.out.println("\n--- Eliminar Equipo ---");
        int id = EntradaDatos.leerEntero("ID del equipo: ");
        EquipoSolarDAO dao = new EquipoSolarDAO();
        if (dao.borrar(id)) System.out.println("✅ Equipo eliminado correctamente.");
        else System.out.println("❌ No se pudo eliminar.");
    }

    // ======================================
    // 4. REGISTRAR MEDICIÓN ENERGÉTICA
    // ======================================
    private static void registrarMedicionEnergetica() {
        System.out.println("\n--- ⚡ Registrar Medición Energética ---");
        int idEquipo = EntradaDatos.leerEntero("ID del equipo solar: ");
        String fechaStr = EntradaDatos.leerTexto("Fecha de medición (AAAA-MM-DD): ");
        double generado = EntradaDatos.leerDouble("Energía generada (kWh): ");
        double consumido = EntradaDatos.leerDouble("Energía consumida (kWh): ");
        double voltaje = EntradaDatos.leerDouble("Voltaje (V): ");
        double corriente = EntradaDatos.leerDouble("Corriente (A): ");

        try {
            LocalDateTime fechaHora = LocalDate.parse(fechaStr).atStartOfDay();
            double balance = generado - consumido;

            RegistroEnergia registro = new RegistroEnergia();
            registro.setIdEquipo(idEquipo);
            registro.setFecha_hora(fechaHora);
            registro.setVoltaje(voltaje);
            registro.setCorriente(corriente);
            registro.setEnergia_generada(generado);
            registro.setEnergia_consumida(consumido);
            registro.setBalance(balance);

            RegistroEnergiaDAO dao = new RegistroEnergiaDAO();
            if (dao.crear(registro)) {
                System.out.printf("✅ Medición guardada | Generado: %.2f kWh | Consumido: %.2f kWh | Balance: %.2f kWh%n",
                        generado, consumido, balance);
            } else {
                System.out.println("❌ No se pudo guardar. Verifique que el ID del equipo exista.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de fecha incorrecto. Use AAAA-MM-DD.");
        } catch (Exception e) {
            System.out.println("❌ Error al registrar: " + e.getMessage());
        }
    }

    // ======================================
    // 5. VER BALANCE ENERGÉTICO
    // ======================================
    private static void verBalanceEnergetico() {
        System.out.println("\n--- Ver Balance Energético ---");
        int idEquipo = EntradaDatos.leerEntero("Ingrese ID del equipo (0 = ver todos): ");
        RegistroEnergiaDAO dao = new RegistroEnergiaDAO();
        List<RegistroEnergia> registros;

        if (idEquipo == 0) registros = dao.obtenerTodos();
        else registros = dao.listarPorEquipo(idEquipo);

        if (registros.isEmpty()) {
            System.out.println("ℹ️ No hay mediciones registradas.");
            return;
        }

        double totalGenerado = 0, totalConsumido = 0, totalBalance = 0;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("--------------------------------------------------");
        System.out.printf("%-12s | %-10s | %-10s | %-10s%n", "Fecha", "Generado", "Consumido", "Balance");
        System.out.println("--------------------------------------------------");

        for (RegistroEnergia r : registros) {
            System.out.printf("%-12s | %10.2f | %10.2f | %10.2f%n",
                    r.getFecha_hora().format(formato),
                    r.getEnergia_generada(),
                    r.getEnergia_consumida(),
                    r.getBalance());
            totalGenerado += r.getEnergia_generada();
            totalConsumido += r.getEnergia_consumida();
            totalBalance += r.getBalance();
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("TOTALES: Generado: %.2f kWh | Consumido: %.2f kWh | Balance: %.2f kWh%n",
                totalGenerado, totalConsumido, totalBalance);
    }

    // ======================================
    // 6. VER Y EXPORTAR REPORTES
    // ======================================
    private static void verYExportarReportes() {
        System.out.println("\n--- Ver y Exportar Reportes ---");
        System.out.println("1. Ver resumen de balances");
        System.out.println("2. Exportar reporte a archivo TXT");
        System.out.print("Seleccione: ");
        int op = EntradaDatos.leerEntero("");

        if (op == 1) {
            verBalanceEnergetico();
        } else if (op == 2) {
            RegistroEnergiaDAO dao = new RegistroEnergiaDAO();
            List<RegistroEnergia> registros = dao.obtenerTodos();
            if (registros.isEmpty()) {
                System.out.println("ℹ️ No hay datos para exportar.");
                return;
            }

            StringBuilder contenido = new StringBuilder();
            contenido.append("=== REPORTE ENERGÉTICO - PROVINCIAS VERDES ===\n");
            contenido.append("Generado el: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");
            contenido.append("Fecha\tGenerado(kWh)\tConsumido(kWh)\tBalance(kWh)\n");

            for (RegistroEnergia r : registros) {
                contenido.append(r.getFecha_hora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .append("\t")
                        .append(String.format("%.2f", r.getEnergia_generada()))
                        .append("\t")
                        .append(String.format("%.2f", r.getEnergia_consumida()))
                        .append("\t")
                        .append(String.format("%.2f", r.getBalance()))
                        .append("\n");
            }

            String ruta = "reporte_energetico_" + LocalDate.now() + ".txt";
            if (escribirArchivo(ruta, contenido.toString())) {
                System.out.println("✅ Reporte guardado en: " + ruta);
            } else {
                System.out.println("❌ No se pudo guardar el archivo.");
            }
        } else {
            System.out.println("⚠️ Opción inválida.");
        }
    }

    // ======================================
    // 7. RESPALDAR DATOS
    // ======================================
    private static void respaldarDatos() {
        System.out.println("\n--- Respaldar Datos ---");
        String carpeta = "respaldos/respaldo_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        System.out.println("Creando respaldo en: " + carpeta);

        if (!crearCarpeta(carpeta)) {
            System.out.println("❌ No se pudo crear la carpeta de respaldo.");
            return;
        }

        boolean ok = true;
        ok &= exportarLista(new UsuarioDAO().listarTodos(), carpeta + "/usuarios.txt");
        ok &= exportarLista(new UbicacionDAO().listarTodos(), carpeta + "/ubicaciones.txt");
        ok &= exportarLista(new EquipoSolarDAO().listarTodos(), carpeta + "/equipos.txt");
        ok &= exportarLista(new RegistroEnergiaDAO().listarTodos(), carpeta + "/registros.txt");

        if (ok) System.out.println("✅ Respaldo completo generado correctamente.");
        else System.out.println("⚠️ Respaldo parcial, algunos archivos fallaron.");
    }

    // ======================================
    // 8. CONFIGURACIÓN DEL SISTEMA
    // ======================================
    private static void configuracionSistema() {
        System.out.println("\n--- Configuración del Sistema ---");
        System.out.println("1. Cambiar mis datos de usuario");
        System.out.println("2. Ver información del sistema");
        System.out.print("Seleccione: ");
        int op = EntradaDatos.leerEntero("");

        if (op == 1) {
            System.out.println("Modificar datos de: " + usuarioLogueado.getNombre());
            String correo = EntradaDatos.leerTexto("Nuevo correo (" + usuarioLogueado.getCorreo() + "): ");
            String clave = EntradaDatos.leerTexto("Nueva contraseña: ");

            if (!correo.isBlank()) usuarioLogueado.setCorreo(correo);
            if (!clave.isBlank()) usuarioLogueado.setContrasena(clave);

            UsuarioDAO dao = new UsuarioDAO();
            if (dao.actualizar(usuarioLogueado)) System.out.println("✅ Datos actualizados.");
            else System.out.println("❌ Error al guardar.");
        } else if (op == 2) {
            System.out.println("\n--- Información del Sistema ---");
            System.out.println("Sistema: Provincias Verdes - Gestión Energética");
            System.out.println("Versión: 1.0");
            System.out.println("Desarrollado para: Seminario Práctico AP4");
            System.out.println("Fecha actual: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            System.out.println("⚠️ Opción inválida.");
        }
    }

    // ======================================
    // Métodos auxiliares para archivos
    // ======================================
    private static boolean escribirArchivo(String ruta, String contenido) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta, false))) {
            pw.write(contenido);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean crearCarpeta(String ruta) {
        return Files.exists(Paths.get(ruta)) || new java.io.File(ruta).mkdirs();
    }

    private static <T> boolean exportarLista(List<T> lista, String ruta) {
        if (lista.isEmpty()) return escribirArchivo(ruta, "Sin registros\n");
        StringBuilder sb = new StringBuilder();
        for (T elemento : lista) {
            sb.append(elemento.toString()).append("\n");
        }
        return escribirArchivo(ruta, sb.toString());
    }
}