package br.com.fcoromoto.desafio.iliaponto.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;

@Getter
@Entity
@Table(name = "TB_BATIDA")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Batida {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    public static Batida of(LocalDateTime dataHora) {
        checkArgument(() -> isNull(dataHora), "DataHora não pode ser nullo");
        return new Batida(null, dataHora);
    }
}
