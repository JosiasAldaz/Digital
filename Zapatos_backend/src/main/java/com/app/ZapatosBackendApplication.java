package com.app;

import com.app.entity.*;
import com.app.repository.PersonaRepository;
import com.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ZapatosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZapatosBackendApplication.class, args);
	}

		@Bean
		CommandLineRunner init(UserRepository userRepository, PersonaRepository personaRepository){
			return args -> {
				/*PERMISO DE CREAR*/
				PermisionEntity createPermission = PermisionEntity.builder()
						.name("CREATE")
						.build();
				/*PERMISO DE LECTURA*/
				PermisionEntity readPermission = PermisionEntity.builder()
						.name("READ")
						.build();
				/*PERMISO DE ACTUALIZAR*/
				PermisionEntity updatePermission = PermisionEntity.builder()
						.name("UPDATE")
						.build();
				/*PERMISO DE ELIMINAR*/
				PermisionEntity deletePermission = PermisionEntity.builder()
						.name("DELETE")
						.build();
				/**/
				PermisionEntity refactorPermission = PermisionEntity.builder()
						.name("REFACTOR")
						.build();

				/*CREAR LOS ROLES*/
				/*ROL DE ADMINISTRADOR*/
				RoleEntity rolAdmin = RoleEntity.builder()
						.roleEnum(RoleEnum.ADMIN)
						.permisos(Set.of(createPermission,readPermission,updatePermission,deletePermission))
						.build();

				/*ROL DE CLIENTE*/
				RoleEntity clienteAdmin = RoleEntity.builder()
						.roleEnum(RoleEnum.CLIENTE)
						.permisos(Set.of(readPermission))
						.build();

				/*ROL DE INVENTARIO*/
				RoleEntity inventarioAdmin = RoleEntity.builder()
						.roleEnum(RoleEnum.INVENTARIO)
						.permisos(Set.of(createPermission,readPermission,updatePermission))
						.build();
				//CREAR PERSONAS
				Persona persona1 = Persona.builder()
						.nombre("Josias")
						.apellido("Aldaz")
						.cedula("0704658624")
						.build();

				Persona persona2 = Persona.builder()
						.nombre("Jose")
						.apellido("Farez")
						.cedula("0704859632")
						.build();

				Persona persona3 = Persona.builder()
						.nombre("Santiago")
						.apellido("Guaman")
						.cedula("0123659877")
						.build();

				Persona persona4 = Persona.builder()
						.nombre("Victor")
						.apellido("Aldaz")
						.cedula("0704658625")
						.build();
				personaRepository.saveAll(List.of(persona1, persona2, persona3, persona4));
				/*CREAR USUARIOS*/
				Usuarios usuarioFull = Usuarios.builder()
						.username("Josias777")
						.password("$2a$10$2yzT1WnPYXEW5auvGsRc1OlQUB8itCXyCrxlim/369o0eHTl9bESu")
						.isEnabled(true)
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.roles(Set.of(rolAdmin,clienteAdmin,inventarioAdmin))
						.persona(persona1)
						.build();
				/*CREAR USUARIOS*/
				Usuarios usuarioCliente = Usuarios.builder()
						.username("Santiago777")
						.password("$2a$10$2yzT1WnPYXEW5auvGsRc1OlQUB8itCXyCrxlim/369o0eHTl9bESu")
						.isEnabled(true)
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.roles(Set.of(clienteAdmin))
						.persona(persona3)
						.build();
				/*CREAR USUARIOS*/
				Usuarios usuarioInventario = Usuarios.builder()
						.username("Jose777")
						.password("$2a$10$2yzT1WnPYXEW5auvGsRc1OlQUB8itCXyCrxlim/369o0eHTl9bESu")
						.isEnabled(true)
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.roles(Set.of(inventarioAdmin))
						.persona(persona2)
						.build();
				/*CREAR USUARIOS*/
				Usuarios usuarioAdmin = Usuarios.builder()
						.username("Victor777")
						.password("$2a$10$2yzT1WnPYXEW5auvGsRc1OlQUB8itCXyCrxlim/369o0eHTl9bESu")
						.isEnabled(true)
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.roles(Set.of(clienteAdmin))
						.persona(persona4)
						.build();

				userRepository.saveAll(List.of(usuarioAdmin,usuarioInventario,usuarioCliente,usuarioFull));

			};
		}
}
