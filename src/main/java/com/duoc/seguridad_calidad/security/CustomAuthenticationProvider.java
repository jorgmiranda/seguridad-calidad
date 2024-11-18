package com.duoc.seguridad_calidad.security;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.duoc.seguridad_calidad.models.ResponseModel;
import com.duoc.seguridad_calidad.models.UserModel;
import com.google.gson.Gson;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private TokenStore tokenStore;

    public CustomAuthenticationProvider(TokenStore tokenStore) {
        super();
        this.tokenStore = tokenStore;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        System.out.println("Llegué Custom Authentication Provider: ");
        System.out.println("Authentication: " + authentication);

        final String name = authentication.getName();
        System.out.println("Name: " + name);
        final String password = authentication.getCredentials().toString();
        System.out.println("Password: " + password);


        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

    //    requestBody.add("user", name);
    //    requestBody.add("encryptedPass", password);

        System.out.println("Request Body: " + requestBody);

        final var restTemplate = new RestTemplate();
        final var responseEntity = restTemplate.postForEntity("http://localhost:8081/api/login?correo="+name+"&password="+password, requestBody, String.class);

        ResponseModel responseModel = new Gson().fromJson(responseEntity.getBody(), ResponseModel.class);

        JSONObject jsonObject = new JSONObject(responseModel);

        UserModel user = new Gson().fromJson(String.valueOf(jsonObject.getJSONObject("data").getJSONObject("user")), UserModel.class);


        tokenStore.setToken(jsonObject.getJSONObject("data").getString("token"));
        tokenStore.setUserModel(user);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BadCredentialsException("Invalid username or password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(name, password, authorities);

    }

    @Override
    public boolean supports(Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

