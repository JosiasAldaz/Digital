package com.app.repository;

import com.app.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios>findUserByUsername(String username);

    //ESTA ES LA MANERA DE CREAR CONSULTAS MODIFICADAS EN NUESTRA BASE
//    @Query("SELECT u FROM  Usuarios u WHERE u.username = ?")
//    Optional<Usuarios>findUser(String username);
}
