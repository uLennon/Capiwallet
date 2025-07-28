package com.digital.wallet.controller;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String exibirPaginaAdmin(Model model) {
        List<Usuario> usuarios = adminService.buscarUsuariosIndefinidos();
        model.addAttribute("usuarios", usuarios);
        return "admin";
    }

    @PostMapping("/admin/aceitar")
    public String aceitarUsuario(@RequestParam("matricula") String matricula) {
        adminService.aceitarUsuario(matricula);
        return "redirect:/admin";
    }

    @PostMapping("/admin/rejeitar")
    public String rejeitarUsuario(@RequestParam("matricula") String matricula,
                                  @RequestParam("motivo") String motivo) {
        boolean sucesso = adminService.rejeitarUsuario(matricula, motivo);
        return sucesso ? "redirect:/admin" : "redirect:/admin?error";
    }

    @PostMapping("/admin/editar")
    public String editarUsuario(@RequestParam("novo-nome") String nome,
                                @RequestParam("novo-email") String email,
                                @RequestParam("nova-senha") String senha,
                                @RequestParam("matricula") String matricula,
                                @RequestParam(value = "aceitar", defaultValue = "false") boolean tornarTecnico,
                                RedirectAttributes redirectAttributes) {
        adminService.editarUsuario(nome, email, senha, matricula, tornarTecnico);
        redirectAttributes.addFlashAttribute("activeSection", "alterarUsuario");
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String buscarUsuario(@RequestParam("busca") String matricula,
                                RedirectAttributes redirectAttributes) {
        adminService.buscarUsuarioParaEdicao(matricula, redirectAttributes);
        return "redirect:/admin";
    }
}
