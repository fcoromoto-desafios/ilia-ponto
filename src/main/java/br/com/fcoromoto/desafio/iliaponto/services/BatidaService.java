package br.com.fcoromoto.desafio.iliaponto.services;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.BatidaDto;
import br.com.fcoromoto.desafio.iliaponto.models.entities.Batida;
import br.com.fcoromoto.desafio.iliaponto.repositories.BatidaRepository;
import br.com.fcoromoto.desafio.iliaponto.validadores.RegistrosValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatidaService {

    @Autowired
    private BatidaRepository repository;


    public Batida incluir(BatidaDto batidaDto) {
        List<LocalDateTime> registros = repository.getRegistrosPorDia(batidaDto.getLocalDate());
        RegistrosValidator validator = RegistrosValidator.of(batidaDto.getDataHora(), registros, 4);

        validator.validarRegistroFinalSemana();
        validator.validarQuantidadeMaximaRegistros();
        validator.validarMinimoUmaHoraAlmoco();

        Batida batida = Batida.of(batidaDto.getDataHora());
        repository.save(batida);
        return batida;
    }
}
