package com.digital.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Integer quantidadeTicketCafeManha;
    private Integer quantidadeTicketRefeicao;
    private BigDecimal valor;
    private String txid;
    private String status;
    private String pixCopiaECola;
    private String data;

    public void setData(){
        this.data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

}
