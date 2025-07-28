package com.digital.wallet.strategy;

import com.digital.wallet.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class RedirecionaPadrao implements RedirecionamentoStrategy{
    public boolean aceita(Usuario usuario) {
        return true;
    }

    public String redirecionar() {
        return "redirect:/inicio";
    }
}
