package com.digital.wallet.strategy;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.util.Identificador;
import org.springframework.stereotype.Component;

@Component
public class RedirecionaTecnico implements RedirecionamentoStrategy{
        @Override
        public boolean aceita(Usuario usuario) {
            return usuario.getTipoUsuario() == Identificador.TECNICO;
        }

        @Override
        public String redirecionar() {
            return "redirect:/tecnico";
        }
}

