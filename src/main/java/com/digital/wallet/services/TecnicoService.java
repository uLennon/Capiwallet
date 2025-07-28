package com.digital.wallet.services;

import com.digital.wallet.auxiliar.UsuarioAuxiliar;
import com.digital.wallet.model.*;
import com.digital.wallet.repositories.ReembolsoTicketRepository;
import com.digital.wallet.util.TipoTicket;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class TecnicoService {

    private final CompraService compraService;
    private final UsuarioService usuarioService;
    private final CarteiraService carteiraService;
    private final RegistroService registroService;
    private final EmailService emailService;
    private final ReembolsoTicketRepository reembolsoTicketRepository;
    private final ExecutorService executorService;

    public TecnicoService(CompraService compraService,UsuarioService usuarioService, CarteiraService carteiraService,RegistroService registroService, EmailService emailService, ReembolsoTicketRepository reembolsoTicketRepository, ExecutorService executorService) {
        this.compraService = compraService;
        this.usuarioService = usuarioService;
        this.carteiraService = carteiraService;
        this.registroService = registroService;
        this.emailService = emailService;
        this.reembolsoTicketRepository = reembolsoTicketRepository;
        this.executorService = executorService;
    }

    public void descontarTicket(Usuario usuario, Usuario tecnico, String tipo){
        usuario.getCarteira().descontarTicket(tipo);
        carteiraService.salvarCarteira(usuario.getCarteira());
        if(usuario.getCarteira().getQuantidadeTicketCafe() == 1){
            emailService.sendEmail(usuario.getEmail(),"Tickets restantes", "Atenção: Restam apenas 1 ticket "+ tipo + "– Verifique sua carteira e efetue a compra!");
        }
        registrarUsoTicket(tecnico,usuario,tipo);
    }

    public void registrarUsoTicket(Usuario tecnico, Usuario usuario, String refeicao) {
        RegistroTicket ticket = RegistroTicket.builder()
                .tipoTicket(refeicao)
                .nomeTecnico(tecnico.getNome().split(" ")[0])
                .matriculaDiscente(usuario.getMatricula())
                .dataRegistro(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))
                .build();
        registroService.registrarTicket(ticket);

    }
    public void rembolsarTicket(String matricula, int quantidadeTicket, TipoTicket tipoTicket){
        ReembolsoTicket reembolsoTicket = ReembolsoTicket.builder()
                .matricula(matricula)
                .quantidadeTicket(quantidadeTicket)
                .tipoTicket(tipoTicket)
                .build();
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
        Carteira carteira = usuario.getCarteira();
        carteira.reembolsarTicket(quantidadeTicket,tipoTicket);
        carteiraService.salvarCarteira(carteira);
        executorService.submit(()->{
            emailService.sendEmail(usuario.getEmail(),"Reembolso de Ticket","Informamos que o reembolso referente ao ticket específico foi processado com sucesso.\n" + quantidadeTicket +" Ticket: "+tipoTicket.name().toLowerCase());
            System.out.println("email enviado: "+usuario.getEmail());
        });

        reembolsoTicketRepository.save(reembolsoTicket);
    }

    public ModelAndView buscarUsuario(String busca, String tipoBusca) {
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(busca);
        ModelAndView mv = new ModelAndView("tecnico");
        if (usuario != null) {
            mv.addObject("user", usuario);
        } else {
            mv.addObject("erro", "Usuário não encontrado.");
        }
        mv.addObject("tipoBusca", tipoBusca);
        return mv;
    }

    public void descontarTicket(String matricula, Usuario tecnico, String tipo, RedirectAttributes attrs) {
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
        Carteira carteira = usuario.getCarteira();

        if (tipo.equals("CAFE") && carteira.getQuantidadeTicketCafe() <= 0) {
            attrs.addFlashAttribute("erroTicket", "Não há ticket de café para descontar.");
            return;
        }
        if (tipo.equals("REFEICAO") && carteira.getQuantidadeTicketRefeicao() <= 0) {
            attrs.addFlashAttribute("erroTicket", "Não há ticket refeição para descontar.");
            return;
        }

        descontarTicket(usuario, tecnico, tipo);
        attrs.addFlashAttribute("successMessage", tipo.equals("CAFE") ?
                "Ticket café descontado com sucesso." :
                "Ticket Almoço/Jantar descontado com sucesso.");
    }

    public void reembolsarTicket(String matricula, String tipo, int quantidade) {
        TipoTicket ticket = TipoTicket.valueOf(tipo);
        rembolsarTicket(matricula, quantidade, ticket);
    }

    public List<CompraPresencial> buscarTodasComprasPresenciais() {
        return compraService.buscarComprasPresenciais();
    }

    public void exportarPDF(HttpServletResponse response) {
        compraService.exportarPDF(response);
    }

    public void exportarExcel(HttpServletResponse response) {
        compraService.exportarExcel(response);
    }

    public void realizarCompra(String matricula, int almoco, int cafe, Usuario tecnico) {
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
        Carteira carteira = usuario.getCarteira();
        carteira.adicionarTicket(cafe, almoco);
        carteiraService.salvarCarteira(carteira);
        compraService.salvaCompra(matricula, cafe, almoco, tecnico.getNome(), usuario);
    }

    public UsuarioAuxiliar montarUsuarioAuxiliar(String matricula) {
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula(matricula);
        return UsuarioAuxiliar.builder()
                .nome(usuario.getNome())
                .matricula(usuario.getMatricula())
                .foto(usuario.byteImage())
                .build();
    }

}
