package com.digital.wallet.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Valor {
    private BigDecimal original;
    private static final BigDecimal VALOR_CAFE = BigDecimal.valueOf(0.70);
    private static final BigDecimal VALOR_ALMOCO = BigDecimal.valueOf(1.45);

    public void calculaValor(Integer cafe, Integer almoco) {
        this.original = VALOR_CAFE.multiply(BigDecimal.valueOf(cafe))
                .add(VALOR_ALMOCO.multiply(BigDecimal.valueOf(almoco)))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
