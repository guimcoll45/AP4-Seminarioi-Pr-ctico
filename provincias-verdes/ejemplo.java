public void guardarReporte(List<IExportable> datos, String ruta) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
        bw.write(datos.get(0).generarEncabezado());
        for (IExportable item : datos) {
            bw.write(item.generarCuerpo());
        }
    } catch (IOException e) {
        System.out.println("Error al guardar archivo: " + e.getMessage());
    }
}
