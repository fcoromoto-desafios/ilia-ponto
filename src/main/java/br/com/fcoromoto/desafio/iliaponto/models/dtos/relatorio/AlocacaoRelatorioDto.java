package br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio;

import br.com.fcoromoto.desafio.iliaponto.models.entities.Alocacao;
import br.com.fcoromoto.desafio.iliaponto.util.Predication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlocacaoRelatorioDto {

    private final String nomeProjeto;
    private final Duration tempo;


    public static AlocacaoRelatorioDto of(String nomeProjeto, Duration tempo) {
        Predication.checkArgument(() -> StringUtils.isEmpty(nomeProjeto), "Nome projeto não pode ser vazio");
        Predication.checkArgument(() -> Objects.isNull(tempo), "Tempo não pode ser nullo");
        return new AlocacaoRelatorioDto(nomeProjeto, tempo);
    }

    public static AlocacaoRelatorioDto of(Alocacao alocacao) {
        return of(alocacao.getProjeto(), alocacao.getTempo());
    }

}
