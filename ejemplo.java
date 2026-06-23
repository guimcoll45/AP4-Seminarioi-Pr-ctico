public interface IExportable {
    void exportarPDF();
    void exportarCSV();
}

public class Reporte implements IExportable {
    @Override
    public void exportarPDF() { /* Lógica de generación */ }
    @Override
    public void exportarCSV() { /* Lógica de generación */ }
}
