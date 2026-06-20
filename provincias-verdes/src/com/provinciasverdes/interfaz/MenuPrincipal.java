package com.provinciasverdes.interfaz;

import com.provinciasverdes.modelo.*;
import com.provinciasverdes.negocio.*;
import com.provinciasverdes.persistencia.*;
import java.util.*;
import java.sql.*;

public class MenuPrincipal {

    public static void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        System.out.println("=====================================");
        System.out.println("     ? SISTEMA PROVINCIAS VERDES    ");
        System.out.println("=====================================");

        // ==== LOGIN ====
        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String clave = scanner.nextLine();

        UsuarioDAO usuarioDao = new UsuarioDAO();
        Usuario usuarioLogueado = usuarioDao.buscarPorCorreoYClave(correo, clave);

        if (usuarioLogueado == null) {
            System.out.println("❌ Usuario o contraseña incorrectos. Saliendo...");
            return;
        }

        System.out.println("✅ Bienvenido/a: " + usuarioLogueado.getNombre() + " | Perfil: " + usuarioLogueado.getPerfil());
        System.out.println("=====================================\n");

        // ==== MENÚ PRINCIPAL ====
        do {
            System.out.println("\n===== ? PROVINCIAS VERDES - MENÚ =====");
            System.out.println("Usuario: " + usuarioLogueado.getNombre() + " | Perfil: " + usuarioLogueado.getPerfil());
            System.out.println("=====================================");
            System.out.println("1. Gestión de Usuarios");
            System.out.println("2. Gestión de Ubicaciones");
            System.out.println("3. Gestión de Equipos Solares");
            System.out.println("4. Registro de Mediciones");
            System.out.println("5. Consultas y Balance Energético");
            System.out.println("6. Generación de Reportes");
            System.out.println("7. Herramientas (Ordenar / Buscar)");
            System.out.println("8. Salir");
            System.out.print("Ingrese su opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Ingrese un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {

                // ==============================================
                // OPCIÓN 1: GESTIÓN DE USUARIOS
                // ==============================================
                case 1:
                    System.out.println("\n===== GESTIÓN DE USUARIOS =====");
                    if (!usuarioLogueado.getPerfil().equals("ADMIN")) {
                        System.out.println("❌ ACCESO DENEGADO: Solo administradores.");
                        break;
                    }

                    int opcUser;
                    do {
                        System.out.println("\n1. Listar todos");
                        System.out.println("2. Buscar por correo");
                        System.out.println("3. Crear nuevo usuario");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Elija una opción: ");

                        while (!scanner.hasNextInt()) {
                            System.out.print("Ingrese un número válido: ");
                            scanner.next();
                        }
                        opcUser = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcUser) {
                            case 1:
                                System.out.println("\n--- Lista Completa ---");
                                List<Usuario> listaUsuarios = usuarioDao.listarTodos();
                                if (listaUsuarios.isEmpty()) {
                                    System.out.println("⚠️ No hay usuarios registrados.");
                                } else {
                                    for (Usuario u : listaUsuarios) {
                                        System.out.println("ID:" + u.getId() + " | Nombre:" + u.getNombre() + " | Correo:" + u.getCorreo() + " | Perfil:" + u.getPerfil());
                                    }
                                }
                                break;

                            case 2:
                                System.out.print("Ingrese correo a buscar: ");
                                String correoBuscar = scanner.nextLine();
                                Usuario encontrado = usuarioDao.buscarPorCorreoYClave(correoBuscar, "");
                                if (encontrado != null) {
                                    System.out.println("✅ Encontrado: " + encontrado.getNombre() + " | Perfil: " + encontrado.getPerfil());
                                } else {
                                    System.out.println("❌ No encontrado.");
                                }
                                break;

                            case 3:
                                System.out.println("--- Nuevo Usuario ---");
                                System.out.print("Nombre: ");
                                String nombreNuevo = scanner.nextLine();
                                System.out.print("Correo: ");
                                String correoNuevo = scanner.nextLine();
                                System.out.print("Contraseña: ");
                                String passNuevo = scanner.nextLine();
                                System.out.print("Perfil (ADMIN / USUARIO): ");
                                String perfilNuevo = scanner.nextLine().toUpperCase();

                                Usuario nuevo = new Usuario(0, nombreNuevo, correoNuevo, passNuevo, perfilNuevo);
                                if (usuarioDao.crear(nuevo)) {
                                    System.out.println("✅ Usuario creado exitosamente.");
                                } else {
                                    System.out.println("❌ Error: Correo ya existe o datos inválidos.");
                                }
                                break;

                            case 4:
                                System.out.println("🔙 Volviendo...");
                                break;

                            default:
                                System.out.println("⚠️ Opción inválida.");
                        }
                    } while (opcUser != 4);
                    break;

                // ==============================================
                // OPCIÓN 2: GESTIÓN DE UBICACIONES (✅ ARREGLADA)
                // ==============================================
                case 2:
                    System.out.println("\n===== GESTIÓN DE UBICACIONES =====");
                    UbicacionDAO ubiDao = new UbicacionDAO();
                    int opcUbi;
                    do {
                        System.out.println("\n1. Ver todas");
                        System.out.println("2. Agregar nueva");
                        System.out.println("3. Buscar por ID");
                        System.out.println("4. Volver");
                        System.out.print("Opción: ");
                        
                        while (!scanner.hasNextInt()) { 
                            System.out.print("Ingrese un número válido: ");
                            scanner.next();
                        }
                        opcUbi = scanner.nextInt();
                        scanner.nextLine(); // Limpieza segura

                        switch (opcUbi) {
                            case 1:
                                List<Ubicacion> listaUbi = ubiDao.listarTodas();
                                for (Ubicacion u : listaUbi) {
                                    System.out.println("ID:" + u.getId() + " | " + u.getProvincia() + " - " + u.getDireccion());
                                }
                                break;

                            case 2:
                                System.out.print("Provincia: "); 
                                String prov = scanner.nextLine();
                                
                                System.out.print("Dirección: "); 
                                String dir = scanner.nextLine();

                                System.out.print("Latitud (ej: -26.17): "); 
                                while (!scanner.hasNextDouble()) { 
                                    System.out.print("Error. Ingrese número válido (ej: -26.17): ");
                                    scanner.next();
                                }
                                double lat = scanner.nextDouble();
                                scanner.nextLine(); // Limpieza obligatoria después de número

                                System.out.print("Longitud (ej: -58.18): "); 
                                while (!scanner.hasNextDouble()) { 
                                    System.out.print("Error. Ingrese número válido (ej: -58.18): ");
                                    scanner.next();
                                }
                                double lon = scanner.nextDouble();
                                scanner.nextLine(); // Limpieza obligatoria después de número

                                Ubicacion nuevaUbi = new Ubicacion(0, usuarioLogueado.getId(), prov, dir, lat, lon);
                                if (ubiDao.crear(nuevaUbi)) {
                                    System.out.println("✅ Ubicación guardada correctamente.");
                                } else {
                                    System.out.println("❌ Error al guardar ubicación.");
                                }
                                break;

                            case 3:
                                System.out.print("Ingrese ID de ubicación: "); 
                                while (!scanner.hasNextInt()) { 
                                    System.out.print("ID inválido, ingrese un número: ");
                                    scanner.next();
                                }
                                int idUbi = scanner.nextInt();
                                scanner.nextLine();
                                
                                Ubicacion b = ubiDao.buscarPorId(idUbi);
                                if (b != null) {
                                    System.out.println("✅ " + b.getProvincia() + ", " + b.getDireccion());
                                } else {
                                    System.out.println("❌ Ubicación no encontrada.");
                                }
                                break;

                            case 4:
                                System.out.println("🔙 Volviendo al menú...");
                                break;

                            default:
                                System.out.println("⚠️ Opción inválida.");
                        }
                    } while (opcUbi != 4);
                    break;

                // ==============================================
                // OPCIÓN 3: GESTIÓN DE EQUIPOS SOLARES
                // ==============================================
                case 3:
                    System.out.println("\n===== GESTIÓN DE EQUIPOS SOLARES =====");
                    EquipoSolarDAO eqDao = new EquipoSolarDAO();
                    int opcEq;
                    do {
                        System.out.println("\n1. Ver todos");
                        System.out.println("2. Agregar equipo");
                        System.out.println("3. Filtrar por ubicación");
                        System.out.println("4. Volver");
                        System.out.print("Opción: ");
                        while (!scanner.hasNextInt()) scanner.next();
                        opcEq = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcEq) {
                            case 1:
                                List<EquipoSolar> equipos = eqDao.listarTodos();
                                for (EquipoSolar e : equipos) {
                                    System.out.println("ID:" + e.getId() + " | Tipo:" + e.getTipo() + " | Potencia:" + e.getPotencia() + "kW");
                                }
                                break;
                            case 2:
                                System.out.print("ID Ubicación: "); int idUbi = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Tipo (Panel/Inversor): "); String tipo = scanner.nextLine();
                                System.out.print("Potencia: "); double pot = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.print("Modelo: "); String mod = scanner.nextLine();

                                EquipoSolar nuevoEq = new EquipoSolar(0, idUbi, tipo, pot, mod);
                                if (eqDao.crear(nuevoEq)) System.out.println("✅ Equipo agregado.");
                                else System.out.println("❌ Error.");
                                break;
                            case 3:
                                System.out.print("ID Ubicación: "); int id = scanner.nextInt();
                                scanner.nextLine();
                                List<EquipoSolar> listaEqUbi = eqDao.listarPorUbicacion(id);
                                if (listaEqUbi.isEmpty()) System.out.println("⚠️ Sin equipos aquí.");
                                else listaEqUbi.forEach(e -> System.out.println("- " + e.getTipo() + " | " + e.getModelo()));
                                break;
                        }
                    } while (opcEq != 4);
                    break;

                // ==============================================
                // OPCIÓN 4: REGISTRO DE MEDICIONES
                // ==============================================
                case 4:
                    System.out.println("\n===== REGISTRO DE MEDICIONES =====");
                    RegistroEnergiaDAO regDao = new RegistroEnergiaDAO();
                    int opcReg;
                    do {
                        System.out.println("\n1. Cargar nueva medición");
                        System.out.println("2. Ver historial");
                        System.out.println("3. Volver");
                        System.out.print("Opción: ");
                        while (!scanner.hasNextInt()) scanner.next();
                        opcReg = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcReg) {
                            case 1:
                                System.out.print("ID Equipo: "); int idEq = scanner.nextInt();
                                System.out.print("Voltaje (V): "); double vol = scanner.nextDouble();
                                System.out.print("Corriente (A): "); double cor = scanner.nextDouble();
                                System.out.print("Energía Generada: "); double gen = scanner.nextDouble();
                                System.out.print("Energía Consumida: "); double con = scanner.nextDouble();
                                scanner.nextLine();

                                RegistroEnergia r = new RegistroEnergia(0, idEq, new java.util.Date(), vol, cor, gen, con);
                                if (regDao.crear(r)) System.out.println("✅ Medición guardada.");
                                else System.out.println("❌ Error al guardar.");
                                break;
                            case 2:
                                List<RegistroEnergia> mediciones = regDao.listarTodos();
                                for (RegistroEnergia m : mediciones) {
                                    System.out.println("Fecha:" + m.getFechaHora() + " | Gen:" + m.getEnergiaGenerada() + " | Con:" + m.getEnergiaConsumida());
                                }
                                break;
                        }
                    } while (opcReg != 3);
                    break;

                // ==============================================
                // OPCIÓN 5: BALANCE ENERGÉTICO
                // ==============================================
                case 5:
                    System.out.println("\n===== CÁLCULO DE BALANCE ENERGÉTICO =====");
                    RegistroEnergiaDAO registroDao = new RegistroEnergiaDAO();
                    CalculadoraBalance calculadora = new CalculadoraBalance();

                    try {
                        List<RegistroEnergia> datos = registroDao.listarTodos();
                        if (datos.isEmpty()) {
                            System.out.println("⚠️ No hay datos para calcular.");
                            break;
                        }

                        double total = calculadora.calcularBalanceTotal(datos);
                        String analisis = calculadora.interpretarBalance(total);

                        System.out.println("\n📊 RESULTADO:");
                        System.out.println("Balance Total: " + String.format("%.2f", total) + " kWh");
                        System.out.println("📌 ANÁLISIS: " + analisis);

                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                    break;

                // ==============================================
                // OPCIÓN 6: GENERACIÓN DE REPORTES
                // ==============================================
                case 6:
                    System.out.println("\n===== GENERADOR DE REPORTES =====");
                    GeneradorReporte generador = new GeneradorReporte();
                    RegistroEnergiaDAO daoReporte = new RegistroEnergiaDAO();

                    System.out.print("Ingrese periodo (ej: Junio-2026): ");
                    String periodo = scanner.nextLine();

                    List<RegistroEnergia> datosReporte = daoReporte.listarTodos();
                    if (datosReporte.isEmpty()) {
                        System.out.println("⚠️ Sin datos para generar.");
                        break;
                    }

                    String textoReporte = generador.generarReporteEficiencia(datosReporte, periodo);
                    System.out.println("\n📄 REPORTE GENERADO:");
                    System.out.println(textoReporte);
                    break;

                // ==============================================
                // OPCIÓN 7: HERRAMIENTAS
                // ==============================================
                case 7:
                    System.out.println("\n===== HERRAMIENTAS =====");
                    System.out.println("1. Ordenar usuarios por nombre");
                    System.out.println("2. Buscar equipo por modelo");
                    System.out.println("3. Volver");
                    System.out.print("Opción: ");
                    int opcHer = scanner.nextInt();
                    scanner.nextLine();

                    if (opcHer == 1) {
                        List<Usuario> lista = usuarioDao.listarTodos();
                        lista.sort(Comparator.comparing(Usuario::getNombre));
                        lista.forEach(u -> System.out.println("- " + u.getNombre()));
                    } else if (opcHer == 2) {
                        System.out.print("Modelo a buscar: ");
                        String mod = scanner.nextLine().toLowerCase();
                        List<EquipoSolar> todos = new EquipoSolarDAO().listarTodos();
                        todos.stream().filter(e -> e.getModelo().toLowerCase().contains(mod)).forEach(e -> System.out.println("✅ Encontrado: " + e.getModelo()));
                    }
                    break;

                // ==============================================
                // OPCIÓN 8: SALIR
                // ==============================================
                case 8:
                    System.out.println("👋 Gracias por usar Provincias Verdes. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("⚠️ Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 8);

        scanner.close();
    }
}