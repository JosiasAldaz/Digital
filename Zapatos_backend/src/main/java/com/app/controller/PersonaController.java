package com.app.controller;

import com.app.Errores.ResourceNotFoundException;
import com.app.entity.Persona;
import com.app.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/persona")
    public Iterable<Persona> getPersona() {
        return personaRepository.findAll();
    }

    @PutMapping("/persona/{id}")
    public Persona updatePersona(@PathVariable Long id, @RequestBody Persona persona) {
        Persona modificada = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrador con el id: " + id));
        modificada.setNombre(persona.getNombre());
        modificada.setApellido(persona.getApellido());
        modificada.setLista_de_usuarios(persona.getLista_de_usuarios());
        return personaRepository.save(modificada);
    }
}
