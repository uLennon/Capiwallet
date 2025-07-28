package com.digital.wallet.util;

import java.math.BigDecimal;

public enum TipoTicket {
    REFEICAO(1.45), CAFE(0.70);

    private final Double valor;

    TipoTicket(Double valor) {
        this.valor = valor;
    }

    public Double getTipoTicket() {
        return valor;
    }
    public static BigDecimal calculaValor(TipoTicket tipoTicket, Integer quantidade) {
        if (tipoTicket == null || quantidade == null) {
            throw new IllegalArgumentException("Tipo de ticket e quantidade não podem ser nulos");
        }
        return BigDecimal.valueOf(tipoTicket.getTipoTicket()).multiply(BigDecimal.valueOf(quantidade));
    }
}
