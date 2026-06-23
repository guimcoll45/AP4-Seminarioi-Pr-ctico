public Usuario buscarPorCorreo(String correo, ArrayList<Usuario> lista) {
    for (Usuario u : lista) {
        if (u.getCorreo().equalsIgnoreCase(correo)) {
            return u;
        }
    }
    return null;
}
