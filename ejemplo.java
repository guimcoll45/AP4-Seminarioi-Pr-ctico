// Opciones fijas del menú principal
String[] opcionesMenu = {
    "Gestión de Usuarios",
    "Gestión de Ubicaciones",
    "Gestión de Equipos",
    "Registrar Medición",
    "Ver Balance Energético",
    "Exportar Reportes",
    "Salir"
};

// Recorrido
for (int i = 0; i < opcionesMenu.length; i++) {
    System.out.println((i+1) + ". " + opcionesMenu[i]);
}
