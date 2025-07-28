package com.digital.wallet.auxiliar;

import com.digital.wallet.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessaoAuxiliar {

    public Usuario getUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuario");
    }
}
