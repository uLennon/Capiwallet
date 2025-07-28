package com.digital.wallet.api.model;

import lombok.Data;

@Data
public class PixModel {
    private Calendario calendario;
    private Devedor devedor;
    private Valor valor;
    private String chave;
}
