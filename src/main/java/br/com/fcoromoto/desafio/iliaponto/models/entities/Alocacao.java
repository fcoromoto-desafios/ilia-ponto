package br.com.fcoromoto.desafio.iliaponto.models.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDate;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alocacao {

    private Long id;
    private LocalDate data;
    private Duration tempo;
    private String projeto;

    public static AlocacaoBuilder builder(LocalDate data, Duration tempo, String projeto) {
        checkArgument(() -> isNull(data), "Data não pode ser nullo");
        checkArgument(() -> isNull(tempo), "Tempo não pode ser nullo");
        checkArgument(() -> StringUtils.isEmpty(projeto), "Projeto não pode ser nullo");

        return new AlocacaoBuilder(data, tempo, projeto);
    }

    public static class AlocacaoBuilder {
        private final LocalDate data;
        private final Duration tempo;
        private final String projeto;
        private Long id;

        AlocacaoBuilder(LocalDate data, Duration tempo, String projeto) {
            this.data = data;
            this.tempo = tempo;
            this.projeto = projeto;
        }

        public AlocacaoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Alocacao build() {
            return new Alocacao(id, data, tempo, projeto);
        }
    }
}
