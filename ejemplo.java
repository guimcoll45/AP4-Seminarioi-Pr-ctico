// Mismo método, comportamiento diferente
EntidadBase entidad1 = new Usuario();
EntidadBase entidad2 = new EquipoSolar();

entidad1.mostrarDatos(); // Muestra datos de usuario
entidad2.mostrarDatos(); // Muestra datos de equipo

// Uso con interfaz
IExportable reporte = new ReporteMensual();
reporte.exportarPDF();
reporte = new ReporteAnual();
reporte.exportarPDF();
