package com.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permission")
public class PermisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_permision;

    @Column(unique = true,nullable = false,updatable = false)
    private String name;
}
