package br.com.fcoromoto.desafio.iliaponto.config.jpa.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationAttributeConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration duracao) {
        return (duracao == null ? null : duracao.toString());
    }

    @Override
    public Duration convertToEntityAttribute(String duracao) {
        return StringUtils.isEmpty(duracao) ? null : Duration.parse(duracao);
    }
}