package com.digital.wallet.services;

import com.digital.wallet.model.CompraPresencial;
import com.digital.wallet.repositories.CompraPresencialRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
@ExtendWith(SpringExtension.class)
class CompraServiceTest {

    @Mock
    private CompraPresencialRepository compraPresencialRepository;

    @InjectMocks
    private CompraService compraService;

    @Test
    void listaTodasComprasPresenciais() {
        Mockito.when(compraPresencialRepository.findAll()).thenReturn(List.of(new CompraPresencial()));
        Assertions.assertThat(compraService.buscarComprasPresenciais()).hasSize(1);
    }

    @Test
    void buscaHistoricoDeComprasRealizdasPeloIdEMatriculaDoUsuario(){
        Mockito.when(compraPresencialRepository.comprasRealizadas(Mockito.anyLong(),Mockito.anyString())).thenReturn(List.of(new CompraPresencial()));
        List<CompraPresencial> comprasRealizadas = compraService.hitoricoComprasRealizadas(1L, "1");
        Assertions.assertThat(comprasRealizadas).hasSize(1);
    }

}