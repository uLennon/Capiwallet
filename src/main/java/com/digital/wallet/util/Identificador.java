package com.digital.wallet.util;

import lombok.Data;


public enum Identificador {
    TECNICO(0), USUARIO(1), ADMIN(2), INDEFINIDO(3);

    private final int identificador;


    // Construtor do enum
    Identificador(int identificador) {
        this.identificador = identificador;
    }

    // Getter para o identificador
    public int getIdentificador() {
        return identificador;
    }

}
