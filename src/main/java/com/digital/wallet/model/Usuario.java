package com.digital.wallet.model;

import com.digital.wallet.util.Identificador;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Identificador tipoUsuario;

    @OneToOne
    @JoinColumn(name = "carteira_id")
    private Carteira carteira;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] dataImage;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] qrCodeImage;

    public String getDataImage(){
       return dataImage != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(dataImage) : null;
    }

    public byte[] byteImage() {
        return dataImage;
    }
    public String getQRCodeImage(){
        return dataImage != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(qrCodeImage) : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return matricula;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
