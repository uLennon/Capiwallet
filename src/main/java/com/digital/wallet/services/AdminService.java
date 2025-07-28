package com.digital.wallet.services;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.util.Identificador;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class AdminService {

    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final ExecutorService executorService;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UsuarioService usuarioService, EmailService emailService, ExecutorService executorService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
        this.executorService = executorService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> buscarUsuariosIndefinidos() {
        return usuarioService.buscarUsuariosPorTipo(Identificador.INDEFINIDO);
    }

    public void aceitarUsuario(String matricula) {
        usuarioService.aceitarUsuario(matricula);
    }

    public boolean rejeitarUsuario(String matricula, String motivo) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
            executorService.submit(() -> emailService.sendEmail(usuario.getEmail(), "Rejeição", motivo));
            usuarioService.rejeitarUsuario(matricula);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void editarUsuario(String nome, String email, String senha, String matricula, boolean tornarTecnico) {
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
        usuario.setNome(nome);
        usuario.setEmail(email);

        if (senha != null && !senha.isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(senha));
        }

        if (tornarTecnico) {
            usuario.setTipoUsuario(Identificador.TECNICO);
        }

        usuarioService.atualizarUsuario(usuario);
    }

    public void buscarUsuarioParaEdicao(String matricula, RedirectAttributes redirectAttributes) {
        Usuario user = usuarioService.buscarUsuarioPorMatricula(matricula);
        if (user != null) {
            redirectAttributes.addFlashAttribute("usuarioEncontrado", user);
            redirectAttributes.addFlashAttribute("image", user.getDataImage());
        } else {
            redirectAttributes.addFlashAttribute("erro", "Usuário não encontrado.");
        }
        redirectAttributes.addFlashAttribute("activeSection", "alterarUsuario");
    }
}
