package br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio;

import br.com.fcoromoto.desafio.iliaponto.exceptions.RegistroNaoEncontradoException;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

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

        relatorioDto.mes = mes;
        relatorioDto.registros = new ArrayList<>(registros);
        relatorioDto.alocacoes = relatorioDto.agruparAlocacoes(alocacoes);

        relatorioDto.horasRegistradas = relatorioDto.calcularHorasRegistradas();
        relatorioDto.horasTrabalhadas = relatorioDto.calcularHorasTrabalhadas();
        relatorioDto.calcularHorasDevidasExcedentes();

        return relatorioDto;
    }

    private List<AlocacaoRelatorioDto> agruparAlocacoes(List<AlocacaoRelatorioDto> alocacoes){
        Map<String, List<Duration>> mapa = criarMapaProjetoTempo(alocacoes);

        List<AlocacaoRelatorioDto> alocacoesProjetosAgrupados = new ArrayList<>();

        for(String projeto : mapa.keySet()){
            Duration tempo = mapa.get(projeto).stream().reduce(Duration.ZERO, Duration::plus);
            AlocacaoRelatorioDto dto = AlocacaoRelatorioDto.of(projeto, tempo);
            alocacoesProjetosAgrupados.add(dto);
        }
        return alocacoesProjetosAgrupados;
    }

    private Map<String, List<Duration>> criarMapaProjetoTempo(List<AlocacaoRelatorioDto> alocacoes){
        return alocacoes.stream()
                .collect(groupingBy(AlocacaoRelatorioDto::getNomeProjeto,
                                    mapping(AlocacaoRelatorioDto::getTempo, toList())));
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
