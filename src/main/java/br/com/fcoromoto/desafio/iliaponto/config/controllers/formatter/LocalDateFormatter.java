package br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class LocalDateFormatter implements Formatter<LocalDate> {


    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        if (Objects.isNull(date)) {
            return null;
        }
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }
}
