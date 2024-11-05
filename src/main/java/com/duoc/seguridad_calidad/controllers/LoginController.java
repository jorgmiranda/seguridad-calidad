package com.duoc.seguridad_calidad.controllers;

import com.duoc.seguridad_calidad.security.TokenStore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
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
    public String ingresar(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        System.out.println("EN INGRESAR" + email);
        System.out.println("EN INGRESAR" + password);

        String url = "http://localhost:8081/login";

        final var restTemplate = new RestTemplate();

        // Agregar parámetros a la URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("user", email)
                .queryParam("encryptedPass", password);

//        String token = tokenStore.getToken();

//        System.out.println("EN INGRESAR TOKEN" + token);

        // Crear los encabezados y añadir el token
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Realizar la petición con el token en el encabezado y los parámetros en la URL
        ResponseEntity response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);

        System.out.println("Response codigo: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
//        System.out.println("Response: " + response);

        if (response.getStatusCode().value() == 200) {
            tokenStore.setToken(response.getBody().toString());
            System.out.println("token: " + tokenStore.getToken());


        }else {
            System.out.println("ERROR EN INGRESAR MALA SUERTE PAPITO");
        }


        // ahora se le manda a la url que necesitamos para demostrar que se usa el token




        return "holamundo";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/testing")
//    public String testingMethod2() {
    public String testingMethod(@RequestParam(name = "correo")String email) {
//        System.out.println("EN LOGIN DE PRUEBA" );
        System.out.println("EN LOGIN DE PRUEBA " + email);
        return "holamundo";
    }

    @GetMapping("/testing")
    public String testingMethod() {
        System.out.println("EN LOGIN DE PRUEBA");

        return "holamundo";
    }
}
