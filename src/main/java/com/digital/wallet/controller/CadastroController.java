package com.digital.wallet.controller;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CadastroController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public CadastroController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/cadastrar")
    public String cadastro(){
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.criarUsuario(usuario);
        return "solicitado";
    }
}
