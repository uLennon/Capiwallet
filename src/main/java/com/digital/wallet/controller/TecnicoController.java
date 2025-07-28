package com.digital.wallet.controller;

import com.digital.wallet.auxiliar.SessaoAuxiliar;
import com.digital.wallet.auxiliar.UsuarioAuxiliar;
import com.digital.wallet.services.TecnicoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TecnicoController {

    private final SessaoAuxiliar sessaoAuxiliar;
    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService, SessaoAuxiliar sessaoAuxiliar) {
        this.tecnicoService = tecnicoService;
        this.sessaoAuxiliar = sessaoAuxiliar;
    }

    @GetMapping
    public String paginaTecnico() {
        return "tecnico";
    }

    @PostMapping
    public ModelAndView buscarUsuario(@RequestParam("busca") String busca, @RequestParam("tipoBusca") String tipoBusca) {
        return tecnicoService.buscarUsuario(busca, tipoBusca);
    }

    @PostMapping("/descontarCafe")
    public String descontarCafe(@RequestParam("matricula") String matricula, HttpSession session, RedirectAttributes attrs) {
        tecnicoService.descontarTicket(matricula, sessaoAuxiliar.getUsuario(session), "CAFE", attrs);
        return "redirect:/tecnico";
    }

    @PostMapping("/descontarRefeicao")
    public String descontarRefeicao(@RequestParam("matricula") String matricula, HttpSession session, RedirectAttributes attrs) {
        tecnicoService.descontarTicket(matricula, sessaoAuxiliar.getUsuario(session), "REFEICAO", attrs);
        return "redirect:/tecnico";
    }

    @PostMapping("/solicitarReembolso")
    public String solicitarReembolso(@RequestParam String matricula, @RequestParam String tipoTicket, @RequestParam int quantidade) {
        tecnicoService.reembolsarTicket(matricula, tipoTicket, quantidade);
        return "redirect:/tecnico";
    }

    @GetMapping("/compra")
    public String paginaCompra(Model model) {
        model.addAttribute("comprasPresenciais", tecnicoService.buscarTodasComprasPresenciais());
        return "presencial";
    }

    @PostMapping("/compra/exportarPDF")
    public void exportarPDF(HttpServletResponse response) {
        tecnicoService.exportarPDF(response);
    }

    @PostMapping("/compra/exportarExcel")
    public void exportarExcel(HttpServletResponse response) {
        tecnicoService.exportarExcel(response);
    }

    @PostMapping("/compra")
    public String realizarCompra(@RequestParam String matricula, @RequestParam int almoco, @RequestParam int cafeManha, HttpSession session) {
        tecnicoService.realizarCompra(matricula, almoco, cafeManha, sessaoAuxiliar.getUsuario(session));
        return "redirect:/tecnico/compra";
    }

    @GetMapping("/comprar")
    public ResponseEntity<UsuarioAuxiliar> buscarUsuarioJson(@RequestParam("matricula") String matricula) {
        return ResponseEntity.ok(tecnicoService.montarUsuarioAuxiliar(matricula));
    }
}
