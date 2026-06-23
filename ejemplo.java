if (correoIngresado.equals(usuarioRegistrado.getCorreo()) &&
    contrasenaIngresada.equals(usuarioRegistrado.getContrasena())) {
    if (usuarioRegistrado.getPerfil().equals("ADMIN")) {
        mostrarMenuCompleto();
    } else {
        mostrarMenuRestringido();
    }
}
