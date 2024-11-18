package com.duoc.seguridad_calidad.security;

import org.springframework.stereotype.Component;

import com.duoc.seguridad_calidad.models.UserModel;

@Component
public class TokenStore {

    private String token;
    private UserModel UserModel; 

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUserModel() {
        return UserModel;
    }

    public void setUserModel(UserModel userModel) {
        UserModel = userModel;
    }

    
}
