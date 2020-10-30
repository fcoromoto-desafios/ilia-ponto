package br.com.fcoromoto.desafio.iliaponto.util;

import lombok.experimental.UtilityClass;

import java.util.function.BooleanSupplier;

@UtilityClass
public class Predication {

    public static void checkArgument(BooleanSupplier invalid, String message) {
        if (invalid.getAsBoolean()) {
            throw new IllegalArgumentException(message);
        }
    }

}
