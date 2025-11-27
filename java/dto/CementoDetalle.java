/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.dto;

/**
 *
 * @author anton
 */

public class CementoDetalle {
    private Integer cantidad;
    private Integer peso;
    private String  tipoCemento;
    private Integer m2;
    private Double  espesor;

    public CementoDetalle() {}

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getTipoCemento() {
        return tipoCemento;
    }

    public void setTipoCemento(String tipoCemento) {
        this.tipoCemento = tipoCemento;
    }

    public Integer getM2() {
        return m2;
    }

    public void setM2(Integer m2) {
        this.m2 = m2;
    }

    public Double getEspesor() {
        return espesor;
    }

    public void setEspesor(Double espesor) {
        this.espesor = espesor;
    }
}
