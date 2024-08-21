package com.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;
    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "permisos_del_usuario" ,
            joinColumns = @JoinColumn(name = "fk_id_rol",referencedColumnName = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "fk_id_permision",referencedColumnName = "id_permision"))
    private Set<PermisionEntity> permisos = new HashSet<>();
}
