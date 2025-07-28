package com.digital.wallet.strategy;

import com.digital.wallet.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public interface RedirecionamentoStrategy {
    boolean aceita(Usuario usuario);
    String redirecionar();
}
