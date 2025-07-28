package com.digital.wallet.services;

import com.digital.wallet.model.Carteira;
import com.digital.wallet.model.ReembolsoTicket;
import com.digital.wallet.model.Usuario;
import com.digital.wallet.repositories.CarteiraRepository;
import com.digital.wallet.repositories.ReembolsoTicketRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(SpringExtension.class)
class CarteiraServiceTest {

    @Mock
    private CarteiraRepository carteiraRepository;
    @Mock
    private ReembolsoTicketRepository reembolsoTicketRepository;
    @InjectMocks
    private CarteiraService carteiraService;


    @Test
    void buscaCarteiraPeloId(){
        Carteira carteira = Carteira.builder().id(1L).build();
        Usuario usuario = Usuario.builder().carteira(carteira).build();
        Mockito.when(carteiraRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(carteira));
        Carteira carteira1 = carteiraService.buscarCarteira(usuario);
        assertEquals(carteira.getId(), carteira1.getId());
    }

    @Test
    void buscaCarteiraPeloIdInvalido(){
        Carteira carteira = Carteira.builder().id(null).build();
        Usuario usuario = Usuario.builder().carteira(carteira).build();
        Mockito.when(carteiraRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(carteira));
        assertThrows(RuntimeException.class,()-> carteiraService.buscarCarteira(usuario));
    }

    @Test
    void buscaReembolsoTicketPeloId(){
        ReembolsoTicket reembolsoTicket = ReembolsoTicket.builder().id(1L).build();
        Mockito.when(reembolsoTicketRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(reembolsoTicket));
        ReembolsoTicket reembolsoTicket1 = carteiraService.buscarReembolso(reembolsoTicket.getId());
        assertEquals(reembolsoTicket.getId(), reembolsoTicket1.getId());
    }

    @Test
    void buscaTodosOsReembolsos(){
        Mockito.when(reembolsoTicketRepository.findAll()).thenReturn(List.of(new ReembolsoTicket()));
        Assertions.assertThat(carteiraService.buscarListaReembolsos()).hasSize(1);
    }

}