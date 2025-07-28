package com.digital.wallet.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAuxiliar {

    private String nome;
    private String matricula;
    private byte[] foto;

    public String getFoto(){
        return foto != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(foto) : null;
    }
}
