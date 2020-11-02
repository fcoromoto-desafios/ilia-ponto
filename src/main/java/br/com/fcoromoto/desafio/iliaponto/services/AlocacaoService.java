package br.com.fcoromoto.desafio.iliaponto.services;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.AlocacaoDto;
import br.com.fcoromoto.desafio.iliaponto.models.entities.Alocacao;
import br.com.fcoromoto.desafio.iliaponto.repositories.AlocacaoRepository;
import br.com.fcoromoto.desafio.iliaponto.repositories.BatidaRepository;
import br.com.fcoromoto.desafio.iliaponto.validadores.AlocacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlocacaoService {

    @Autowired
    private AlocacaoRepository repository;

    @Autowired
    private BatidaRepository batidaRepository;

    public void incluir(AlocacaoDto alocacaoDto) {

        AlocacaoValidator validador = AlocacaoValidator.of(
                batidaRepository.getRegistrosPorDia(alocacaoDto.getDia()),
                repository.getTempoAlocadoPorDia(alocacaoDto.getDia()),
                alocacaoDto.getTempo());

        validador.validarAlocarTempoMaiorTrabalhoDia();

        Alocacao alocacao = Alocacao.of(alocacaoDto);
        repository.save(alocacao);
    }
}
