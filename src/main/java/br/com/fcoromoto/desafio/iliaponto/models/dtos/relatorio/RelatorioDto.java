package br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio;

import br.com.fcoromoto.desafio.iliaponto.exceptions.RegistroNaoEncontradoException;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RelatorioDto {

    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("yyyy-MM");

    private String mes;
    private Duration horasRegistradas;
    private Duration horasTrabalhadas;
    private Duration horasExcedentes;
    private Duration horasDevidas;

    private List<AlocacaoRelatorioDto> alocacoes;
    private List<RegistroRelatorioDto> registros;


    public static RelatorioDto of(String mes, List<AlocacaoRelatorioDto> alocacoes, List<RegistroRelatorioDto> registros) {
        if (CollectionUtils.isEmpty(alocacoes) && CollectionUtils.isEmpty(registros)) {
            throw new RegistroNaoEncontradoException("Não é possível gerar o relatorio");
        }

        RelatorioDto relatorioDto = new RelatorioDto();

        relatorioDto.alocacoes = new ArrayList<>(alocacoes);
        relatorioDto.registros = new ArrayList<>(registros);
        relatorioDto.mes = mes;

        relatorioDto.horasRegistradas = relatorioDto.calcularHorasRegistradas();
        relatorioDto.horasTrabalhadas = relatorioDto.calcularHorasTrabalhadas();
        relatorioDto.calcularHorasDevidasExcedentes();

        return relatorioDto;
    }

    private Duration calcularHorasRegistradas() {
        return registros.stream().map(RegistroRelatorioDto::getTotalHoras).reduce(Duration.ZERO, Duration::plus);
    }

    private Duration calcularHorasTrabalhadas() {
        return alocacoes.stream().map(AlocacaoRelatorioDto::getTempo).reduce(Duration.ZERO, Duration::plus);
    }

    private void calcularHorasDevidasExcedentes() {
        horasExcedentes = Duration.ZERO;
        horasDevidas = Duration.ZERO;

        Duration diferenca = horasRegistradas.minus(horasTrabalhadas);

        if (diferenca.isNegative()) {
            horasExcedentes = diferenca;
        } else if (!diferenca.isZero()) {
            horasDevidas = diferenca;
        }
    }

}
