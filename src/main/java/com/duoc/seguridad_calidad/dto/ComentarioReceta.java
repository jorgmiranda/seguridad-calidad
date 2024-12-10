package com.duoc.seguridad_calidad.dto;

public class ComentarioReceta {
    private Long id;
    private Long idReceta;
    private Long id_usuario;
    private String comentario;
    private boolean esPublico;
    private int calificacion;

    public ComentarioReceta() {
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isEsPublico() {
        return esPublico;
    }

    public void setEsPublico(boolean esPublico) {
        this.esPublico = esPublico;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.id_usuario = idUsuario;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }
}
