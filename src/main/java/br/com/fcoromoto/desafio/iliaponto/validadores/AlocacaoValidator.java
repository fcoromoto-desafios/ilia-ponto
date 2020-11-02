package br.com.fcoromoto.desafio.iliaponto.validadores;

import br.com.fcoromoto.desafio.iliaponto.helpers.CalculadoraHorarios;
import br.com.fcoromoto.desafio.iliaponto.util.Predication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AlocacaoValidator {

    private static final Duration HORA_ALMOCO = Duration.ofHours(1);
    private final Duration totalHorasTrabalhasDia;
    private final List<Duration> alocacoesDia;
    private final Duration tempoTrabalho;

    public static AlocacaoValidator of(List<LocalDateTime> registrosDia, List<Duration> alocacoesTempoDia, Duration tempoTrabalho) {
        checkArgument(() -> isNull(registrosDia), "Registros não pode ser nullo");
        checkArgument(() -> isNull(alocacoesTempoDia), "Alocacoes de tempo não pode ser nullo");
        checkArgument(() -> isNull(tempoTrabalho), "Tempo trabalho não pode ser nullo");

        AlocacaoValidator validator = new AlocacaoValidator(calcularTempoTotalTrabalhoDia(registrosDia),
                new ArrayList<>(alocacoesTempoDia),
                tempoTrabalho);
        return validator;
    }

    private static Duration calcularTempoTotalTrabalhoDia(List<LocalDateTime> registrosDia) {
        return CalculadoraHorarios.calcular(registrosDia);
    }

    public void validarAlocarTempoMaiorTrabalhoDia() {
        List<Duration> alocacoes = new ArrayList<>(alocacoesDia);
        alocacoes.add(tempoTrabalho);

        Duration tempoTotal = alocacoes.stream().reduce(Duration.ZERO, Duration::plus);

        Predication.validarRegra(() -> tempoTotal.toMillis() > totalHorasTrabalhasDia.toMillis(),
                "Não pode alocar tempo maior que o tempo trabalhado no dia");
    }
}
