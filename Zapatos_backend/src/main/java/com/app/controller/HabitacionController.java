package com.app.controller;

import com.app.Errores.ResourceNotFoundException;
import com.app.entity.DtoHabitaciones;
import com.app.entity.Habitacion;
import com.app.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/api")
@RequiredArgsConstructor
public class HabitacionController {

    private final ModelMapper modelMapper;

    private final HabitacionRepository habitacionRepository;

    private DtoHabitaciones entityToDtoHabitaciones(Habitacion habitacion) {
        return modelMapper.map(habitacion, DtoHabitaciones.class);
    }

    @GetMapping("/habitaciones")
    public List<DtoHabitaciones> habitaciones(){
         return habitacionRepository.findAll().stream().map(this::entityToDtoHabitaciones).collect(Collectors.toList());
    }

    @PostMapping("/habitaciones")
    public Habitacion createHabitacion(@RequestBody Habitacion habitacion){
        return habitacionRepository.save(habitacion);
    }

    @PutMapping("/habitaciones/{id}")
    public Habitacion updatePosicion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        Habitacion modificacion = habitacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Zona not found with id: " + id));
        modificacion.setDescripcion(habitacion.getDescripcion());
        modificacion.setNumeroHabitacion(habitacion.getNumeroHabitacion());
        modificacion.setEstado(habitacion.getEstado());
        modificacion.setPrecioHabitacion(habitacion.getPrecioHabitacion());
        modificacion.setTipo(habitacion.getTipo());
        return habitacionRepository.save(modificacion);
    }

    @DeleteMapping("/habitaciones/{id}")
    public void deleteHabitacion(@PathVariable Long id) {
        habitacionRepository.deleteById(id);
    }
}
