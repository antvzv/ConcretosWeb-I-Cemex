/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.model;

/**
 *
 * @author anton
 */

public class Cotizacion {
    private String nombre;
    private String telefono;
    private String email;
    private String codigoPostal;
    private String ciudad;
    private String estado;
    private String producto;
    private boolean acepto;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public boolean isAcepto() { return acepto; }
    public void setAcepto(boolean acepto) { this.acepto = acepto; }
}
