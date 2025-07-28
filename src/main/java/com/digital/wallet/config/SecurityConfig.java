package com.digital.wallet.config;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/cadastrar", "/css/**", "/js/**", "/assets/**").permitAll()
                        .requestMatchers("/pix/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/redefinir-senha").permitAll()
                        .requestMatchers("/inicio/**").hasAnyRole("USUARIO", "ADMIN", "TECNICO")
                        .requestMatchers("/tecnico/**").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/imagem").hasAnyRole("USUARIO", "ADMIN", "TECNICO")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureHandler(erroAuthentication())
                        .defaultSuccessUrl("/redirectAfterLogin", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/logout")
                );

        return http.build();
    }

    public AuthenticationFailureHandler erroAuthentication(){
        return new ErroConfig();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return matricula -> {
            Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario não encontrado pela matrícula: " + matricula);
            }

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList()));

            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.getTipoUsuario().name())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}