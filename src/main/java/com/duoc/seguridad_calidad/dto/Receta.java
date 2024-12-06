package com.duoc.seguridad_calidad.dto;

import java.util.Date;
import java.util.List;

public class Receta {
    private Long id;
    private String nombre;
    private String tipoDeCocina;
    private String paisDeOrigen;
    private String dificultadElaboracion;
    private String instruccionesPreparacion;
    private String urlVideo;
    private Integer tiempoCoccion;
    private String urlImagen;
    private Date fechaCreacion;
    private Integer popularidad;
    private List<Ingrediente> recetaIngredientes;

    

    public Receta() {
    }

    

    

    

    public Receta(Long id, String nombre, String tipoDeCocina, String paisDeOrigen, String dificultadElaboracion,
            String instruccionesPreparacion, String urlVideo, Integer tiempoCoccion, String urlImagen,
            Date fechaCreacion, Integer popularidad, List<Ingrediente> recetaIngredientes) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDeCocina = tipoDeCocina;
        this.paisDeOrigen = paisDeOrigen;
        this.dificultadElaboracion = dificultadElaboracion;
        this.instruccionesPreparacion = instruccionesPreparacion;
        this.urlVideo = urlVideo;
        this.tiempoCoccion = tiempoCoccion;
        this.urlImagen = urlImagen;
        this.fechaCreacion = fechaCreacion;
        this.popularidad = popularidad;
        this.recetaIngredientes = recetaIngredientes;
    }







    public Long getId() {
        return id;
    }



    public void setId(Long id) {
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



    public Date getFechaCreacion() {
        return fechaCreacion;
    }



    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }



    public Integer getPopularidad() {
        return popularidad;
    }



    public void setPopularidad(Integer popularidad) {
        this.popularidad = popularidad;
    }



    public List<Ingrediente> getRecetaIngredientes() {
        return recetaIngredientes;
    }



    public void setRecetaIngredientes(List<Ingrediente> recetaIngredientes) {
        this.recetaIngredientes = recetaIngredientes;
    }



    public String getUrlVideo() {
        return urlVideo;
    }



    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }



    // public static class Ingrediente {
    //     private String nombreIngrediente;

    //     public String getNombreIngrediente() {
    //         return nombreIngrediente;
    //     }

    //     public void setNombreIngrediente(String nombreIngrediente) {
    //         this.nombreIngrediente = nombreIngrediente;
    //     }

        
    // }
}
