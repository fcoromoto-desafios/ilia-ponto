package br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio;

import br.com.fcoromoto.desafio.iliaponto.helpers.CalculadoraHorarios;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistroRelatorioDto {

    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalDate dia;
    private final List<String> horarios;
    private final Duration totalHoras;


    public static List<RegistroRelatorioDto> of(List<LocalDateTime> registros) {
        if (CollectionUtils.isEmpty(registros)) {
            return null;
        }

        List<RegistroRelatorioDto> dtos = registros.stream()
                .collect(Collectors.groupingBy(LocalDateTime::toLocalDate,
                        Collectors.mapping(localDateTime -> localDateTime, Collectors.toList())))
                .entrySet()
                .stream()
                .map(localDateSetEntry -> of(localDateSetEntry.getKey(), localDateSetEntry.getValue()))
                .collect(Collectors.toList());

        dtos.sort(Comparator.comparing(RegistroRelatorioDto::getDia));

        return dtos;
    }

    public static RegistroRelatorioDto of(LocalDate dia, List<LocalDateTime> registros) {
        registros.sort(LocalDateTime::compareTo);
        List<String> horariosFormatados = registros.stream().map(formatadorData::format).collect(Collectors.toList());
        Duration tempoTrabalhado = CalculadoraHorarios.calcular(registros);
        return new RegistroRelatorioDto(dia, horariosFormatados, tempoTrabalhado);
    }


}
