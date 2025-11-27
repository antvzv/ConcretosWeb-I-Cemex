/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.resources;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author anton
 */

@Path("/jakartaee10")
public class JakartaEE10Resource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "ping Jakarta EE";
    }
}
