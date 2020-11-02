package br.com.fcoromoto.desafio.iliaponto.models.entities;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.AlocacaoDto;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;

@Getter
@Entity
@Table(name = "TB_ALOCACAO")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alocacao {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALOCACAO")
    private Long id;

    @Column(name = "DT_ALOCACAO")
    private LocalDate data;

    @Column(name = "TEMPO_ALOCACAO")
    private Duration tempo;

    @Column(name = "NOME_PROJETO")
    private String projeto;

    public static Alocacao builder(LocalDate data, Duration tempo, String projeto) {
        checkArgument(() -> isNull(data), "Data não pode ser nullo");
        checkArgument(() -> isNull(tempo), "Tempo não pode ser nullo");
        checkArgument(() -> StringUtils.isEmpty(projeto), "Projeto não pode ser nullo");

        return new Alocacao(null, data, tempo, projeto);
    }

    public static Alocacao of(AlocacaoDto alocacaoDto) {
        return builder(alocacaoDto.getDia(), alocacaoDto.getTempo(), alocacaoDto.getNomeProjeto());
    }
}
