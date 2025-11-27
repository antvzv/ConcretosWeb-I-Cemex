/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.resources;
import com.mycompany.concretosweb.dto.CementoDetalle;
import com.mycompany.concretosweb.dto.ConcretoDetalle;
import com.mycompany.concretosweb.dto.PedidoRequest;
import com.mycompany.concretosweb.model.ApiResponse;
import com.mycompany.concretosweb.service.PedidoService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author anton
 */

@Path("/pedidos")
public class PedidosResource {

    private final PedidoService service = new PedidoService();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPedido(
            @FormParam("tipo") String tipo,
            @FormParam("accion") String accion,

            // comunes
            @FormParam("fecha") String fecha,
            @FormParam("hora") String hora,
            @FormParam("direccion") String direccion,
            @FormParam("cp") String cp,
            @FormParam("nombre") String nombre,
            @FormParam("telefono") String telefono,
            @FormParam("notas") String notas,

            // opcional costos del front
            @FormParam("subtotal") Double subtotal,
            @FormParam("iva") Double iva,
            @FormParam("total") Double total,

            // concreto
            @FormParam("volumen") Double volumen,
            @FormParam("fc") String fc,
            @FormParam("slump") String slump,
            @FormParam("agregado") String agregado,
            @FormParam("aditivos") String aditivosCsv,
            @FormParam("bombeo") String bombeo,
            @FormParam("longitud_bomba") Integer longitudBomba,
            @FormParam("camion") String camion,
            @FormParam("ventana") Integer ventana,
            @FormParam("acceso_dificil") String accesoDificil,

            // cemento
            @FormParam("cantidad") Integer cantidad,
            @FormParam("peso") Integer peso,
            @FormParam("tipo_cemento") String tipoCemento,
            @FormParam("m2") Integer m2,
            @FormParam("espesor") Double espesor
    ){
        PedidoRequest req = new PedidoRequest();
        req.setTipo(tipo);
        req.setAccion(accion);
        req.setFecha(fecha);
        req.setHora(hora);
        req.setDireccion(direccion);
        req.setCp(cp);
        req.setNombre(nombre);
        req.setTelefono(telefono);
        req.setNotas(notas);

        req.setSubtotal(subtotal);
        req.setIva(iva);
        req.setTotal(total);

        if ("CONCRETO".equalsIgnoreCase(tipo)) {
            ConcretoDetalle c = new ConcretoDetalle();
            c.setVolumen(volumen);
            c.setFc(fc);
            c.setSlump(slump);
            c.setAgregado(agregado);
            c.setBombeo(bombeo);
            c.setLongitudBomba(longitudBomba);
            c.setCamion(camion);
            c.setVentana(ventana);
            c.setAccesoDificil("true".equalsIgnoreCase(accesoDificil));

            if (aditivosCsv != null && !aditivosCsv.trim().isEmpty()) {
                List<String> aditivos = Arrays.stream(aditivosCsv.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                c.setAditivos(aditivos);
            }

            req.setConcreto(c);

        } else if ("CEMENTO".equalsIgnoreCase(tipo)) {
            CementoDetalle c = new CementoDetalle();
            c.setCantidad(cantidad);
            c.setPeso(peso);
            c.setTipoCemento(tipoCemento);
            c.setM2(m2);
            c.setEspesor(espesor);
            req.setCemento(c);
        }

        String error = service.validar(req);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, error)).build();
        }

        long id;
        try {
            id = service.guardar(req);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError()
                    .entity(new ApiResponse(false, "Error al guardar: " + ex.getMessage()))
                    .build();
        }

        String okMsg = "COTIZAR".equalsIgnoreCase(req.getAccion()) ? "Cotización recibida." : "Pedido recibido.";
        okMsg += " Folio: " + id;
        return Response.ok(new ApiResponse(true, okMsg)).build();
    }
}