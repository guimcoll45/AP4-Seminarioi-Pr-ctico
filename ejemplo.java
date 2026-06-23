// Superclase
public abstract class EntidadBase {
    protected int id;
    public abstract void mostrarDatos();
}

// Subclase que hereda
public class EquipoSolar extends EntidadBase {
    private String marca;
    private double potencia;
    private String tipo;
    // ... resto de atributos y métodos
}
