package com.digital.wallet.services;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.strategy.RedirecionamentoStrategy;
import com.digital.wallet.util.Identificador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class RedirecionamentoServiceTest {

    @Mock
    RedirecionamentoStrategy redirecionaPadrao;

    @Mock
    RedirecionamentoStrategy redirecionaTecnico;

    @Mock
    RedirecionamentoStrategy redirecionaAdmin;

    Usuario usuario;
    RedirecionamentoService redirecionamentoService;

    @BeforeEach
    void setUp() {
        usuario  = Usuario.builder().tipoUsuario(Identificador.TECNICO).build();
        redirecionamentoService = new RedirecionamentoService(
                List.of(redirecionaPadrao, redirecionaTecnico, redirecionaAdmin)
        );
    }


    @Test
    void redirecionarQuandoEstrategiaAceitaUsuario() {
        Mockito.when(redirecionaPadrao.aceita(usuario)).thenReturn(false);
        Mockito.when(redirecionaTecnico.aceita(usuario)).thenReturn(true);
        Mockito.when(redirecionaTecnico.redirecionar()).thenReturn("redirect:/tecnico");

        String resultado = redirecionamentoService.redirecionar(usuario);

        Assertions.assertEquals("redirect:/tecnico", resultado);
        Mockito.verify(redirecionaTecnico).redirecionar();
        Mockito.verify(redirecionaAdmin, Mockito.never()).redirecionar();
    }

    @Test
    void redirecionarParaInicioQuandoNenhumaEstrategiaAceita() {
        Mockito.when(redirecionaPadrao.aceita(usuario)).thenReturn(false);
        Mockito.when(redirecionaTecnico.aceita(usuario)).thenReturn(false);
        Mockito.when(redirecionaAdmin.aceita(usuario)).thenReturn(false);

        String resultado = redirecionamentoService.redirecionar(usuario);

        Assertions.assertEquals("redirect:/inicio", resultado);
    }

    @Test
    void usarPrimeiraEstrategiaQueAceitaUsuario() {
        Mockito.when(redirecionaPadrao.aceita(usuario)).thenReturn(true);
        Mockito.when(redirecionaPadrao.redirecionar()).thenReturn("redirect:/padrao");
        Mockito.when(redirecionaTecnico.aceita(usuario)).thenReturn(true);
        Mockito.when(redirecionaAdmin.aceita(usuario)).thenReturn(true);

        String resultado = redirecionamentoService.redirecionar(usuario);

        Assertions.assertEquals("redirect:/padrao", resultado);
        Mockito.verify(redirecionaPadrao).redirecionar();
        Mockito.verify(redirecionaTecnico, Mockito.never()).redirecionar();
        Mockito.verify(redirecionaAdmin, Mockito.never()).redirecionar();
    }
}