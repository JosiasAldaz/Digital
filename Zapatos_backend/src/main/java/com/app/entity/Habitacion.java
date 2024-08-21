package com.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "habitaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"numero_habitacion"})})
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_habitacion;

    @Column(name = "numero_habitacion")
    private int numeroHabitacion;
    private String descripcion;
    private String estado;
    private double precioHabitacion;

    @ManyToOne(targetEntity = Tipo.class)
    //@JsonBackReference
    private Tipo tipo;

}

