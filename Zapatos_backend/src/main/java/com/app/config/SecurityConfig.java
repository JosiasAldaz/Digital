package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .httpBasic(withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http ->{
                    //PARA DEFINIR LA AUTORIZACIÓN USAMOS EL ARCHIVO userDetailsService
                    //Este endpoint es público
                    http.requestMatchers("/auth/**","/api/**","/swagger-ui/**").permitAll();
                    //Este end point es privado
                    //CON EL COMANDO hasAnyAuthority se deniega el acceso con varios permisos
//                    http.requestMatchers(HttpMethod.POST,"/method/post").hasAnyAuthority( "CREATE","DELETE");
                    //CON EL COMANDO hasAuthority se deniega el acceso con 1 solo autority
//                    http.requestMatchers(HttpMethod.POST,"/method/post").hasAuthority("DELETE");
//                    http.requestMatchers(HttpMethod.DELETE,"/method/delete").hasRole( "W");
//                    http.requestMatchers(HttpMethod.PATCH,"/method/patch").hasRole("INVENTARIO");
//                    http.requestMatchers(HttpMethod.DELETE,"/method/delete").hasAnyRole( "ADMIN","CLIENTE");
                    //Configurar los demás end point -- NO ESPECIFICADOS
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                .build();
//
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //$2a$10$2yzT1WnPYXEW5auvGsRc1OlQUB8itCXyCrxlim/369o0eHTl9bESu

}
