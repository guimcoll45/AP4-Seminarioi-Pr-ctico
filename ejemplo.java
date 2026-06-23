// Constructor vacío
public Usuario() { }

// Constructor parametrizado
public Usuario(String nombre, String correo, String contrasena, String perfil) {
    this.nombre = nombre;
    this.correo = correo;
    this.contrasena = contrasena;
    this.perfil = perfil;
}

// Instanciación y uso
Usuario usuario = new Usuario(
    "Juan Perez",
    "juan@mail.com",
    "1234",
    "Final"
);
