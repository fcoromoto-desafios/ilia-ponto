package br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.time.Duration;
import java.util.Locale;
import java.util.Objects;

public class DurationFormatter implements Formatter<Duration> {


    @Override
    public Duration parse(String s, Locale locale) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return Duration.parse(s);
    }

    @Override
    public String print(Duration duracao, Locale locale) {
        if (Objects.isNull(duracao)) {
            return null;
        }

        return duracao.toString();
    }
}
