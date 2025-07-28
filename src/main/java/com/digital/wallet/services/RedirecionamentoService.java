package com.digital.wallet.services;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.strategy.RedirecionamentoStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedirecionamentoService {

    private final List<RedirecionamentoStrategy> estrategias;

    public RedirecionamentoService(List<RedirecionamentoStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public String redirecionar(Usuario usuario) {
        return estrategias.stream()
                .filter(e -> e.aceita(usuario))
                .findFirst()
                .map(RedirecionamentoStrategy::redirecionar)
                .orElse("redirect:/inicio");
    }
}
