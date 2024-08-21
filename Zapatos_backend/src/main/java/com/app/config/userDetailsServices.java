package com.app.config;

import com.app.Errores.ResourceNotFoundException;
import com.app.controller.dto.AuthCreateUserRequest;
import com.app.controller.dto.AuthLoginRequest;
import com.app.controller.dto.AuthReponse;
import com.app.entity.Persona;
import com.app.entity.RoleEntity;
import com.app.entity.Usuarios;
import com.app.repository.PersonaRepository;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class userDetailsServices implements UserDetailsService {

    //OBJETOS NECESARIOS PARA TRABAJAR CON LOS TOKEN Y CON EL CREAR USUARIO
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuarios usuario = userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException("EL USUARIO NO SE ENCUENTRA EN EL SISTEMA"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        usuario.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        usuario.getRoles().stream()
                .flatMap(role -> role.getPermisos().stream())
                .forEach(permisionEntity -> authorityList.add(new SimpleGrantedAuthority(permisionEntity.getName())));
        return new User(usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNonExpired(),
                usuario.isCredentialsNonExpired(),
                usuario.isAccountNonLocked(),
                authorityList);
    }

    public AuthReponse loginUser (AuthLoginRequest authLoginRequest){
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();
        Authentication authentication = this.authenticated(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthReponse authReponse = new AuthReponse(username,"USUARIO LOGEADO CORRECTAMENTE",accessToken,true);
        return authReponse;
    }

    private Authentication authenticated(String username, String password) {

        UserDetails userDetails = this.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("El usuario no existe");

        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("CONTRASEÑA INCORRECTA");
        }

        return new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthReponse createUser(AuthCreateUserRequest authCreate,Persona persona) {
        String username = authCreate.username();
        String password = authCreate.password();
        List<String> roleRequest = authCreate.roleRequest().roleListName();
        Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest)
                .stream().collect(Collectors.toSet());
        if(roleEntitySet.isEmpty()){
            throw new IllegalArgumentException("LOS ROLES INGRESADOS NO EXISTEN");
        }
        personaRepository.save(persona);
        Persona perosna = personaRepository.findPersonaByCedula(persona.getCedula()).orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con la cedula:  " + persona.getCedula()));
            Usuarios usuario = Usuarios.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .roles(roleEntitySet)
                    .isEnabled(true)
                    .accountNonLocked(true)
                    .accountNonExpired(true)
                    .credentialsNonExpired(true)
                    .persona(perosna)
                    .build();
        Usuarios usuarioCreado = userRepository.save(usuario);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usuarioCreado.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        usuarioCreado.getRoles()
                .stream()
                .flatMap(role -> role.getPermisos().stream())
                .forEach(permisionEntity -> authorities.add(new SimpleGrantedAuthority(permisionEntity.getName())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioCreado.getUsername(),usuarioCreado.getPassword(),authorities);
        String accessToken = jwtUtils.createToken(authentication);
        AuthReponse authReponse = new AuthReponse(usuarioCreado.getUsername(),"USUARIO CREADO CORRECTAMENTE", accessToken,true);
        return authReponse;
    }
}
