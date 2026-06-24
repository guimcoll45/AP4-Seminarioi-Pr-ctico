import java.io.*;

public void exportarRegistros(List<RegistroEnergia> lista, String ruta) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
        bw.write("Fecha,Hora,Generado_kWh,Consumido_kWh,Balance_kWh\n");
        for (RegistroEnergia r : lista) {
            bw.write(String.format("%s,%.2f,%.2f,%.2f%n",
                r.getFechaHora(),
                r.getGenerado(),
                r.getConsumido(),
                r.calcularBalance()
            ));
        }
        System.out.println("✅ Archivo exportado correctamente en: " + ruta);

    } catch (IOException e) {
        System.err.println("❌ Error al escribir archivo: " + e.getMessage());
    }
}
