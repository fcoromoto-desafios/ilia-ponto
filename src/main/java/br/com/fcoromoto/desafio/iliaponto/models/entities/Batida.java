package br.com.fcoromoto.desafio.iliaponto.models.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;

@Getter
@Entity
@Table(name = "TB_BATIDA")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Batida {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BATIDA")
    private Long id;

    @Column(name = "DH_BATIDA")
    private LocalDateTime dataHora;

    private Batida (LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public static Batida of(LocalDateTime dataHora) {
        checkArgument(() -> isNull(dataHora), "DataHora não pode ser nullo");
        return new Batida(dataHora);
    }
}
