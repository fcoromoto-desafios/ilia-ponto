package br.com.fcoromoto.desafio.iliaponto.models.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDTO {

    private final String field;
    private final String error;

    public static ErrorDTO of(String field, String error) {
        return new ErrorDTO(field, error);
    }
}
