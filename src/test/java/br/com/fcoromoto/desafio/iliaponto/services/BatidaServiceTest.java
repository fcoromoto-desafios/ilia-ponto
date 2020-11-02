package br.com.fcoromoto.desafio.iliaponto.services;

import br.com.fcoromoto.desafio.iliaponto.exceptions.NegocioException;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.BatidaDto;
import br.com.fcoromoto.desafio.iliaponto.models.entities.Batida;
import br.com.fcoromoto.desafio.iliaponto.repositories.BatidaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BatidaServiceTest {

    @InjectMocks
    private BatidaService service;

    @Mock
    private BatidaRepository repository;

    @Test
    void deveIniciarMocks() {
        assertNotNull(service);
        assertNotNull(repository);
    }

    @Test
    void deveLancarExceptionQuandoLancarBatidaSabado() {
        LocalDateTime dataHora = LocalDateTime.of(2020, 10, 31, 10, 0); // Sabado
        testarFinalSemana(dataHora);
    }

    @Test
    void deveLancarExceptionQuandoLancarBatidaDomingo() {
        LocalDateTime dataHora = LocalDateTime.of(2020, 11, 1, 10, 0); // Domingo
        testarFinalSemana(dataHora);
    }

    @Test
    void deveLancarExceptionQuandoLancarMaisQuatroRegistros() {
        // Cenário
        LocalDateTime dataHora = LocalDateTime.of(2020, 11, 2, 10, 0); // Segunda
        List<LocalDateTime> registros = Arrays.asList(dataHora, dataHora, dataHora, dataHora);
        when(repository.getRegistrosPorDia(dataHora.toLocalDate())).thenReturn(registros);

        // Ação
        Executable incluir = () -> service.incluir(BatidaDto.of(dataHora));

        // Teste
        verificarException(incluir, "Apenas 4 horários podem ser registrados por dia");
        verify(repository, times(0)).save(any(Batida.class));
    }

    @Test
    void deveLancarExceptionQuandoLancarMenosUmaHoraAlmoloco() {
        //Cenário
        LocalDateTime dataHora = LocalDateTime.of(2020, 11, 2, 8, 0); // Segunda
        List<LocalDateTime> registros = Arrays.asList(dataHora, dataHora.plusHours(4));
        when(repository.getRegistrosPorDia(dataHora.toLocalDate())).thenReturn(registros);

        // Ação
        Executable incluir = () -> service.incluir(BatidaDto.of(dataHora.plusHours(4).plusMinutes(30)));

        //Teste
        verificarException(incluir, "Deve haver no mínimo 1 hora de almoço");
        verify(repository, times(0)).save(any(Batida.class));
    }

    @Test
    void deveLancarExceptionQuandoLancarMenosUmaHoraAlmolocoAdicionandoHorarioSemOrdem() {
        // Cenário
        LocalDateTime dataHora = LocalDateTime.of(2020, 11, 2, 8, 0); // Segunda
        List<LocalDateTime> registros = Arrays.asList(dataHora, dataHora.plusHours(4), dataHora.plusHours(9));
        when(repository.getRegistrosPorDia(dataHora.toLocalDate())).thenReturn(registros);

        // Ação
        Executable incluir = () -> service.incluir(BatidaDto.of(dataHora.plusHours(4).plusMinutes(30)));

        // Teste
        verificarException(incluir, "Deve haver no mínimo 1 hora de almoço");
        verify(repository, times(0)).save(any(Batida.class));
    }

    @Test
    void naoDeveLancarExceptionQuandoLancarMenosUmaHoraAlmolocoRegistroEntrada() {
        // Cenário
        LocalDateTime dataHora = LocalDateTime.of(2020, 11, 2, 8, 0); // Segunda
        when(repository.getRegistrosPorDia(dataHora.toLocalDate())).thenReturn(new ArrayList<>());

        // Ação
        Executable executable = () -> service.incluir(BatidaDto.of(dataHora));

        // Teste
        assertDoesNotThrow(executable);
        verify(repository, times(1)).save(any(Batida.class));
    }

    @Test
    void deveIncluirBatidaSucesso() {
        // Cenário
        LocalDateTime dataHora = LocalDateTime.of(2020, 11, 2, 8, 0); // Segunda
        BatidaDto batidaDto = BatidaDto.of(dataHora);

        // Ação
        service.incluir(batidaDto);

        // Teste
        verify(repository, times(1)).save(any(Batida.class));
    }

    private void testarFinalSemana(LocalDateTime dataHora) {
        verificarException(() -> service.incluir(BatidaDto.of(dataHora)),
                "Sábado e domingo não são permitidos como dia de trabalho");

        verify(repository, times(0)).save(any(Batida.class));
    }

    private void verificarException(Executable executable, String mensagem) {
        Throwable exception = assertThrows(NegocioException.class, executable);
        assertEquals(mensagem, exception.getMessage());
    }
}