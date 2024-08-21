package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoHabitaciones implements Serializable {

    private Long id_habitacion;

    private int numeroHabitacion;
    private String descripcion;
    private String estado;
    private double precioHabitacion;

    @JsonIgnoreProperties("habitaciones")
    private Tipo tipo;
}
