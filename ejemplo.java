public void exportarUbicaciones(List<Ubicacion> lista, String rutaArchivo) {
    try (BufferedWriter bw = Files.newBufferedWriter(Path.of(rutaArchivo), StandardOpenOption.CREATE)) {
        bw.write("ID;Provincia;Ciudad;Direccion;CP\n");
        for (Ubicacion u : lista) {
            bw.write(u.paraArchivo() + "\n");
        }
        System.out.println("✅ Archivo exportado correctamente.");
    } catch (IOException e) {
        System.err.println("❌ Error al exportar: " + e.getMessage());
    }
}
