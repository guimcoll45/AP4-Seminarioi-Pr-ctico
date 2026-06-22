package com.provinciasverdes.interfaz;

import com.provinciasverdes.enums.EstadoEquipo;
import com.provinciasverdes.enums.EstadoUsuario;
import com.provinciasverdes.enums.TipoEquipo;
import com.provinciasverdes.enums.TipoUsuario;
import com.provinciasverdes.modelo.EquipoSolar;
import com.provinciasverdes.modelo.Perfil;
import com.provinciasverdes.modelo.RegistroEnergia;
import com.provinciasverdes.modelo.Reporte;
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
            System.out.println("❌ Datos incorrectos o usuario no activo");
        }
    }

    private static void registrarUsuario() {
        String nombre = EntradaDatos.leerCadena("Nombre completo");
        String correo = EntradaDatos.leerCadena("Correo electrónico");
        String clave = EntradaDatos.leerCadena("Contraseña");

        if (!Validador.validarCorreo(correo)) {
            System.out.println("❌ Formato de correo inválido");
            return;
        }
        if (!Validador.validarContrasena(clave)) {
            System.out.println("❌ La contraseña debe tener al menos 4 caracteres");
            return;
        }

        Perfil perfil = new Perfil(0, TipoUsuario.RESIDENCIAL, "Usuario residencial estándar");
        Usuario nuevo = new Usuario(0, nombre, correo, clave, perfil, EstadoUsuario.ACTIVO);

        if (usuarioDAO.agregar(nuevo)) {
            System.out.println("✅ Registro exitoso. Ya puedes iniciar sesión");
        } else {
            System.out.println("❌ No se pudo completar el registro");
        }
    }

    private static void mostrarMenuUsuario() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("Usuario: " + usuarioActual.getNombre() + " | Perfil: " + usuarioActual.getPerfil().getTipo());
            System.out.println("1. Ver mis ubicaciones");
            System.out.println("2. Agregar nueva ubicación");
            System.out.println("3. Agregar equipo solar a una ubicación");
            System.out.println("4. Registrar medición de energía");
            System.out.println("5. Ver reporte energético");
            if (usuarioActual.getPerfil().getTipo() == TipoUsuario.ADMIN) {
                System.out.println("6. Administrar usuarios del sistema");
            }
            System.out.println("0. Cerrar sesión");
            opcion = EntradaDatos.leerEntero("Seleccione una opción");

            switch (opcion) {
                case 1 -> verUbicaciones();
                case 2 -> agregarUbicacion();
                case 3 -> agregarEquipo();
                case 4 -> agregarRegistro();
                case 5 -> verReporte();
                case 6 -> {
                    if (usuarioActual.getPerfil().getTipo() == TipoUsuario.ADMIN) {
                        menuAdministracionUsuarios();
                    } else {
                        System.out.println("❌ No tienes permisos para esta acción");
                    }
                }
                case 0 -> {
                    usuarioActual = null;
                    System.out.println("🔑 Sesión cerrada correctamente");
                }
                default -> System.out.println("⚠️ Opción no válida, intenta nuevamente");
            }
        } while (opcion != 0);
    }

    private static void verUbicaciones() {
        List<Ubicacion> lista = ubicacionDAO.obtenerPorUsuario(usuarioActual.getId());
        if (lista.isEmpty()) {
            System.out.println("ℹ️ No tienes ubicaciones registradas todavía");
            return;
        }
        System.out.println("\n--- Mis Ubicaciones ---");
        lista.forEach(System.out::println);
    }

    private static void agregarUbicacion() {
        String provincia = EntradaDatos.leerCadena("Provincia");
        String direccion = EntradaDatos.leerCadena("Dirección completa");
        double latitud = EntradaDatos.leerDoble("Latitud");
        double longitud = EntradaDatos.leerDoble("Longitud");

        Ubicacion nuevaUbicacion = new Ubicacion(0, usuarioActual.getId(), provincia, direccion, latitud, longitud);

        if (!Validador.validarUbicacion(nuevaUbicacion)) {
            System.out.println("❌ Datos de ubicación inválidos. Revisa las coordenadas y campos obligatorios");
            return;
        }

        if (ubicacionDAO.agregar(nuevaUbicacion)) {
            System.out.println("✅ Ubicación agregada correctamente");
        } else {
            System.out.println("❌ Error al guardar la ubicación");
        }
    }

    private static void agregarEquipo() {
        List<Ubicacion> misUbicaciones = ubicacionDAO.obtenerPorUsuario(usuarioActual.getId());
        if (misUbicaciones.isEmpty()) {
            System.out.println("ℹ️ Primero debes agregar al menos una ubicación");
            return;
        }

        System.out.println("\n--- Ubicaciones disponibles ---");
        misUbicaciones.forEach(System.out::println);
        int idUbicacion = EntradaDatos.leerEntero("Ingresa el ID de la ubicación donde instalarás el equipo");

        String tipoStr = EntradaDatos.leerCadena("Tipo de equipo (PANEL_SOLAR, INVERSOR, BATERIA, REGULADOR)").toUpperCase();
        TipoEquipo tipo;
        try {
            tipo = TipoEquipo.valueOf(tipoStr);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Tipo de equipo no válido");
            return;
        }

        double potencia = EntradaDatos.leerDoble("Potencia nominal (en kW)");
        String modelo = EntradaDatos.leerCadena("Modelo del equipo");

        EquipoSolar nuevoEquipo = new EquipoSolar(
                0,
                idUbicacion,
                tipo,
                potencia,
                modelo,
                LocalDateTime.now(),
                EstadoEquipo.ACTIVO
        );

        if (!Validador.validarEquipo(nuevoEquipo)) {
            System.out.println("❌ Datos del equipo inválidos. Verifica que la potencia sea mayor a 0");
            return;
        }

        if (equipoDAO.agregar(nuevoEquipo)) {
            System.out.println("✅ Equipo agregado correctamente");
        } else {
            System.out.println("❌ No se pudo registrar el equipo");
        }
    }

    private static void agregarRegistro() {
        int idEquipo = EntradaDatos.leerEntero("ID del equipo solar");
        double voltaje = EntradaDatos.leerDoble("Voltaje medido (V)");
        double corriente = EntradaDatos.leerDoble("Corriente medida (A)");
        double generada = EntradaDatos.leerDoble("Energía generada (kWh)");
        double consumida = EntradaDatos.leerDoble("Energía consumida (kWh)");

        RegistroEnergia nuevoRegistro = new RegistroEnergia(
                0,
                idEquipo,
                LocalDateTime.now(),
                voltaje,
                corriente,
                generada,
                consumida
        );

        if (!Validador.validarRegistro(nuevoRegistro)) {
            System.out.println("❌ Valores inválidos. Las energías no pueden ser negativas");
            return;
        }

        if (registroDAO.agregar(nuevoRegistro)) {
            System.out.println("✅ Medición registrada correctamente");
        } else {
            System.out.println("❌ Error al guardar el registro");
        }
    }

    private static void verReporte() {
        int idEquipo = EntradaDatos.leerEntero("Ingresa el ID del equipo para generar el reporte");
        List<RegistroEnergia> registros = registroDAO.listarPorEquipo(idEquipo);

        if (registros.isEmpty()) {
            System.out.println("ℹ️ No hay mediciones registradas para este equipo");
            return;
        }

        Reporte reporte = GeneradorReporte.generarReporte(usuarioActual, registros);
        System.out.println("\n" + GeneradorReporte.exportarReporteTexto(reporte));
    }

    private static void menuAdministracionUsuarios() {
        int opcion;
        do {
            System.out.println("\n===== ADMINISTRACIÓN DE USUARIOS =====");
            System.out.println("1. Ver todos los usuarios");
            System.out.println("2. Agregar nuevo usuario");
            System.out.println("3. Modificar usuario");
            System.out.println("4. Cambiar estado de usuario");
            System.out.println("0. Volver al menú principal");
            opcion = EntradaDatos.leerEntero("Seleccione una opción");

            switch (opcion) {
                case 1 -> listarTodosUsuarios();
                case 2 -> agregarUsuarioAdmin();
                case 3 -> modificarUsuario();
                case 4 -> cambiarEstadoUsuario();
                case 0 -> System.out.println("↩️ Volviendo...");
                default -> System.out.println("⚠️ Opción inválida");
            }
        } while (opcion != 0);
    }

    private static void listarTodosUsuarios() {
        List<Usuario> todos = usuarioDAO.obtenerTodos();
        if (todos.isEmpty()) {
            System.out.println("ℹ️ No hay usuarios registrados en el sistema");
            return;
        }
        System.out.println("\n--- Lista Completa de Usuarios ---");
        todos.forEach(System.out::println);
    }

    private static void agregarUsuarioAdmin() {
        String nombre = EntradaDatos.leerCadena("Nombre completo");
        String correo = EntradaDatos.leerCadena("Correo electrónico");
        String clave = EntradaDatos.leerCadena("Contraseña");
        String tipoStr = EntradaDatos.leerCadena("Tipo de usuario (ADMIN / RESIDENCIAL / COMERCIAL)").toUpperCase();

        if (!Validador.validarCorreo(correo) || !Validador.validarContrasena(clave)) {
            System.out.println("❌ Datos de acceso inválidos");
            return;
        }

        TipoUsuario tipo;
        try {
            tipo = TipoUsuario.valueOf(tipoStr);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Tipo de usuario no válido");
            return;
        }

        Perfil perfil = new Perfil(0, tipo, "Perfil asignado por administrador");
        Usuario nuevo = new Usuario(0, nombre, correo, clave, perfil, EstadoUsuario.ACTIVO);

        if (usuarioDAO.agregar(nuevo)) {
            System.out.println("✅ Usuario creado correctamente");
        } else {
            System.out.println("❌ No se pudo crear el usuario");
        }
    }

    private static void modificarUsuario() {
        int id = EntradaDatos.leerEntero("ID del usuario a modificar");
        Usuario usuario = usuarioDAO.obtenerPorId(id);
        if (usuario == null) {
            System.out.println("❌ Usuario no encontrado");
            return;
        }

        System.out.println("Deja el campo vacío si no deseas modificarlo");
        String nuevoNombre = EntradaDatos.leerCadena("Nuevo nombre");
        if (!nuevoNombre.isBlank()) usuario.setNombre(nuevoNombre);

        String nuevoCorreo = EntradaDatos.leerCadena("Nuevo correo");
        if (!nuevoCorreo.isBlank() && Validador.validarCorreo(nuevoCorreo)) usuario.setCorreo(nuevoCorreo);

        String nuevaClave = EntradaDatos.leerCadena("Nueva contraseña");
        if (!nuevaClave.isBlank() && Validador.validarContrasena(nuevaClave)) usuario.setContrasena(nuevaClave);

        String nuevoTipo = EntradaDatos.leerCadena("Nuevo perfil (ADMIN/RESIDENCIAL/COMERCIAL)").toUpperCase();
        if (!nuevoTipo.isBlank()) {
            try {
                TipoUsuario tipo = TipoUsuario.valueOf(nuevoTipo);
                usuario.getPerfil().setTipo(tipo);
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Perfil inválido, se mantiene el anterior");
            }
        }

        if (usuarioDAO.actualizar(usuario)) {
            System.out.println("✅ Usuario actualizado");
        } else {
            System.out.println("❌ Error al modificar");
        }
    }

    private static void cambiarEstadoUsuario() {
        int id = EntradaDatos.leerEntero("ID del usuario");
        Usuario usuario = usuarioDAO.obtenerPorId(id);
        if (usuario == null) {
            System.out.println("❌ Usuario no existe");
            return;
        }

        String estadoStr = EntradaDatos.leerCadena("Nuevo estado (ACTIVO / INACTIVO / SUSPENDIDO)").toUpperCase();
        try {
            EstadoUsuario estado = EstadoUsuario.valueOf(estadoStr);
            usuario.setEstado(estado);
            if (usuarioDAO.actualizar(usuario)) {
                System.out.println("✅ Estado actualizado");
            } else {
                System.out.println("❌ No se pudo cambiar el estado");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Estado no válido");
        }
    }
}
