package com.app.controller;

import com.app.Errores.ResourceNotFoundException;
import com.app.entity.DtoTipo;
import com.app.entity.Tipo;
import com.app.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/api")
@RequiredArgsConstructor
public class TipoController {

    private final ModelMapper modelMapper;

    private final TipoRepository tipoRepository;

    private DtoTipo entityToDto(Tipo entity) {
        return modelMapper.map(entity, DtoTipo.class);
    }

    @GetMapping("/tipo")
    public List<?> getTipos() {
        return tipoRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/tipo/{id}")
    public ResponseEntity<DtoTipo> buscarTipo(@PathVariable Long id) {
        return tipoRepository.findById(id)
                .map(this::entityToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tipo")
    public Tipo createHabitacion(@RequestBody Tipo habitacion){
        return tipoRepository.save(habitacion);
    }

    @PutMapping("/tipo/{id}")
    public Tipo updatePosicion(@PathVariable Long id, @RequestBody Tipo tipo) {
        Tipo modificacion = tipoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Zona not found with id: " + id));
        modificacion.setNombre(tipo.getNombre());
        return tipoRepository.save(modificacion);
    }

    @DeleteMapping("/tipo/{id}")
    public void deleteHabitacion(@PathVariable Long id) {
        tipoRepository.deleteById(id);
    }



}
