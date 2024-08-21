package com.app.controller.Security;

import com.app.config.userDetailsServices;
import com.app.controller.dto.AuthCreateUserRequest;
import com.app.controller.dto.AuthLoginRequest;
import com.app.controller.dto.AuthReponse;
import com.app.controller.dto.RequuestRegisterPersonalizado;
import com.app.entity.Persona;
import com.app.entity.Usuarios;
import com.app.repository.PersonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private userDetailsServices userDetailsService;

    @Autowired
    private PersonaRepository personaRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthReponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthReponse> register(@RequestBody @Valid RequuestRegisterPersonalizado parametro ){
        Persona persona =  parametro.getPersona();
        AuthCreateUserRequest usuario = parametro.getUsuario();
        return new ResponseEntity<>(this.userDetailsService.createUser(usuario,persona), HttpStatus.CREATED);
    }

}
