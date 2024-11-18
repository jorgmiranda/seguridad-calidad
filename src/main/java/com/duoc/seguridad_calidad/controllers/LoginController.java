package com.duoc.seguridad_calidad.controllers;

import com.duoc.seguridad_calidad.models.ResponseModel;
import com.duoc.seguridad_calidad.models.UserModel;
import com.duoc.seguridad_calidad.security.TokenStore;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping("/ingresar")
    public String ingresar(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, Model model, RedirectAttributes redirectAttributes) {

        String url = "http://localhost:8081/api/login";

        final var restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("correo", email)
                .queryParam("password", password);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(
                "parameters", headers
        );

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);

        ResponseModel responseModel = new Gson().fromJson(response.getBody(), ResponseModel.class);

        JSONObject jsonObject = new JSONObject(responseModel);

        UserModel user = new Gson().fromJson(String.valueOf(jsonObject.getJSONObject("data").getJSONObject("user")), UserModel.class);

        System.out.println("usuario: " + user.getNombre());

        if (response.getStatusCode().value() == 200) {
            tokenStore.setToken(jsonObject.getJSONObject("data").getString("token"));
            System.out.println("token: " + tokenStore.getToken());
        }

        model.addAttribute("token", tokenStore.getToken());
        model.addAttribute("user", user.getEmail());

        redirectAttributes.addAttribute("token", tokenStore.getToken());
        redirectAttributes.addAttribute("user", user.toString());

        return "redirect:/home";
    }
}
