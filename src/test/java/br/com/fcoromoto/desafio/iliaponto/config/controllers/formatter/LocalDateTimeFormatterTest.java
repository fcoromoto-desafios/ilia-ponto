package br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

class LocalDateTimeFormatterTest {

    private static final LocalDateTimeFormatter formatter = new LocalDateTimeFormatter();

    @Test
    void parse() {
        String dataAsString = "2020-11-01T20:11:32.611Z";
        LocalDateTime dataConvertiva = LocalDateTime.ofInstant(Instant.parse(dataAsString), ZoneOffset.UTC);

        Assertions.assertEquals(dataConvertiva, formatter.parse(dataAsString, null));
    }

    @Test
    void parseSemnanoSegundos() {
        String dataAsString = "2020-11-01T20:11:32Z";
        LocalDateTime dataConvertiva = LocalDateTime.ofInstant(Instant.parse(dataAsString), ZoneOffset.UTC);

        Assertions.assertEquals(dataConvertiva, formatter.parse(dataAsString, null));
    }

    @Test
    void print() {
        LocalDateTime data = LocalDateTime.of(2020, 11, 1, 20, 11, 32, 611000000);
        String dataParse = formatter.print(data, null);

        Assertions.assertEquals("2020-11-01T20:11:32.611Z", dataParse);
    }
}