package br.com.fcoromoto.desafio.iliaponto.controllers;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.relatorio.RelatorioDto;
import br.com.fcoromoto.desafio.iliaponto.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("folhas-de-ponto")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    @RequestMapping("/{mes}")
    public ResponseEntity<RelatorioDto> gerarRelatorio(@PathVariable String mes) {
        return ResponseEntity.ok().body(relatorioService.gerarRelatorio(mes));
    }
}
