package br.com.fcoromoto.desafio.iliaponto.services;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio.AlocacaoRelatorioDto;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio.RegistroRelatorioDto;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio.RelatorioDto;
import br.com.fcoromoto.desafio.iliaponto.repositories.AlocacaoRepository;
import br.com.fcoromoto.desafio.iliaponto.repositories.BatidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private AlocacaoRepository alocacaoRepository;

    @Autowired
    private BatidaRepository batidaRepository;

    public RelatorioDto gerarRelatorio(String mesAno) {
        List<AlocacaoRelatorioDto> alocacoes = alocacaoRepository.getRegistrosPorMes(mesAno).stream()
                .map(AlocacaoRelatorioDto::of)
                .collect(Collectors.toList());

        List<RegistroRelatorioDto> registros = RegistroRelatorioDto.of(batidaRepository.getRegistrosPorMes(mesAno));

        return RelatorioDto.of(mesAno, alocacoes, registros);
    }
}
