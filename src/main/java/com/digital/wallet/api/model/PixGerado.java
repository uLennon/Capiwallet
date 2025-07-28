package com.digital.wallet.api.model;

import lombok.Data;

@Data
public class PixGerado {
    private Calendario calendario;
    private Devedor devedor;
    private Valor valor;
    private String chave;
    private String solicitacaoPagador;
    private String txid;
    private Integer revisao;
    private String location;
    private String status;
    private String pixCopiaECola;
}
