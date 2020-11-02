package br.com.fcoromoto.desafio.iliaponto;

import br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter.DurationFormatter;
import br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter.LocalDateFormatter;
import br.com.fcoromoto.desafio.iliaponto.config.controllers.formatter.LocalDateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class IliaPontoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IliaPontoApplication.class, args);
    }

    @Bean
    @Primary
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new LocalDateTimeFormatter();
    }

    @Bean
    @Primary
    public Formatter<LocalDate> localDateFormatter() {
        return new LocalDateFormatter();
    }

    @Bean
    @Primary
    public Formatter<Duration> durationFormatter() {
        return new DurationFormatter();
    }

}
