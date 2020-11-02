package br.com.fcoromoto.desafio.iliaponto.config.controllers;

import br.com.fcoromoto.desafio.iliaponto.exceptions.NegocioException;
import br.com.fcoromoto.desafio.iliaponto.exceptions.RegistroNaoEncontradoException;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.ErrorDTO;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.MensagemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceErrorAdvice {

    private static final String RESOURCE_NOT_FOUND = "Resource not found";

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDTO> handle(MethodArgumentNotValidException exception) {

        return exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    String field = fieldError.getField();
                    return ErrorDTO.of(field, message);
                })
                .collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handle(EntityNotFoundException exception) {
        return RESOURCE_NOT_FOUND;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handle(EmptyResultDataAccessException exception) {
        return RESOURCE_NOT_FOUND;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public String handle(RegistroNaoEncontradoException exception) {
        return RESOURCE_NOT_FOUND;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NegocioException.class)
    public MensagemDTO handle(NegocioException exception) {
        return MensagemDTO.of(exception.getMessage());
    }
}
