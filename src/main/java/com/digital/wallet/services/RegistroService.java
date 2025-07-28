package com.digital.wallet.services;

import com.digital.wallet.model.RegistroTicket;
import com.digital.wallet.repositories.RegistroRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;

    public RegistroService(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public void registrarTicket(RegistroTicket ticket){
        registroRepository.save(ticket);
    }
}
