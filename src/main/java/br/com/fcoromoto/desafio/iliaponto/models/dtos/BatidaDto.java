package br.com.fcoromoto.desafio.iliaponto.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static java.util.Objects.isNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatidaDto {

    @NotNull
    private LocalDateTime dataHora;

    public static BatidaDto of(LocalDateTime dataHora) {
        checkArgument(() -> isNull(dataHora), "Data hora não pode ser nullo");

        return new BatidaDto(dataHora);
    }

    public LocalDate getLocalDate() {
        return dataHora.toLocalDate();
    }

}
