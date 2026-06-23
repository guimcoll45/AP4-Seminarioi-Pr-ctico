public class Usuario extends EntidadBase {
    private String nombre;
    private String correo;
    private String contrasena;
    private String perfil;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    // Resto de getters y setters
}
