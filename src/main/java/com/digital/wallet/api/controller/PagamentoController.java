package com.digital.wallet.api.controller;

import com.digital.wallet.api.model.PixGerado;
import com.digital.wallet.api.service.PagamentoService;
import com.digital.wallet.model.Carteira;
import com.digital.wallet.model.Compra;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.CarteiraRepository;
import com.digital.wallet.repositories.CompraRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final CompraRepository compraRepository;
    private final CarteiraRepository carteiraRepository;

    public PagamentoController(PagamentoService pagamentoService, CompraRepository compraRepository,CarteiraRepository carteiraRepository) {
            this.pagamentoService = pagamentoService;
            this.compraRepository = compraRepository;
            this.carteiraRepository = carteiraRepository;
    }

    @PostMapping("/inicio/criaPagamento")
    public String geraCobranca(@RequestParam(name = "cafeManha", defaultValue = "0") int cafeManha, @RequestParam(name = "refeicao", defaultValue = "0") int refeicao, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        try{
            PixGerado pixGerado = pagamentoService.armazenarCompra(cafeManha, refeicao, usuario);
            redirectAttributes.addFlashAttribute("total", pixGerado.getValor().getOriginal());
            redirectAttributes.addFlashAttribute("showModal",true);
            redirectAttributes.addFlashAttribute("dadoQRCode",pixGerado.getPixCopiaECola());
            redirectAttributes.addFlashAttribute("pixCopiaECola",pixGerado.getPixCopiaECola());
            redirectAttributes.addFlashAttribute("activeSection", "comprar");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("erroGerarPagamento", "Ops! Não conseguimos gerar a cobrança agora. Por favor, tente novamente mais tarde ou fale com o nosso suporte se precisar de ajuda.");
            redirectAttributes.addFlashAttribute("activeSection", "comprar");
        }
        return "redirect:/inicio";
    }

    @PostMapping("/inicio/pagarPix")
    public String concluirPagamento(@RequestParam("pixCopiaECola") String pixCopiaECola, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            executor.submit(() -> {
                try {
                    pagamentoService.pagarPix(pixCopiaECola);
                    Usuario usuario = (Usuario) session.getAttribute("usuario");
                    Carteira carteira = (Carteira) session.getAttribute("carteira");
                    Compra compra = compraRepository.findCompraPendenteByUsuarioId(usuario.getId())
                            .orElseThrow(() -> new RuntimeException("Txid não encontrado para o usuário: " + usuario.getNome())).get(0);
                    String status = pagamentoService.consultarPixPago(compra.getTxid(), pagamentoService.pegarToken());
                    if (status.equals("CONCLUIDA")) {
                        compra.setStatus(status);
                        compraRepository.save(compra);
                        carteira.adicionarTicket(compra.getQuantidadeTicketCafeManha(), compra.getQuantidadeTicketRefeicao());
                        carteiraRepository.save(carteira);
                    }
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("erroPagamento", "Ops! Algo deu errado com o pagamento. Tente mais tarde ou fale com o nosso suporte para resolver o problema.");
                    redirectAttributes.addFlashAttribute("activeSection", "comprar");
                }
            });
        }

        redirectAttributes.addFlashAttribute("activeSection", "comprar");
        return "redirect:/inicio";
    }
}