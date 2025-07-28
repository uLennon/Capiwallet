package com.digital.wallet.controller;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.services.LoginService;
import com.digital.wallet.services.RedirecionamentoService;
import com.digital.wallet.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    private final RedirecionamentoService redirecionamentoService;
    private final LoginService loginService;

    public LoginController(UsuarioService usuarioService, RedirecionamentoService redirecionamentoService, LoginService loginService) {
        this.usuarioService = usuarioService;
        this.redirecionamentoService = redirecionamentoService;
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String paginaInicial(HttpServletRequest request) {
       return loginService.verificarRedirecionamento(request);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/redirectAfterLogin")
    public String redirectAfterLogin(HttpSession session) {
        loginService.configurarSessaoUsuario(session);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        return redirecionamentoService.redirecionar(usuario);
    }

    @PostMapping("/login/redefinirSenha")
    public String redefinirSenha(@RequestParam("email") String email, @RequestParam("matricula") String matricula,  RedirectAttributes redirectAttributes) {
        boolean status = usuarioService.enviarLinkRecuperacao(matricula, email);
        loginService.processarRedefinicaoSenha(status, redirectAttributes);

        return "redirect:/login";
    }
}