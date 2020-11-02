package br.com.fcoromoto.desafio.iliaponto.models.entities;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class AlocacaoTest {

    @Test
    void deveLancarIllegalArgumentExceptionQuandoNaoInformarData() {
        Supplier<Alocacao> builder = () -> Alocacao.builder(null, Duration.ZERO, null);
        testarArgumentoInvalido(builder, "Data não pode ser nullo");
    }

    @Test
    void deveLancarIllegalArgumentExceptionQuandoNaoInformarTempo() {
        Supplier<Alocacao> builder = () -> Alocacao.builder(LocalDate.now(), null, null);
        testarArgumentoInvalido(builder, "Tempo não pode ser nullo");
    }

    @Test
    void deveLancarIllerArgumentExceptionQuandoNaoInformarProjeto() {
        Supplier<Alocacao> builder = () -> Alocacao.builder(LocalDate.now(), Duration.ZERO, null);
        testarArgumentoInvalido(builder, "Projeto não pode ser nullo");
    }

    @Test
    void deveCriarAlocacaoComSucesso() {
        String projeto = "Projeto Teste";
        Duration tempo = Duration.ZERO;
        LocalDate data = LocalDate.now();

        Alocacao alocacao = Alocacao.builder(data, tempo, projeto);

        assertNotNull(alocacao);
        assertSame(data, alocacao.getData());
        assertSame(tempo, alocacao.getTempo());
        assertSame(projeto, alocacao.getProjeto());
    }

    private void testarArgumentoInvalido(Supplier<Alocacao> builder, String mensagem) {
        Throwable exception = assertThrows(IllegalArgumentException.class, builder::get);
        assertSame(mensagem, exception.getMessage());
    }
}