package com.app.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "personas", uniqueConstraints = {@UniqueConstraint(columnNames = {"cedula"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    private String nombre;
    private String apellido;
    @Column(nullable = false)
    private String cedula;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Usuarios> lista_de_usuarios;


}
