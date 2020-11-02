package br.com.fcoromoto.desafio.iliaponto.util;

import br.com.fcoromoto.desafio.iliaponto.exceptions.NegocioException;
import lombok.experimental.UtilityClass;

import java.util.function.BooleanSupplier;

@UtilityClass
public class Predication {

    public static void checkArgument(BooleanSupplier invalid, String message) {
        if (invalid.getAsBoolean()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validarRegra(BooleanSupplier invalid, String message) {
        if (invalid.getAsBoolean()) {
            throw new NegocioException(message);
        }
    }

}
