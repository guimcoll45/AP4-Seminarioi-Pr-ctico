// Tratamiento polimórfico mediante interfaz
IExportable reporte = new ReporteMensual();
reporte.exportarPDF(); // Ejecuta la lógica propia de ReporteMensual

reporte = new ReporteAnual();
reporte.exportarPDF(); // Ejecuta la lógica propia de ReporteAnual

// Tratamiento polimórfico mediante herencia
EntidadBase entidad1 = new Usuario();
entidad1.mostrarDatos(); // Muestra información de usuario

EntidadBase entidad2 = new EquipoSolar();
entidad2.mostrarDatos(); // Muestra información del equipo
