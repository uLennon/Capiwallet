package com.digital.wallet.services;

import com.digital.wallet.model.Carteira;
import com.digital.wallet.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class LoginService {
    private final UsuarioService usuarioService;
    private final CarteiraService carteiraService;

    public LoginService(UsuarioService usuarioService, CarteiraService carteiraService) {
        this.usuarioService = usuarioService;
        this.carteiraService = carteiraService;
    }

    public void configurarSessaoUsuario(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(userDetails.getUsername());
        Carteira carteira = carteiraService.buscarCarteira(usuario);
        session.setAttribute("usuario", usuario);
        session.setAttribute("carteira", carteira);
    }

    public void processarRedefinicaoSenha(Boolean status, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute("linkSuccesso", "Um link para redefinir sua senha foi enviado para o seu e-mail.");
        } else {
            redirectAttributes.addFlashAttribute("linkErro", "Ocorreu um erro ao tentar enviar o e-mail de redefinição de senha. Por favor, tente novamente mais tarde.");
        }
    }

    public String verificarRedirecionamento(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            return "redirect:/inicio";
        } else {
            return "redirect:/login";
        }
    }
}
