package br.com.fcoromoto.desafio.iliaponto.models.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BatidaTest {

    @Test
    void deveLancarIllegalArgumentExceptionQuandoCriarEntidadeSemDataHora() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Batida.of(null));
        assertSame("DataHora não pode ser nullo", exception.getMessage());
    }

    @Test
    void deveCriarBatidaComBuilder() {
        LocalDateTime data = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime dataEsperada = LocalDateTime.of(2020, 1, 1, 0, 0);

        Batida batida = Batida.of(data);

        assertNotNull(batida);
        assertTrue(dataEsperada.isEqual(batida.getDataHora()));
    }
}