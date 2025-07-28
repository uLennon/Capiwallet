package com.digital.wallet.model;

import com.digital.wallet.util.TipoTicket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CompraPresencial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String nomeVendedor;
    private String matricula;
    private Integer quantidadeTicketCafeManha;
    private Integer quantidadeTicketRefeicao;
    private BigDecimal valor;
    private String data;

    public void setData(){
        this.data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public void setValor() {
        this.valor = TipoTicket.calculaValor(TipoTicket.REFEICAO, quantidadeTicketRefeicao).add(TipoTicket.calculaValor(TipoTicket.CAFE, quantidadeTicketCafeManha)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
