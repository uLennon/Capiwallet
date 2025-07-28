package com.digital.wallet.api.model;

import lombok.Data;

@Data
public class Token {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String scope;

}
