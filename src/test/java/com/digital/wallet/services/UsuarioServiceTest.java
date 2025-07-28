package com.digital.wallet.services;

import com.digital.wallet.auxiliar.PasswordResetToken;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.PasswordResetTokenRepository;
import com.digital.wallet.repositories.UsuarioRepository;
import com.digital.wallet.util.Identificador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void buscaUsuarioPelaMatricula(){
        Usuario usuarioMock = Usuario.builder().matricula("1").build();
        Mockito.when(usuarioRepository.findAllByMatricula(Mockito.anyString())).thenReturn(usuarioMock);
        Usuario usuario = usuarioService.buscarUsuarioPorMatricula("1");
        Assertions.assertEquals(usuarioMock.getMatricula(), usuario.getMatricula());

    }

    @Test
    void buscaTodosOsUsuariosPorIdentificador(){
        Mockito.when(usuarioRepository.findByTipoUsuario(Mockito.any(Identificador.class))).thenReturn(List.of(new Usuario()));
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorTipo(Identificador.USUARIO);
        Assertions.assertEquals(usuarios.size(), 1);

    }

    @Test
    void enviaLinkDeRecuperacaoUsandoEmailEMatricula(){
        Usuario usuarioMock = Usuario.builder().matricula("1").email("email@exemplo.com").build();
        Mockito.when(usuarioRepository.findAllByMatricula(Mockito.anyString())).thenReturn(usuarioMock);
        boolean resultado = usuarioService.enviarLinkRecuperacao("1", "email@exemplo.com");
        Assertions.assertTrue(resultado);
    }

    @Test
    void enviaLinkDeRecupercaoUsandoEmailInvalido(){
        Usuario usuarioMock = Usuario.builder().email("email@exemplo.com").build();
        Mockito.when(usuarioRepository.findAllByMatricula("matricula")).thenReturn(usuarioMock);
        boolean resultado = usuarioService.enviarLinkRecuperacao("matricula", "EmailInvalido.com");
        Assertions.assertFalse(resultado);

    }

    @Test
    void enviaLinkDeRecupercaoUsandoMatriculaInvalida(){
        Mockito.when(usuarioRepository.findAllByMatricula("matriculaInvalida")).thenReturn(null);
        boolean resultado = usuarioService.enviarLinkRecuperacao("matriculaInvalida", "email@exemplo.com");
        Assertions.assertFalse(resultado);

    }

    @Test
    void trocaSenhaUsandoToken(){
        Usuario usuarioMock = Usuario.builder().matricula("1").senha("senhaAtinga").build();
        PasswordResetToken token = PasswordResetToken.builder().id(1L).usuario(usuarioMock).expiryDate(LocalDateTime.now().plusHours(1)).build();
        Mockito.when(passwordResetTokenRepository.findByToken(Mockito.anyString())).thenReturn(token);
        boolean resultado = usuarioService.trocarSenha("token", "novaSenha");
        assertTrue(resultado);
        assertEquals(usuarioMock.getSenha(), "novaSenha");

    }

    @Test
    void trocaSenhaUsandoTokenExpirado(){
        Usuario usuarioMock = Usuario.builder().matricula("1").senha("senhaAtinga").build();
        PasswordResetToken token = PasswordResetToken.builder().id(1L).usuario(usuarioMock).expiryDate(LocalDateTime.now().minusHours(1)).build();
        Mockito.when(passwordResetTokenRepository.findByToken("tokenExpirado")).thenReturn(token);
        boolean resultado = usuarioService.trocarSenha("tokenExpirado", "novaSenha");
        Assertions.assertFalse(resultado);
    }

    @Test
    void trocaSenhaUsandoTokenInvalido(){
        Mockito.when(passwordResetTokenRepository.findByToken("TokenInvalido")).thenReturn(null);
        boolean resultado = usuarioService.trocarSenha("TokenInvalido", "novaSenha");
        Assertions.assertFalse(resultado);
    }


}