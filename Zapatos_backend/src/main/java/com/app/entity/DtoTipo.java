package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTipo implements Serializable {

    private Long id_tipo;

    private String nombre;

    @JsonIgnoreProperties("tipo")
    private List<Habitacion> habitaciones;
}
