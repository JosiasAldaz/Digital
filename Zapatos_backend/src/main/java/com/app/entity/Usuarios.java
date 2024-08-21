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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_Non_Expired")
    private boolean accountNonExpired;
    @Column(name = "account_Non_Locked")
    private boolean accountNonLocked;
    @Column(name = "credentials_Non_Expired")
    private boolean credentialsNonExpired;

    //RELACIÓN DE ROLES Y USUARIO
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_del_usuario" , joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<RoleEntity> roles = new HashSet<>();

    // RELACIÓN DE USUARIO Y USUARIO
    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

}
