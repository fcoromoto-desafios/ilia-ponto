package br.com.fcoromoto.desafio.iliaponto.validadores;


import br.com.fcoromoto.desafio.iliaponto.models.enums.TipoRegistro;
import br.com.fcoromoto.desafio.iliaponto.util.Predication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.validarRegra;
import static java.lang.String.format;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegistrosValidator {

    private final LocalDateTime dataHora;
    private final List<LocalDateTime> registros;
    private final int totalRegistros;
    private final int quantidadeMaximaRegistros;


    public static RegistrosValidator of(LocalDateTime dataHora, List<LocalDateTime> registros, int quantidadeMaximaRegistros) {
        Predication.checkArgument(() -> Objects.isNull(dataHora), "Data hora não pode ser nullo");
        return new RegistrosValidator(dataHora, registros, registros.size(), quantidadeMaximaRegistros);
    }

    public void validarRegistroFinalSemana() {
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        validarRegra(() -> diaSemana.equals(SATURDAY) || diaSemana.equals(SUNDAY),
                "Sábado e domingo não são permitidos como dia de trabalho");
    }

    public void validarQuantidadeMaximaRegistros() {
        validarRegra(() -> totalRegistros >= quantidadeMaximaRegistros,
                format("Apenas %d horários podem ser registrados por dia", quantidadeMaximaRegistros));
    }

    public void validarMinimoUmaHoraAlmoco() {
        if (isRegistroVoltaAlmoco()) {

            List<LocalDateTime> horariosRegistrados = new ArrayList<>(registros);
            horariosRegistrados.add(dataHora);

            horariosRegistrados.sort(LocalDateTime::compareTo);

            LocalDateTime saidaAlmoco = horariosRegistrados.get(TipoRegistro.SAIDA_ALMOCO.getIndice());
            LocalDateTime voltaAlmoco = horariosRegistrados.get(TipoRegistro.VOLTA_ALMOCO.getIndice());

            long tempoAlmoco = ChronoUnit.MINUTES.between(saidaAlmoco, voltaAlmoco);

            validarRegra(() -> tempoAlmoco < 60, "Deve haver no mínimo 1 hora de almoço");
        }
    }

    private boolean isRegistroVoltaAlmoco() {
        if (CollectionUtils.isEmpty(registros)) {
            return false;
        }

        LinkedList<LocalDateTime> itens = new LinkedList<>(registros);
        int ultimoIndice = itens.indexOf(itens.getLast());
        int proximoIndice = ultimoIndice + 1;
        return proximoIndice == TipoRegistro.VOLTA_ALMOCO.getIndice();
    }
}
