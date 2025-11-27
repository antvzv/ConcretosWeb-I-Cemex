/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.servlets;
import com.mycompany.concretosweb.dto.CementoDetalle;
import com.mycompany.concretosweb.dto.ConcretoDetalle;
import com.mycompany.concretosweb.dto.PedidoRequest;
import com.mycompany.concretosweb.model.ApiResponse;
import com.mycompany.concretosweb.service.PedidoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author anton
 */

@WebServlet(name = "PedidosServlet", urlPatterns = {"/api/pedidos"})
public class PedidosServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        PedidoRequest pr = new PedidoRequest();
        pr.setTipo(param(req, "tipo"));
        pr.setAccion(param(req, "accion"));

        pr.setFecha(param(req, "fecha"));     // YYYY-MM-DD
        pr.setHora(param(req, "hora"));       // HH:mm o HH:mm:ss
        pr.setDireccion(param(req, "direccion"));
        pr.setCp(param(req, "cp"));
        pr.setNombre(param(req, "nombre"));
        pr.setTelefono(param(req, "telefono"));
        pr.setNotas(param(req, "notas"));

        pr.setSubtotal(parseDouble(param(req, "subtotal")));
        pr.setIva(parseDouble(param(req, "iva")));
        pr.setTotal(parseDouble(param(req, "total")));

        String tipo = pr.getTipo() == null ? "" : pr.getTipo().toUpperCase();

        if ("CONCRETO".equals(tipo)) {
            ConcretoDetalle c = new ConcretoDetalle();
            c.setVolumen(parseDouble(param(req, "volumen")));
            c.setFc(param(req, "fc"));
            c.setSlump(param(req, "slump"));
            c.setAgregado(param(req, "agregado"));
            c.setBombeo(param(req, "bombeo"));
            c.setLongitudBomba(parseInt(param(req, "longitud_bomba")));
            c.setCamion(param(req, "camion"));
            c.setVentana(parseInt(param(req, "ventana")));
            c.setAccesoDificil("true".equalsIgnoreCase(param(req, "acceso_dificil")));

            String aditivosCsv = param(req, "aditivos");
            if (aditivosCsv != null && !aditivosCsv.trim().isEmpty()) {
                List<String> aditivos = Arrays.stream(aditivosCsv.split(","))
                        .map(String::trim).filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                c.setAditivos(aditivos);
            }
            pr.setConcreto(c);

        } else if ("CEMENTO".equals(tipo)) {
            CementoDetalle c = new CementoDetalle();
            c.setCantidad(parseInt(param(req, "cantidad")));
            c.setPeso(parseInt(param(req, "peso")));
            c.setTipoCemento(param(req, "tipo_cemento"));
            c.setM2(parseInt(param(req, "m2")));
            c.setEspesor(parseDouble(param(req, "espesor")));
            pr.setCemento(c);
        }

        ApiResponse out;
        int status = HttpServletResponse.SC_OK;

        try {
            PedidoService service = new PedidoService();
            String error = service.validar(pr);
            if (error != null) {
                status = HttpServletResponse.SC_BAD_REQUEST;
                out = new ApiResponse(false, error);
            } else {
                long id = service.guardar(pr);
                boolean cot = "COTIZAR".equalsIgnoreCase(pr.getAccion());
                out = new ApiResponse(true, (cot ? "Cotización recibida." : "Pedido recibido.") + " Folio: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            out = new ApiResponse(false, "Error al guardar: " + e.getMessage());
        }

        resp.setStatus(status);
        try (PrintWriter pw = resp.getWriter()) {
            // JSON simple sin dependencias
            String json = "{\"ok\":" + out.isOk() + ",\"msg\":\"" + escapeJson(out.getMsg()) + "\"}";
            pw.write(json);
        }
    }

    private static String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return (v == null ? null : v.trim());
    }

    private static Integer parseInt(String s) {
        try { return (s == null || s.isEmpty()) ? null : Integer.valueOf(s); }
        catch (Exception e) { return null; }
    }

    private static Double parseDouble(String s) {
        try { return (s == null || s.isEmpty()) ? null : Double.valueOf(s); }
        catch (Exception e) { return null; }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\r","\\r").replace("\n","\\n");
    }
}
