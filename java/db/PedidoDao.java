/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.concretosweb.db;
import com.mycompany.concretosweb.db.Conexion_BD;
import com.mycompany.concretosweb.dto.CementoDetalle;
import com.mycompany.concretosweb.dto.ConcretoDetalle;
import com.mycompany.concretosweb.dto.PedidoRequest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 *
 * @author anton
 */

public class PedidoDao {

    private static Time toSqlTime(String hhmmOrHhmmss) {
        if (hhmmOrHhmmss == null || hhmmOrHhmmss.trim().isEmpty()) return null;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("H:mm[:ss]");
            LocalTime lt = LocalTime.parse(hhmmOrHhmmss.trim(), fmt);
            return Time.valueOf(lt);
        } catch (Exception ex) {
            try { return Time.valueOf(hhmmOrHhmmss.trim() + ":00"); }
            catch (Exception ignored) { return null; }
        }
    }

    public long insertarPedido(PedidoRequest req) throws SQLException {
        final String sql =
            "INSERT INTO pedidos " +
            "(tipo, accion, estado, programado_fecha, programado_hora, " +
            " direccion, cp, nombre_contacto, telefono_contacto, notas, " +
            " subtotal, iva, total) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection cn = Conexion_BD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, req.getTipo());
            ps.setString(2, req.getAccion());
            ps.setString(3, "NUEVO");

            if (req.getFecha() == null || req.getFecha().trim().isEmpty()) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(req.getFecha().trim())); // YYYY-MM-DD
            }

            Time t = toSqlTime(req.getHora());
            if (t == null) ps.setNull(5, Types.TIME); else ps.setTime(5, t);

            ps.setString(6,  req.getDireccion());
            ps.setString(7,  req.getCp());
            ps.setString(8,  req.getNombre());
            ps.setString(9,  req.getTelefono());
            ps.setString(10, req.getNotas());

            if (req.getSubtotal() != null) ps.setBigDecimal(11, java.math.BigDecimal.valueOf(req.getSubtotal())); else ps.setNull(11, Types.DECIMAL);
            if (req.getIva()      != null) ps.setBigDecimal(12, java.math.BigDecimal.valueOf(req.getIva()));      else ps.setNull(12, Types.DECIMAL);
            if (req.getTotal()    != null) ps.setBigDecimal(13, java.math.BigDecimal.valueOf(req.getTotal()));    else ps.setNull(13, Types.DECIMAL);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("No se generó ID de pedido");
    }

    public void insertarDetalleConcreto(long pedidoId, ConcretoDetalle c) throws SQLException {
        final String sql =
            "INSERT INTO pedido_concreto " +
            "(pedido_id, volumen_m3, fc, slump, agregado_max, bombeo, " +
            " longitud_bomba_m, camion, ventana_min, acceso_dificil) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection cn = Conexion_BD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, pedidoId);
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(c.getVolumen()));
            ps.setString(3, c.getFc());
            ps.setString(4, c.getSlump());
            ps.setString(5, c.getAgregado());
            ps.setString(6, c.getBombeo());
            if (c.getLongitudBomba() != null) ps.setInt(7, c.getLongitudBomba()); else ps.setNull(7, Types.INTEGER);
            ps.setString(8, c.getCamion());
            if (c.getVentana() != null) ps.setInt(9, c.getVentana()); else ps.setNull(9, Types.INTEGER);
            ps.setBoolean(10, Boolean.TRUE.equals(c.getAccesoDificil()));

            ps.executeUpdate();
        }
    }

    public void insertarAditivos(long pedidoId, List<String> aditivos) throws SQLException {
        if (aditivos == null || aditivos.isEmpty()) return;
        final String sql = "INSERT INTO pedido_concreto_aditivo (pedido_id, aditivo) VALUES (?,?)";

        try (Connection cn = Conexion_BD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            for (String a : aditivos) {
                if (a == null) continue;
                String v = a.trim();
                if (v.isEmpty()) continue;
                ps.setLong(1, pedidoId);
                ps.setString(2, v);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void insertarDetalleCemento(long pedidoId, CementoDetalle c) throws SQLException {
        final String sql =
            "INSERT INTO pedido_cemento " +
            "(pedido_id, cantidad, peso_kg, tipo_cemento, m2_aplanado, espesor_cm) " +
            "VALUES (?,?,?,?,?,?)";

        try (Connection cn = Conexion_BD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, pedidoId);
            ps.setInt(2, c.getCantidad());
            ps.setInt(3, c.getPeso());
            ps.setString(4, c.getTipoCemento());
            if (c.getM2() != null) ps.setInt(5, c.getM2()); else ps.setNull(5, Types.INTEGER);
            if (c.getEspesor() != null) ps.setBigDecimal(6, java.math.BigDecimal.valueOf(c.getEspesor())); else ps.setNull(6, Types.DECIMAL);

            ps.executeUpdate();
        }
    }
}