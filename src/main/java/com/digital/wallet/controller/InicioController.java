package com.digital.wallet.controller;

import com.digital.wallet.auxiliar.SessaoAuxiliar;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.services.PerfilService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InicioController {

    private final PerfilService perfilService;
    private final SessaoAuxiliar sessaoAuxiliar;

    public InicioController(PerfilService perfilService, SessaoAuxiliar sessaoAuxiliar) {
        this.perfilService = perfilService;
        this.sessaoAuxiliar = sessaoAuxiliar;
    }

    @GetMapping("/inicio")
    public String inicio(Model model, HttpSession session) {
        Usuario usuario = sessaoAuxiliar.getUsuario(session);
        model.addAttribute("image", usuario.getDataImage());
        model.addAttribute("usuario", usuario);
        model.addAttribute("carteira", usuario.getCarteira());
        return "index";
    }

    @PostMapping("/inicio/senha")
    public String atualizarSenha(@RequestParam("senha-atual") String senhaAtual,
                                 @RequestParam("nova-senha") String novaSenha,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        perfilService.atualizarSenhaUsuario(sessaoAuxiliar.getUsuario(session), senhaAtual, novaSenha, redirectAttributes);
        return "redirect:/inicio";
    }

    @PostMapping("/inicio/email")
    public String atualizarEmail(@RequestParam("novo-email") String novoEmail,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {
        perfilService.atualizarEmailUsuario(sessaoAuxiliar.getUsuario(session), novoEmail, redirectAttributes);
        return "redirect:/inicio";
    }

    @PostMapping("/imagem")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        perfilService.atualizarImagemUsuario(sessaoAuxiliar.getUsuario(session), file, redirectAttributes);
        return "redirect:/inicio";
    }

    @PostMapping("/inicio/historicoCompra")
    public String mostraHistorico(HttpSession session, RedirectAttributes redirectAttributes) {
        perfilService.carregarHistoricoVendas(sessaoAuxiliar.getUsuario(session), redirectAttributes);
        return "redirect:/inicio";
    }
}
