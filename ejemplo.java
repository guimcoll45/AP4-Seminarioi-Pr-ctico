// Interfaz que define un comportamiento
public interface IExportable {
    void exportarPDF();
    void exportarCSV();
}

// Clase que implementa la interfaz
public class Reporte implements IExportable {
    @Override
    public void exportarPDF() {
        System.out.println("Generando reporte en formato PDF...");
        // Lógica interna de generación oculta al usuario
    }

    @Override
    public void exportarCSV() {
        System.out.println("Generando reporte en formato CSV...");
    }
}
