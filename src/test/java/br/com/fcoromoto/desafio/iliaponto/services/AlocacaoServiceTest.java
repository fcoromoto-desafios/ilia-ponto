package br.com.fcoromoto.desafio.iliaponto.services;

import br.com.fcoromoto.desafio.iliaponto.exceptions.NegocioException;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.AlocacaoDto;
import br.com.fcoromoto.desafio.iliaponto.models.entities.Alocacao;
import br.com.fcoromoto.desafio.iliaponto.repositories.AlocacaoRepository;
import br.com.fcoromoto.desafio.iliaponto.repositories.BatidaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlocacaoServiceTest {

    @InjectMocks
    private AlocacaoService service;

    @Mock
    private AlocacaoRepository repository;

    @Mock
    private BatidaRepository batidaRepository;

    @Test
    public void deveLancarExpcetionQuandoAlocarTempoMaiorTrabalhado() {
        // Cenário
        AlocacaoDto alocacaoDto = AlocacaoDto.of(LocalDate.now(), Duration.ofHours(2), "Projeto novo");

        List<LocalDateTime> registrosDia = Arrays.asList(
                LocalDateTime.of(2020, 11, 2, 8, 0, 0),
                LocalDateTime.of(2020, 11, 2, 12, 0, 0),
                LocalDateTime.of(2020, 11, 2, 13, 0, 0),
                LocalDateTime.of(2020, 11, 2, 14, 0, 0));

        List<Duration> registrosTempo = Collections.singletonList(Duration.ofHours(7));

        // Teste
        verificarException(alocacaoDto, registrosDia, registrosTempo);
    }

    @Test
    public void deveLancarExpcetionuandoAlocarTempoMaiorTrabalhadoRegistrosNaoCompletos() {
        // Cenário
        AlocacaoDto alocacaoDto = AlocacaoDto.of(LocalDate.now(), Duration.ofHours(2), "Projeto novo");
        List<LocalDateTime> registrosDia = Arrays.asList(
                LocalDateTime.of(2020, 11, 2, 8, 0, 0),
                LocalDateTime.of(2020, 11, 2, 12, 0, 0),
                LocalDateTime.of(2020, 11, 2, 17, 0, 0));

        List<Duration> registrosTempo = Collections.singletonList(Duration.ofHours(7));

        // Teste
        verificarException(alocacaoDto, registrosDia, registrosTempo);
    }

    @Test
    public void deveLancarExpcetionQuandoNaoPossuiRegistrosHoras() {
        // Cenário
        AlocacaoDto alocacaoDto = AlocacaoDto.of(LocalDate.now(), Duration.ofHours(2), "Projeto novo");
        List<LocalDateTime> registrosDia = Collections.emptyList();
        List<Duration> registrosTempo = Collections.emptyList();

        // Teste
        verificarException(alocacaoDto, registrosDia, registrosTempo);
    }

    @Test
    public void deveAlocarHorarioSucesso() {
        // Cenário
        AlocacaoDto alocacaoDto = AlocacaoDto.of(LocalDate.now(), Duration.ofHours(1).plusMinutes(30),
                "Projeto novo");

        List<LocalDateTime> registrosDia = Arrays.asList(
                LocalDateTime.of(2020, 11, 2, 8, 0, 0),
                LocalDateTime.of(2020, 11, 2, 12, 0, 0),
                LocalDateTime.of(2020, 11, 2, 13, 0, 0),
                LocalDateTime.of(2020, 11, 2, 17, 0, 0));
        Mockito.when(batidaRepository.getRegistrosPorDia(Mockito.any(LocalDate.class))).thenReturn(registrosDia);

        List<Duration> registrosTempo = Arrays.asList(
                Duration.ofHours(1),
                Duration.ofHours(3),
                Duration.ofMinutes(30)
        );
        Mockito.when(repository.getTempoAlocadoPorDia(Mockito.any(LocalDate.class))).thenReturn(registrosTempo);

        // Ação
        Executable incluir = () -> service.incluir(alocacaoDto);

        // Teste
        assertDoesNotThrow(incluir);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Alocacao.class));
    }

    private void verificarException(AlocacaoDto alocacaoDto,
                                    List<LocalDateTime> registrosDia,
                                    List<Duration> registrosTempo) {
        // Cenário
        Mockito.when(batidaRepository.getRegistrosPorDia(Mockito.any(LocalDate.class))).thenReturn(registrosDia);
        Mockito.when(repository.getTempoAlocadoPorDia(Mockito.any(LocalDate.class))).thenReturn(registrosTempo);

        // Ação
        Executable incluir = () -> service.incluir(alocacaoDto);

        // Teste
        verificarException(incluir, "Não pode alocar tempo maior que o tempo trabalhado no dia");
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Alocacao.class));
    }

    private void verificarException(Executable executable, String mensagem) {
        Throwable exception = assertThrows(NegocioException.class, executable);
        assertEquals(mensagem, exception.getMessage());
    }

}