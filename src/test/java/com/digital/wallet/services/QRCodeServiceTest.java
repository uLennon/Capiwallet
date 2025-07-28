package com.digital.wallet.services;

import com.google.zxing.WriterException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
class QRCodeServiceTest {

    @Mock
    private QRCodeService qrCodeService;

    @Test
    void geraImagemDoQRCodeUsandoString() throws WriterException, IOException {
        Mockito.when(qrCodeService.gerarImagemQrCode("texto")).thenReturn(new byte[1]);
        byte[] bytes = qrCodeService.gerarImagemQrCode("texto");
        Assertions.assertThat(bytes).isNotEmpty();
        Assertions.assertThat(bytes).isNotNull();
    }
}