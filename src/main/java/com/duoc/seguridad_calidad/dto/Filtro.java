package com.duoc.seguridad_calidad.dto;

public class Filtro {
    private String nombre;
    private String paisOrigen;
    private String dificultad;
    private String tipoCocina;
    private String ingredientes;
    public Filtro() {
    }
    public Filtro(String nombre, String paisOrigen, String dificultad, String tipoCocina, String ingredientes) {
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.dificultad = dificultad;
        this.tipoCocina = tipoCocina;
        this.ingredientes = ingredientes;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPaisOrigen() {
        return paisOrigen;
    }
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    public String getDificultad() {
        return dificultad;
    }
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
    public String getTipoCocina() {
        return tipoCocina;
    }
    public void setTipoCocina(String tipoCocina) {
        this.tipoCocina = tipoCocina;
    }
    public String getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(String ingrediente) {
        this.ingredientes = ingrediente;
    }
    
}
