package br.com.fcoromoto.desafio.iliaponto.controllers;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.AlocacaoDto;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.MensagemDTO;
import br.com.fcoromoto.desafio.iliaponto.services.AlocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("alocacoes")
public class AlocacaoController {

    @Autowired
    private AlocacaoService alocacaoService;

    @PostMapping
    public ResponseEntity<MensagemDTO> incluir(@RequestBody @Valid AlocacaoDto alocacao) {
        alocacaoService.incluir(alocacao);
        return ResponseEntity.created(null).body(MensagemDTO.of("Registro incluido com sucesso"));
    }
}
