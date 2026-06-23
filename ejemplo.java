public List<Ubicacion> listarTodos() {
    List<Ubicacion> lista = new ArrayList<>();
    String sql = "SELECT id, direccion, ciudad, provincia, codigo_postal, id_usuario FROM ubicaciones";

    try (Connection conn = ConexionDB.getInstancia().getConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Ubicacion u = new Ubicacion();
            u.setId(rs.getInt("id"));
            u.setProvincia(rs.getString("provincia"));
            u.setCiudad(rs.getString("ciudad"));
            u.setDireccion(rs.getString("direccion"));
            u.setCodigoPostal(rs.getString("codigo_postal"));
            u.setIdUsuario(rs.getObject("id_usuario") != null ? rs.getInt("id_usuario") : null);
            lista.add(u);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al listar ubicaciones: " + e.getMessage());
    }
    return lista;
}
