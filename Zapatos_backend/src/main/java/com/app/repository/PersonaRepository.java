package com.app.repository;

import com.app.entity.Persona;
import com.app.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona>findPersonaByCedula(String cedula);
}
