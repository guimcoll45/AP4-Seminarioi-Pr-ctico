package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroEnergiaDAO extends DAOBase<RegistroEnergia> {

    @Override
    public List<RegistroEnergia> listarTodos() {
        List<RegistroEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_energia ORDER BY fecha_hora DESC";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar registros: " + e.getMessage());
        }
        return lista;
    }

    public List<RegistroEnergia> listarPorEquipo(int idEquipo) {
        List<RegistroEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_energia WHERE id_equipo = ? ORDER BY fecha_hora DESC";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar por equipo: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public RegistroEnergia leer(int id) {
        RegistroEnergia r = null;
        String sql = "SELECT * FROM registro_energia WHERE id = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r = mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error leer registro: " + e.getMessage());
        }
        return r;
    }

    @Override
    public boolean crear(RegistroEnergia r) {
        String sql = "INSERT INTO registro_energia (id_equipo, fecha_hora, energia_generada, energia_consumida, voltaje, corriente, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getIdEquipo());
            ps.setTimestamp(2, Timestamp.valueOf(r.getFechaHora()));
            ps.setDouble(3, r.getEnergiaGenerada());
            ps.setDouble(4, r.getEnergiaConsumida());
            ps.setDouble(5, r.getVoltaje());
            ps.setDouble(6, r.getCorriente());
            ps.setDouble(7, r.getBalance());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error crear registro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(RegistroEnergia r) {
        return false;
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM registro_energia WHERE id = ?";
        try (Connection conn = ConexionDB.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error borrar registro: " + e.getMessage());
            return false;
        }
    }

    private RegistroEnergia mapear(ResultSet rs) throws SQLException {
        RegistroEnergia r = new RegistroEnergia();
        r.setId(rs.getInt("id"));
        r.setIdEquipo(rs.getInt("id_equipo"));
        r.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        r.setEnergiaGenerada(rs.getDouble("energia_generada"));
        r.setEnergiaConsumida(rs.getDouble("energia_consumida"));
        r.setVoltaje(rs.getDouble("voltaje"));
        r.setCorriente(rs.getDouble("corriente"));
        r.setBalance(rs.getDouble("balance"));
        return r;
    }
}
