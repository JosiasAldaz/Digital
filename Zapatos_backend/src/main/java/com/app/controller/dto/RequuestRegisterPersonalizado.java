package com.app.controller.dto;

import com.app.entity.Persona;
import lombok.Data;

@Data
public class RequuestRegisterPersonalizado {
    Persona persona;
    AuthCreateUserRequest usuario;
}
