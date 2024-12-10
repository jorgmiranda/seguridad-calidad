package com.duoc.seguridad_calidad.dto;

public class Ingrediente {
    private Long id;
    private String nombreIngrediente;

    
    public Ingrediente(Long id, String nombreIngrediente) {
        this.id = id;
        this.nombreIngrediente = nombreIngrediente;
    }
    public Ingrediente() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreIngrediente() {
        return nombreIngrediente;
    }
    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }
    
    
}
