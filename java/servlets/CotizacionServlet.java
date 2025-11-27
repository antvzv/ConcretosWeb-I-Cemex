package com.mycompany.concretosweb.servlets;

import com.mycompany.concretosweb.db.Conexion_BD;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "CotizacionServlet", urlPatterns = {"/api/cotizaciones"})
public class CotizacionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String nombre = req.getParameter("nombre");
        String telefono = req.getParameter("telefono");
        String email = req.getParameter("email");
        String codigoPostal = req.getParameter("codigo_postal");
        String ciudad = req.getParameter("ciudad");
        String estado = req.getParameter("estado");
        String producto = req.getParameter("producto");
        String aceptoStr = req.getParameter("acepto");
        boolean acepto = "on".equalsIgnoreCase(aceptoStr) || "true".equalsIgnoreCase(aceptoStr);

        String sql = "INSERT INTO cotizaciones "
                   + "(nombre, telefono, email, codigo_postal, ciudad, estado, producto, acepto) "
                   + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection cn = Conexion_BD.getConnection()) {
            // Diagnóstico: imprime base de datos, puerto y usuario
            var diag = cn.createStatement().executeQuery(
                "SELECT DATABASE() db, @@port prt, CURRENT_USER() usr");
            if (diag.next()) {
                System.out.println("DB=" + diag.getString("db")
                    + " PORT=" + diag.getString("prt")
                    + " USER=" + diag.getString("usr"));
            }

            try (PreparedStatement ps = cn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nombre);
                ps.setString(2, telefono);
                ps.setString(3, email);
                ps.setString(4, codigoPostal);
                ps.setString(5, ciudad);
                ps.setString(6, estado);
                ps.setString(7, producto);
                ps.setBoolean(8, acepto);

                int rows = ps.executeUpdate();
                long id = -1;
                try (var rs = ps.getGeneratedKeys()) {
                    if (rs.next()) id = rs.getLong(1);
                }

                System.out.println("INSERT rows=" + rows + " id=" + id);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write("{\"ok\":true,\"id\":" + id + ",\"msg\":\"Cotización registrada\"}");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write("{\"ok\":false,\"msg\":\"" + ex.getMessage().replace("\"","\\\"") + "\"}");
        }
    }
}
