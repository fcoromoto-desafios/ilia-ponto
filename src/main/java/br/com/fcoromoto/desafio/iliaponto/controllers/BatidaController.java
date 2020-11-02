package br.com.fcoromoto.desafio.iliaponto.controllers;

import br.com.fcoromoto.desafio.iliaponto.models.dtos.BatidaDto;
import br.com.fcoromoto.desafio.iliaponto.models.dtos.MensagemDTO;
import br.com.fcoromoto.desafio.iliaponto.services.BatidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("batidas")
public class BatidaController {

    @Autowired
    private BatidaService batidaService;

    @PostMapping
    public ResponseEntity<MensagemDTO> incluir(@RequestBody @Valid BatidaDto batida) {
        batidaService.incluir(batida);
        return ResponseEntity.created(null).body(MensagemDTO.of("Registro incluido com sucesso"));
    }
}
