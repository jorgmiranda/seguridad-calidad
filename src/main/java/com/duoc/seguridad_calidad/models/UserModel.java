package com.duoc.seguridad_calidad.models;

public class UserModel {

    private long id;

    private String correo;

    private String contrasena;

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return correo;
    }

    public void setEmail( String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return contrasena;
    }

    public void setPassword(String contrasena) {
        this.contrasena = contrasena;
    }

}