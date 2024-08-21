package com.app.controller.Security;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
@RequestMapping("/method")
//SE PUEDE DECLARAR DE ESTA MANERA, PERO EXISTE OTRA FORMA DE ESTABLECER LOS PERMISOS, DESDE EL SecurityConfig
//@PreAuthorize("denyAll()")
public class testAuthController {

    @GetMapping("/get")
//    @PreAuthorize("hasAuthority('READ')")
    public String holaGet() {
        return "Hola Mundo";
    }

    @PostMapping("/post")
//    @PreAuthorize("hasAuthority('DELETE')")
    public String holaPost(){
        return "Hola Mundo -POST";
    }


    @PutMapping("/put")
//    @PreAuthorize("hasAuthority('UPDATE')")
    public String holaPut(){
        return "Hola Mundo -PUT";
    }

    @DeleteMapping("/delete")
    public String holaDelete(){
        return "Hola Mundo -DELETE";
    }


    @PatchMapping("/patch")
    public String holaPatch(){
        return "Hola Mundo -PATCH";
    }

}
