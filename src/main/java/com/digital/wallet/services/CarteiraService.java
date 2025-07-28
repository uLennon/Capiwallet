package com.digital.wallet.services;

import com.digital.wallet.model.Carteira;
import com.digital.wallet.model.ReembolsoTicket;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.CarteiraRepository;
import com.digital.wallet.repositories.ReembolsoTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final ReembolsoTicketRepository reembolsoTicketRepository;

    public CarteiraService(CarteiraRepository carteiraRepository, ReembolsoTicketRepository reembolsoTicketRepository) {
        this.carteiraRepository = carteiraRepository;
        this.reembolsoTicketRepository = reembolsoTicketRepository;
    }

    public void salvarCarteira(Carteira carteira){
        carteiraRepository.save(carteira);
    }

    public Carteira buscarCarteira(Usuario usuario){
       return carteiraRepository.findById(usuario.getCarteira().getId()).orElseThrow(()-> new RuntimeException("Erro ao buscar carteira"));
    }

    public ReembolsoTicket buscarReembolso(Long reembolsoId){
        return reembolsoTicketRepository.findById(reembolsoId).orElseThrow();
    }

    public List<ReembolsoTicket>buscarListaReembolsos() {
        return reembolsoTicketRepository.findAll();
    }

}
