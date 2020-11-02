package br.com.fcoromoto.desafio.iliaponto.helpers;

import br.com.fcoromoto.desafio.iliaponto.models.enums.TipoRegistro;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CalculadoraHorarios {

    private static final Duration HORA_ALMOCO = Duration.ofHours(1);

    public static Duration calcular(List<LocalDateTime> registrosDia) {
        if (CollectionUtils.isEmpty(registrosDia) || registrosDia.size() == 1) {
            return Duration.ZERO;
        }

        if (registrosDia.size() == 4) {
            List<LocalDateTime> registros = new ArrayList<>(registrosDia);
            registros.sort(LocalDateTime::compareTo);

            LocalDateTime inicioExpediente = registrosDia.get(TipoRegistro.INICIO_EXPEDIENTE.getIndice());
            LocalDateTime saidaAlmoco = registrosDia.get(TipoRegistro.SAIDA_ALMOCO.getIndice());
            LocalDateTime voltaAlmoco = registrosDia.get(TipoRegistro.VOLTA_ALMOCO.getIndice());
            LocalDateTime fimExpediente = registrosDia.get(TipoRegistro.FIM_EXPEDIENTE.getIndice());

            Duration tempoManha = Duration.between(inicioExpediente, saidaAlmoco);
            Duration tempoTarde = Duration.between(voltaAlmoco, fimExpediente);
            return tempoManha.plus(tempoTarde);
        } else {
            LinkedList<LocalDateTime> horario = new LinkedList<>(registrosDia);
            LocalDateTime primeiroHorario = horario.pollFirst();
            LocalDateTime ultimoHorario = horario.pollLast();

            Duration total = Duration.between(primeiroHorario, ultimoHorario);

            return total.minus(HORA_ALMOCO);
        }
    }
}
