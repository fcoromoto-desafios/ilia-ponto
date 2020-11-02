package br.com.fcoromoto.desafio.iliaponto.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoRegistro {

    INICIO_EXPEDIENTE(0),
    SAIDA_ALMOCO(1),
    VOLTA_ALMOCO(2),
    FIM_EXPEDIENTE(3);

    private final int indice;
}
