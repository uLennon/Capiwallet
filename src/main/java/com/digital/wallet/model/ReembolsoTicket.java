package com.digital.wallet.model;

import com.digital.wallet.util.TipoTicket;
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
public class ReembolsoTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTicket tipoTicket;

    private String matricula;
    private int quantidadeTicket;

    public ReembolsoTicket(String matricula, int quantidadeTicket, TipoTicket tipoTicket) {
        this.matricula = matricula;
        this.quantidadeTicket = quantidadeTicket;
        this.tipoTicket = tipoTicket;
    }
}
