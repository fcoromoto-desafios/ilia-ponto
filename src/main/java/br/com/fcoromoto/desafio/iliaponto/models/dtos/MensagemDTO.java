package br.com.fcoromoto.desafio.iliaponto.models.dtos;

import br.com.fcoromoto.desafio.iliaponto.util.Predication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MensagemDTO {

    private final String mensagem;

    public static MensagemDTO of(String mensagem) {
        Predication.checkArgument(() -> StringUtils.isEmpty(mensagem), "Mensagem não pode ser vazia");
        return new MensagemDTO(mensagem);
    }
}
