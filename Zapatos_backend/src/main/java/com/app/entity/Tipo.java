package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "tipo")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo;

    private String nombre;

    //@JsonManagedReference
    @ToString.Exclude
    @OneToMany(targetEntity = Habitacion.class, fetch = FetchType.EAGER, mappedBy = "tipo")
    private List<Habitacion> habitaciones;
}


