package com.provinciasverdes.persistencia;

import com.provinciasverdes.modelo.RegistroEnergia;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroEnergiaDAO extends DAOBase<RegistroEnergia> {

    @Override
    public boolean crear(RegistroEnergia registro) {
        String sql = "INSERT INTO registro_energia (fecha_hora, energia_generada, energia_consumida, voltaje, corriente, id_equipo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setTimestamp(1, Timestamp.valueOf(registro.getFechaHora()));
            pst.setDouble(2, registro.getEnergiaGenerada());
            pst.setDouble(3, registro.getEnergiaConsumida());
            pst.setDouble(4, registro.getVoltaje());
            pst.setDouble(5, registro.getCorriente());
            pst.setInt(6, registro.getIdEquipo());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error RegistroEnergia.crear: Equipo no existe.");
            return false;
        }
    }

    @Override
    public RegistroEnergia leer(int id) {
        String sql = "SELECT * FROM registro_energia WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return new RegistroEnergia(
                    rs.getInt("id"),
                    rs.getTimestamp("fecha_hora").toLocalDateTime(),
                    rs.getDouble("energia_generada"),
                    rs.getDouble("energia_consumida"),
                    rs.getDouble("voltaje"),
                    rs.getDouble("corriente"),
                    rs.getInt("id_equipo")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error RegistroEnergia.leer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(RegistroEnergia registro) {
        String sql = "UPDATE registro_energia SET energia_generada=?, energia_consumida=? WHERE id=?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setDouble(1, registro.getEnergiaGenerada());
            pst.setDouble(2, registro.getEnergiaConsumida());
            pst.setInt(3, registro.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error RegistroEnergia.actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(int id) {
        String sql = "DELETE FROM registro_energia WHERE id = ?";
        try (Connection conn = conexionDB.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error RegistroEnergia.borrar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<RegistroEnergia> listarTodos() {
        List<RegistroEnergia> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_energia ORDER BY fecha_hora DESC";
        try (Connection conn = conexionDB.getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                lista.add(new RegistroEnergia(
                    rs.getInt("id"),
                    rs.getTimestamp("fecha_hora").toLocalDateTime(),
                    rs.getDouble("energia_generada"),
                    rs.getDouble("energia_consumida"),
                    rs.getDouble("voltaje"),
                    rs.getDouble("corriente"),
                    rs.getInt("id_equipo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error RegistroEnergia.listarTodos: " + e.getMessage());
        }
        return lista;
    }
}
