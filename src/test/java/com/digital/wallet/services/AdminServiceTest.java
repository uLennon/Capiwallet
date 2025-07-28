package com.digital.wallet.services;

import com.digital.wallet.model.Usuario;
import com.digital.wallet.util.Identificador;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class AdminServiceTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ExecutorService executorService;

    @InjectMocks
    private AdminService adminService;

    @Test
    void buscarUsuariosIndefinidos() {
        Mockito.when(usuarioService.buscarUsuariosPorTipo(Identificador.INDEFINIDO)).thenReturn(List.of(Usuario.builder().tipoUsuario(Identificador.INDEFINIDO).build()));
        List<Usuario> usuarios = adminService.buscarUsuariosIndefinidos();
        Assertions.assertThat(usuarios).hasSize(1).allMatch(usuario -> usuario.getTipoUsuario().equals(Identificador.INDEFINIDO));
        verify(usuarioService).buscarUsuariosPorTipo(Identificador.INDEFINIDO);
    }

    @Test
    void rejeitarUsuarioComSucesso() {
        String matricula = "123";
        String motivo = "Matricula  invalida";
        Usuario usuarioMock = Usuario.builder().email("usuario@email.com").build();

        Mockito.when(usuarioService.buscarUsuarioPorMatricula(motivo)).thenReturn(usuarioMock);
        Mockito.when(executorService.submit(Mockito.any(Runnable.class))).thenReturn(null);
        Mockito.doNothing().when(usuarioService).rejeitarUsuario(matricula);

        boolean resultado = adminService.rejeitarUsuario(matricula, motivo);

        assertTrue(resultado);
        verify(usuarioService).buscarUsuarioPorMatricula(matricula);
        verify(usuarioService).rejeitarUsuario(matricula);
        verify(executorService).submit(Mockito.any(Runnable.class));
    }
}