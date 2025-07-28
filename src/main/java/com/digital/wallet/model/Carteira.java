package com.digital.wallet.model;

import com.digital.wallet.util.TipoTicket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "carteira")
    @JsonIgnore
    private Usuario usuario;

    private int quantidadeTicketRefeicao = 0;
    private int quantidadeTicketCafe = 0;


    public void descontarTicket(String tipo){
        if(tipo.equals("CAFE")){
            quantidadeTicketCafe--;
        }
        if(tipo.equals("REFEICAO")){
            quantidadeTicketRefeicao--;
        }
    }

    public void adicionarTicket(int quantidadeTicketCafe, int quantidadeTicketRefeicao){
        this.quantidadeTicketCafe += quantidadeTicketCafe;
        this.quantidadeTicketRefeicao += quantidadeTicketRefeicao;
    }

    public void reembolsarTicket(int quantidadeTicket, TipoTicket tipoTicket){
        if(tipoTicket.name().equals("CAFE")){
            quantidadeTicketCafe += quantidadeTicket;
        }
        if(tipoTicket.name().equals("ALMOCO")){
            quantidadeTicketRefeicao += quantidadeTicket;
        }
    }
}
