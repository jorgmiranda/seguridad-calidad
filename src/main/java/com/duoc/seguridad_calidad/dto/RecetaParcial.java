package com.duoc.seguridad_calidad.dto;

import java.util.Date;

public class RecetaParcial {
    private int id;
    private String nombre;
    private String tipoDeCocina;
    private String dificultadElaboracion;
    private String tiempoCoccion;
    private String urlImagen;
    private int popularidad;
    private Date fechaCreacion;

    public RecetaParcial() {
    }

    public RecetaParcial(int id, String nombre, String tipoDeCocina, String dificultadElaboracion, String tiempoCoccion,
            String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDeCocina = tipoDeCocina;
        this.dificultadElaboracion = dificultadElaboracion;
        this.tiempoCoccion = tiempoCoccion;
        this.urlImagen = urlImagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDeCocina() {
        return tipoDeCocina;
    }

    public void setTipoDeCocina(String tipoDeCocina) {
        this.tipoDeCocina = tipoDeCocina;
    }

    public String getDificultadElaboracion() {
        return dificultadElaboracion;
    }

    public void setDificultadElaboracion(String dificultadElaboracion) {
        this.dificultadElaboracion = dificultadElaboracion;
    }

    public String getTiempoCoccion() {
        return tiempoCoccion;
    }

    public void setTiempoCoccion(String tiempoCoccion) {
        this.tiempoCoccion = tiempoCoccion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(int popularidad) {
        this.popularidad = popularidad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
}
