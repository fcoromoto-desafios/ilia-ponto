package br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Objects;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {


    @Override
    public LocalDateTime parse(String s, Locale locale) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        Instant parse = Instant.parse(s);
        return LocalDateTime.ofInstant(parse, ZoneOffset.UTC);
    }

    @Override
    public String print(LocalDateTime date, Locale locale) {
        if (Objects.isNull(date)) {
            return null;
        }

        return date.toInstant(ZoneOffset.UTC).toString();
    }
}
