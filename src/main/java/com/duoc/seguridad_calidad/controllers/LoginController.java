package com.duoc.seguridad_calidad.controllers;

import com.duoc.seguridad_calidad.security.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private TokenStore tokenStore;

    public LoginController (TokenStore tokenStore) {
        super();
        this.tokenStore = tokenStore;
    }


    @GetMapping("/login")
    public String showLoginPage(@RequestParam(name = "email", required = false, defaultValue = "testing waters") String email, Model model) {

        return "login";

    }

}
