import java.util.ArrayList;

// Colección dinámica para almacenar usuarios
ArrayList<Usuario> listaUsuarios = new ArrayList<>();
listaUsuarios.add(new Usuario("Juan", "juan@mail.com", "ADMINISTRADOR"));
listaUsuarios.add(new Usuario("Ana", "ana@mail.com", "RESIDENCIAL"));

// Recorrido
for (Usuario u : listaUsuarios) {
    System.out.println(u.getNombre());
}
