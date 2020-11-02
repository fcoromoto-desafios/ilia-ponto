package br.com.fcoromoto.desafio.iliaponto.repositories;

import br.com.fcoromoto.desafio.iliaponto.models.entities.Alocacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class AlocacaRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AlocacaoRepository repository;


    @Test
    void deveRetornarRegistrosPorMes() {

        LocalDateTime dataHoraBase = LocalDateTime.of(2020, 11, 2, 8, 0, 0);

        entityManager.persist(Alocacao.builder(dataHoraBase.toLocalDate(), Duration.ofHours(8), "Projeto Teste"));
        entityManager.persist(Alocacao.builder(dataHoraBase.plusDays(1).toLocalDate(), Duration.ofHours(8), "Projeto Teste"));

        List<Alocacao> registrosPorMes = repository.getRegistrosPorMes("2020-11");
        assertSame(2, registrosPorMes.size());

    }
}