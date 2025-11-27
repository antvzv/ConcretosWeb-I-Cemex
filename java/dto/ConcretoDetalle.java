/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concretosweb.dto;
import java.util.List;

/**
 *
 * @author anton
 */

public class ConcretoDetalle {
    private Double volumen;
    private String fc;
    private String slump;
    private String agregado;
    private String bombeo;
    private Integer longitudBomba;
    private String camion;
    private Integer ventana;
    private Boolean accesoDificil;

    private List<String> aditivos;

    public ConcretoDetalle() {}

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getSlump() {
        return slump;
    }

    public void setSlump(String slump) {
        this.slump = slump;
    }

    public String getAgregado() {
        return agregado;
    }

    public void setAgregado(String agregado) {
        this.agregado = agregado;
    }

    public String getBombeo() {
        return bombeo;
    }

    public void setBombeo(String bombeo) {
        this.bombeo = bombeo;
    }

    public Integer getLongitudBomba() {
        return longitudBomba;
    }

    public void setLongitudBomba(Integer longitudBomba) {
        this.longitudBomba = longitudBomba;
    }

    public String getCamion() {
        return camion;
    }

    public void setCamion(String camion) {
        this.camion = camion;
    }

    public Integer getVentana() {
        return ventana;
    }

    public void setVentana(Integer ventana) {
        this.ventana = ventana;
    }

    public Boolean getAccesoDificil() {
        return accesoDificil;
    }

    public void setAccesoDificil(Boolean accesoDificil) {
        this.accesoDificil = accesoDificil;
    }

    public List<String> getAditivos() {
        return aditivos;
    }

    public void setAditivos(List<String> aditivos) {
        this.aditivos = aditivos;
    }
}
