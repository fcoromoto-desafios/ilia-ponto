package br.com.fcoromoto.desafio.iliaponto.util;

import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static br.com.fcoromoto.desafio.iliaponto.util.Predication.checkArgument;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PredicationTest {


    @Test
    void mustThrowIllegalArgumentException() {
        String messageError = "Object must not be null";
        BooleanSupplier invalid = () -> true;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> checkArgument(invalid, messageError));
        assertSame(messageError, exception.getMessage());
    }

}