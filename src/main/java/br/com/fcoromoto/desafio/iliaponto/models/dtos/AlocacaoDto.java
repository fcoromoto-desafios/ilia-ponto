package br.com.fcoromoto.desafio.iliaponto.models.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlocacaoDto {

    @NotNull
    private LocalDate dia;

    @NotNull
    private Duration tempo;

    @NotNull
    @NotEmpty
    private String nomeProjeto;


    public static AlocacaoDto of(LocalDate dia, Duration tempo, String nomeProjeto) {
        checkArgument(() -> isNull(dia), "Dia não pode ser nullo");
        checkArgument(() -> isNull(tempo), "Tempo não pode ser nullo");
        checkArgument(() -> isEmpty(nomeProjeto), "Nome do projeto não pode ser nullo ou vazio");
        return new AlocacaoDto(dia, tempo, nomeProjeto);
    }
}
