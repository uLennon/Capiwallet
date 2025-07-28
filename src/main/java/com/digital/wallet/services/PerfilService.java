package com.digital.wallet.services;

import com.digital.wallet.model.CompraPresencial;
import com.digital.wallet.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Service
public class PerfilService {
    private static final long MAX_IMAGE_SIZE = 1 * 1024 * 1024;

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final CompraService compraService;

    public PerfilService(UsuarioService usuarioService, PasswordEncoder passwordEncoder, CompraService compraService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.compraService = compraService;
    }

    public void atualizarSenhaUsuario(Usuario usuario, String senhaAtual, String novaSenha, RedirectAttributes redirectAttributes) {
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            redirectAttributes.addFlashAttribute("senhaErro", "Senha atual incorreta.");
        } else {
            usuario.setSenha(passwordEncoder.encode(novaSenha));
            usuarioService.atualizarUsuario(usuario);
            redirectAttributes.addFlashAttribute("senhaSuccesso", "Senha atualizada com sucesso.");
        }
        marcarSecaoAtivaUsuario(redirectAttributes);
    }

    public void atualizarEmailUsuario(Usuario usuario, String novoEmail, RedirectAttributes redirectAttributes) {
        usuario.setEmail(novoEmail);
        usuarioService.atualizarUsuario(usuario);
        redirectAttributes.addFlashAttribute("emailSucesso", "E-mail atualizado com sucesso.");
        marcarSecaoAtivaUsuario(redirectAttributes);
    }

    public void atualizarImagemUsuario(Usuario usuario, MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (file.getSize() > MAX_IMAGE_SIZE) {
                redirectAttributes.addFlashAttribute("imagemErro", "A imagem é muito grande!");
            } else {
                usuario.setDataImage(file.getBytes());
                usuarioService.atualizarUsuario(usuario);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("imagemErro", "Erro ao processar imagem.");
        }
        marcarSecaoAtivaUsuario(redirectAttributes);
    }

    public void carregarHistoricoVendas(Usuario usuario, RedirectAttributes redirectAttributes) {
        List<CompraPresencial> compras = compraService.hitoricoComprasRealizadas(usuario.getId(), usuario.getMatricula());
        if (compras.isEmpty()) {
            redirectAttributes.addFlashAttribute("historicoVazio", "Não há compras realizadas");
        } else {
            redirectAttributes.addFlashAttribute("compras", compras);
        }
        marcarSecaoAtivaUsuario(redirectAttributes);
        redirectAttributes.addFlashAttribute("exibirModal", true);
    }

    private void marcarSecaoAtivaUsuario(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeSection", "usuario");
    }
}
