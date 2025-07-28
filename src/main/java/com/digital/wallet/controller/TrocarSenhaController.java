package com.digital.wallet.controller;


import com.digital.wallet.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrocarSenhaController {

        private final UsuarioService usuarioService;
        private final PasswordEncoder passwordEncoder;

        public TrocarSenhaController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
            this.usuarioService = usuarioService;
            this.passwordEncoder = passwordEncoder;
        }

        @GetMapping("/redefinir-senha")
        public String showChangePasswordForm(String token, Model model) {
            model.addAttribute("token", token);
            return "redefinir";
        }

        @PostMapping("/redefinir-senha")
        public String changePassword(@RequestParam("token") String token, @RequestParam("novaSenha") String novaSenha, @RequestParam("ConfirmaSenha") String confirmarSenha, Model model) {
            boolean sucesso = false;
            if(novaSenha.equals(confirmarSenha)) {
                sucesso = usuarioService.trocarSenha(token,passwordEncoder.encode(novaSenha));
            }
            if (sucesso) {
                model.addAttribute("message", "Sua senha foi alterada com sucesso.");
            } else {
                model.addAttribute("error", "Ocorreu um erro ao alterar a senha.");
            }
            return "redefinir";
        }
}



