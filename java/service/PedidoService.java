/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.service;
import com.mycompany.concretosweb.db.PedidoDao;
import com.mycompany.concretosweb.dto.CementoDetalle;
import com.mycompany.concretosweb.dto.ConcretoDetalle;
import com.mycompany.concretosweb.dto.PedidoRequest;
import com.mycompany.concretosweb.validation.SimpleValidators;

/**
 *
 * @author anton
 */

public class PedidoService {

    private final PedidoDao dao = new PedidoDao();

    public String validar(PedidoRequest req) {
        if (req == null) return "Solicitud vacía.";
        if (!"CONCRETO".equalsIgnoreCase(req.getTipo()) &&
            !"CEMENTO".equalsIgnoreCase(req.getTipo())) return "Tipo de pedido inválido.";

        if (!"COTIZAR".equalsIgnoreCase(req.getAccion()) &&
            !"PEDIR".equalsIgnoreCase(req.getAccion())) return "Acción inválida.";

        if (!SimpleValidators.notBlank(req.getFecha()))     return "Fecha requerida.";
        if (!SimpleValidators.notBlank(req.getHora()))      return "Hora requerida.";
        if (!SimpleValidators.notBlank(req.getDireccion())) return "Dirección requerida.";
        if (!SimpleValidators.notBlank(req.getCp()))        return "CP requerido.";
        if (!SimpleValidators.notBlank(req.getNombre()))    return "Nombre requerido.";
        if (!SimpleValidators.notBlank(req.getTelefono()))  return "Teléfono requerido.";

        if ("CONCRETO".equalsIgnoreCase(req.getTipo())) {
            ConcretoDetalle c = req.getConcreto();
            if (c == null) return "Detalle de concreto requerido.";
            if (!SimpleValidators.isPositive(c.getVolumen())) return "Volumen inválido.";
            if (!SimpleValidators.notBlank(c.getFc())) return "f’c requerido.";
            if (!SimpleValidators.notBlank(c.getSlump())) return "Slump requerido.";
        } else {
            CementoDetalle c = req.getCemento();
            if (c == null) return "Detalle de cemento requerido.";
            if (!SimpleValidators.isPositiveInt(c.getCantidad())) return "Cantidad de bolsas inválida.";
            if (c.getPeso() == null) return "Peso de bolsa requerido.";
            if (!SimpleValidators.notBlank(c.getTipoCemento())) return "Tipo de cemento requerido.";
        }
        return null;
    }

    public long guardar(PedidoRequest req) throws Exception {
        long id = dao.insertarPedido(req);
        if ("CONCRETO".equalsIgnoreCase(req.getTipo())) {
            dao.insertarDetalleConcreto(id, req.getConcreto());
            dao.insertarAditivos(id, req.getConcreto().getAditivos());
        } else {
            dao.insertarDetalleCemento(id, req.getCemento());
        }
        return id;
    }
}