package com.duoc.seguridad_calidad.dto;

import java.util.List;

public class CrearReceta {
    private String nombre;
    private String tipoDeCocina;
    private String paisDeOrigen;
    private String dificultadElaboracion;
    private String instruccionesPreparacion;
    private Integer tiempoCoccion;
    private String urlImagen;
    private Integer popularidad;
    private List<String> ingredientes;
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
    public String getPaisDeOrigen() {
        return paisDeOrigen;
    }
    public void setPaisDeOrigen(String paisDeOrigen) {
        this.paisDeOrigen = paisDeOrigen;
    }
    public String getDificultadElaboracion() {
        return dificultadElaboracion;
    }
    public void setDificultadElaboracion(String dificultadElaboracion) {
        this.dificultadElaboracion = dificultadElaboracion;
    }
    public String getInstruccionesPreparacion() {
        return instruccionesPreparacion;
    }
    public void setInstruccionesPreparacion(String instruccionesPreparacion) {
        this.instruccionesPreparacion = instruccionesPreparacion;
    }
    public Integer getTiempoCoccion() {
        return tiempoCoccion;
    }
    public void setTiempoCoccion(Integer tiempoCoccion) {
        this.tiempoCoccion = tiempoCoccion;
    }
    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    public Integer getPopularidad() {
        return popularidad;
    }
    public void setPopularidad(Integer popularidad) {
        this.popularidad = popularidad;
    }
    public List<String> getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    
}
