package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroEnergiaDAO extends DAOBase {

    private static final String TABLA = "registro_energia";

    // ✅ Método obligatorio: crear nuevo registro
    @Override
    public boolean crear(Object objeto) {
        if (!(objeto instanceof RegistroEnergia)) return false;
        RegistroEnergia registro = (RegistroEnergia) objeto;

        String sql = "INSERT INTO " + TABLA +
            " (id_equipo, fecha_hora, voltaje, corriente, energia_generada, energia_consumida, balance) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, registro.getIdEquipo());
            pstmt.setTimestamp(2, Timestamp.valueOf(registro.getFecha_hora()));
            pstmt.setDouble(3, registro.getVoltaje());
            pstmt.setDouble(4, registro.getCorriente());
            pstmt.setDouble(5, registro.getEnergiaGenerada());
            pstmt.setDouble(6, registro.getEnergiaConsumida());
            pstmt.setDouble(7, registro.getBalance());

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) registro.setId(rs.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Error al crear registro: " + e.getMessage());
            return false;
        }
    }

    // ✅ Método obligatorio: actualizar registro
    @Override
    public boolean actualizar(Object objeto) {
        if (!(objeto instanceof RegistroEnergia)) return false;
        RegistroEnergia registro = (RegistroEnergia) objeto;

        String sql = "UPDATE " + TABLA +
            " SET id_equipo = ?, fecha_hora = ?, voltaje = ?, corriente = ?, energia_generada = ?, energia_consumida = ?, balance = ? " +
            " WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registro.getIdEquipo());
            pstmt.setTimestamp(2, Timestamp.valueOf(registro.getFecha_hora()));
            pstmt.setDouble(3, registro.getVoltaje());
            pstmt.setDouble(4, registro.getCorriente());
            pstmt.setDouble(5, registro.getEnergiaGenerada());
            pstmt.setDouble(6, registro.getEnergiaConsumida());
            pstmt.setDouble(7, registro.getBalance());
            pstmt.setInt(8, registro.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar registro: " + e.getMessage());
            return false;
        }
    }

    // ✅ Método obligatorio: borrar registro
    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM " + TABLA + " WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al borrar registro: " + e.getMessage());
            return false;
        }
    }

    // ✅ Método obligatorio: leer registro por ID
    @Override
    public Object leer(int id) {
        String sql = "SELECT * FROM " + TABLA + " WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    RegistroEnergia r = new RegistroEnergia();
                    r.setId(rs.getInt("id"));
                    r.setIdEquipo(rs.getInt("id_equipo"));
                    r.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    r.setVoltaje(rs.getDouble("voltaje"));
                    r.setCorriente(rs.getDouble("corriente"));
                    r.setEnergia_generada(rs.getDouble("energia_generada"));
                    r.setEnergia_consumida(rs.getDouble("energia_consumida"));
                    r.setBalance(rs.getDouble("balance"));
                    return r;
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al leer registro: " + e.getMessage());
        }
        return null;
    }

    // ✅ Método obligatorio de DAOBase: devuelve lista genérica
    @Override
    public List<Object> listarTodos() {
        List<Object> lista = new ArrayList<>();
        for (RegistroEnergia r : obtenerTodos()) {
            lista.add(r);
        }
        return lista;
    }

    // ✅ Método específico: devuelve lista directa de RegistroEnergia (lo usa el menú)
    public List<RegistroEnergia> obtenerTodos() {
        List<RegistroEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " ORDER BY fecha_hora DESC";

        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RegistroEnergia r = new RegistroEnergia();
                r.setId(rs.getInt("id"));
                r.setIdEquipo(rs.getInt("id_equipo"));
                r.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                r.setVoltaje(rs.getDouble("voltaje"));
                r.setCorriente(rs.getDouble("corriente"));
                r.setEnergia_generada(rs.getDouble("energia_generada"));
                r.setEnergia_consumida(rs.getDouble("energia_consumida"));
                r.setBalance(rs.getDouble("balance"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar todos los registros: " + e.getMessage());
        }
        return lista;
    }

    // ✅ Método auxiliar para guardar
    public boolean guardar(RegistroEnergia registro) {
        return crear(registro);
    }

    // ✅ Método usado en el menú principal
    public List<RegistroEnergia> listarPorEquipo(int idEquipo) {
        List<RegistroEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " WHERE id_equipo = ? ORDER BY fecha_hora DESC";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RegistroEnergia r = new RegistroEnergia();
                    r.setId(rs.getInt("id"));
                    r.setIdEquipo(rs.getInt("id_equipo"));
                    r.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    r.setVoltaje(rs.getDouble("voltaje"));
                    r.setCorriente(rs.getDouble("corriente"));
                    r.setEnergia_generada(rs.getDouble("energia_generada"));
                    r.setEnergia_consumida(rs.getDouble("energia_consumida"));
                    r.setBalance(rs.getDouble("balance"));
                    lista.add(r);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar registros por equipo: " + e.getMessage());
        }
        return lista;
    }
}